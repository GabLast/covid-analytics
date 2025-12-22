package com.myorg.covid_analytics.services.process;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myorg.covid_analytics.adapter.CovidLoadAdapter;
import com.myorg.covid_analytics.converter.PaginationConverter;
import com.myorg.covid_analytics.dao.CovidDataSet;
import com.myorg.covid_analytics.dao.CovidRowCsv;
import com.myorg.covid_analytics.dto.PaginationObject;
import com.myorg.covid_analytics.dto.request.dashboard.DashboardTwoFilterRequest;
import com.myorg.covid_analytics.dto.request.process.CovidDetailFilterRequest;
import com.myorg.covid_analytics.dto.request.process.CovidHeaderFilterRequest;
import com.myorg.covid_analytics.dto.request.process.CovidLoadRequest;
import com.myorg.covid_analytics.dto.response.CountResponse;
import com.myorg.covid_analytics.dto.response.CountResponseData;
import com.myorg.covid_analytics.dto.response.dashboard.DashboardOneData;
import com.myorg.covid_analytics.dto.response.dashboard.DashboardOneDataDetails;
import com.myorg.covid_analytics.dto.response.dashboard.DashboardOneResponse;
import com.myorg.covid_analytics.dto.response.dashboard.DashboardTwoData;
import com.myorg.covid_analytics.dto.response.dashboard.DashboardTwoResponse;
import com.myorg.covid_analytics.dto.response.process.CovidDetailFilterData;
import com.myorg.covid_analytics.dto.response.process.CovidDetailFilterDataDetails;
import com.myorg.covid_analytics.dto.response.process.CovidDetailFilterResponse;
import com.myorg.covid_analytics.dto.response.process.CovidHeaderFilterData;
import com.myorg.covid_analytics.dto.response.process.CovidHeaderFilterDataDetails;
import com.myorg.covid_analytics.dto.response.process.CovidHeaderFilterResponse;
import com.myorg.covid_analytics.dto.response.process.CovidLoadData;
import com.myorg.covid_analytics.dto.response.process.CovidLoadDataDetail;
import com.myorg.covid_analytics.dto.response.process.CovidLoadResponse;
import com.myorg.covid_analytics.exceptions.ClientException;
import com.myorg.covid_analytics.exceptions.InvalidDataFormat;
import com.myorg.covid_analytics.exceptions.ResourceExistsException;
import com.myorg.covid_analytics.exceptions.ResourceNotFoundException;
import com.myorg.covid_analytics.models.configurations.UserSetting;
import com.myorg.covid_analytics.models.process.CovidLoadDetail;
import com.myorg.covid_analytics.models.process.CovidLoadHeader;
import com.myorg.covid_analytics.models.security.User;
import com.myorg.covid_analytics.services.configuration.CountryService;
import com.myorg.covid_analytics.services.redis.RedisService;
import com.myorg.covid_analytics.utils.DateUtilities;
import com.myorg.covid_analytics.utils.Utilities;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CovidAnalyticsService {

    private final CovidLoadHeaderService covidLoadHeaderService;
    private final CovidLoadDetailService covidLoadDetailService;
    private final RedisService           redisService;
    private final CountryService         countryService;
    private final ObjectMapper           objectMapper;

    public CovidLoadResponse save(CovidLoadRequest request, MultipartFile file) {

        Optional<CovidLoadHeader> header = covidLoadHeaderService.get(request.id());
        User user = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        CovidLoadHeader saveObject = header.orElse(new CovidLoadHeader());

        saveObject.setLoadedDate(request.date());
        saveObject.setUser(user);
        saveObject.setDescription(request.description().trim());
        saveObject.setJsonURL(request.jsonURL() != null ? request.jsonURL() : "");
        saveObject.setJsonString(
                request.jsonString() != null ? request.jsonString() : "");

        if (header.isPresent()) {
            if ((!StringUtils.isBlank(request.jsonURL()) || !StringUtils.isBlank(
                    request.jsonString()) || file != null)
                    && covidLoadHeaderService.hasThereBeenALoadOnDate(LocalDate.now())) {
                throw new ResourceExistsException(
                        "The data ingestion has been done today. Feel free to retry tomorrow.");
            }

            saveObject = covidLoadHeaderService.saveAndFlush(saveObject);
            return buildCovidLoadResponse(saveObject, user, false);
        } else {
            if (covidLoadHeaderService.hasThereBeenALoadOnDate(LocalDate.now())) {
                throw new ResourceExistsException(
                        "The data ingestion has been done today. Feel free to retry tomorrow.");
            }
        }

        saveObject = covidLoadHeaderService.saveAndFlush(saveObject);

        List<CovidLoadDetail> detailsToSave;

        if (!StringUtils.isBlank(request.jsonURL())) {

            if (!Utilities.isValidUrl(request.jsonURL())) {
                throw new InvalidDataFormat("Invalid JSON URL");
            }

            try {
                HttpRequest req =
                        HttpRequest.newBuilder().uri(URI.create(request.jsonURL()))
                                .header("Content-Type", "application/json").GET().build();

                HttpResponse<String> response = HttpClient.newHttpClient()
                        .send(req, HttpResponse.BodyHandlers.ofString());

                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        false);
                objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS,
                        true);
                objectMapper.configure(DeserializationFeature.USE_LONG_FOR_INTS, true);

                CovidDataSet covidDataSet =
                        objectMapper.readValue(response.body(), CovidDataSet.class);

                detailsToSave =
                        CovidLoadAdapter.jsonDatasetToModel(covidDataSet, saveObject,
                                countryService);

                covidLoadDetailService.saveAllAndFlush(detailsToSave);

            } catch (Exception e) {
                throw new ClientException("Client Error: " + e.getMessage());
            }

        } else if (!StringUtils.isBlank(request.jsonString())) {

            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false);
            objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS,
                    true);
            objectMapper.configure(DeserializationFeature.USE_LONG_FOR_INTS, true);

            CovidDataSet covidDataSet;
            try {
                covidDataSet =
                        objectMapper.readValue(request.jsonString(), CovidDataSet.class);

                detailsToSave =
                        CovidLoadAdapter.jsonDatasetToModel(covidDataSet, saveObject,
                                countryService);

                covidLoadDetailService.saveAllAndFlush(detailsToSave);
            } catch (Exception e) {
                throw new InvalidDataFormat("Invalid JSON Format");
            }

        } else if (file != null) {

            if (!Utilities.isCSVFile(file)) {
                throw new InvalidDataFormat("Invalid File Type. Please use a CSV File");
            }

            File csvFile = Utilities.convertMultipartFileToTempFile(file);

            List<CovidRowCsv> covidRowCsvList;
            try {
                FileReader fileReader = new FileReader(csvFile);
                covidRowCsvList =
                        new CsvToBeanBuilder(fileReader).withType(CovidRowCsv.class)
                                .build().parse();
                fileReader.close();
            } catch (IOException e) {
                throw new ClientException("Client Error: " + e.getMessage());
            }

            Utilities.deleteTempFile(csvFile.getName());

            //            System.out.println(covidRowCsvList.size());
            //            System.out.println("file name: " + csvFile.getName());
            //            System.out.println("deleted:" + Utilities.deleteTempFile(csvFile.getName()));;

            detailsToSave = new ArrayList<>();
            for (CovidRowCsv row : covidRowCsvList) {
                CovidLoadDetail tmp = CovidLoadAdapter.csvDatasetToModel(row, saveObject,
                        countryService.findByAnyIdentifier(true, row.getCountry_code())
                                .orElse(null));
                detailsToSave.add(tmp);
            }

            covidLoadDetailService.saveAllAndFlush(detailsToSave);

        } else {
            throw new InvalidDataFormat("No data to load");
        }

        redisService.setDashboardOneCache(getDataDashboardOne());

        return buildCovidLoadResponse(saveObject, user, true);
    }

    private CovidLoadResponse buildCovidLoadResponse(CovidLoadHeader header, User user,
            boolean sendDetails) {
        return CovidLoadResponse.builder()
                .data(CovidLoadData.builder().headerId(header.getId())
                        .description(header.getDescription()).loadDate(
                                DateUtilities.getLocalDateAsString(header.getLoadedDate(),
                                        (UserSetting) SecurityContextHolder.getContext()
                                                .getAuthentication().getDetails()))
                        .jsonURL(header.getJsonURL()).jsonString(header.getJsonString())
                        .details(sendDetails ? getDetailsForHeader(header) : null)
                        .build()).build();
    }

    private List<CovidLoadDataDetail> getDetailsForHeader(CovidLoadHeader header) {
        List<CovidLoadDataDetail> list = new ArrayList<>();

        for (CovidLoadDetail it : covidLoadDetailService.findAllByHeaderAndEnabled(header,
                true)) {
            CovidLoadDataDetail tmp =
                    CovidLoadDataDetail.builder().headerId(header.getId())
                            .country(it.getCountry().getName())
                            .country_code(it.getCountry().getCountryCode())
                            .date(DateUtilities.getLocalDateAsString(it.getDate(),
                                    (UserSetting) SecurityContextHolder.getContext()
                                            .getAuthentication().getDetails()))
                            .new_confirmed(it.getNew_confirmed())
                            .new_deceased(it.getNew_deceased())
                            .new_persons_vaccinated(it.getNew_persons_vaccinated())
                            .new_persons_fully_vaccinated(
                                    it.getNew_persons_fully_vaccinated())
                            .new_vaccine_doses_administered(
                                    it.getNew_vaccine_doses_administered())
                            .population(it.getPopulation())
                            .population_male(it.getPopulation_male())
                            .population_female(it.getPopulation_female())
                            .population_rural(it.getPopulation_rural())
                            .population_urban(it.getPopulation_urban())
                            .population_largest_city(it.getPopulation_largest_city())

                            .cancel_public_events(it.getCancel_public_events())
                            .public_transport_closing(it.getPublic_transport_closing())
                            .debt_relief(it.getDebt_relief())
                            .international_support(it.getInternational_support())
                            .investment_in_vaccines(it.getInvestment_in_vaccines())
                            .build();

            list.add(tmp);
        }

        return list;
    }

    public CovidLoadResponse delete(Long id) {
        Optional<CovidLoadHeader> header = covidLoadHeaderService.get(id);
        if (header.isEmpty()) {
            throw new ResourceNotFoundException("Header does not exist");
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        List<CovidLoadDetail> details =
                covidLoadDetailService.findAllByHeaderAndEnabled(header.get(), true);

        for (CovidLoadDetail it : details) {
            it.setEnabled(false);
        }
        covidLoadDetailService.saveAllAndFlush(details);

        header.get().setEnabled(false);
        covidLoadHeaderService.saveAndFlush(header.get());

        return buildCovidLoadResponse(header.get(), user, false);
    }

    public CovidLoadResponse getHeaderData(Long id) {
        Optional<CovidLoadHeader> header = covidLoadHeaderService.get(id);
        if (header.isEmpty()) {
            throw new ResourceNotFoundException("Header does not exist");
        }

        if (!header.get().isEnabled()) {
            throw new ResourceNotFoundException("This entry has been deleted");
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return buildCovidLoadResponse(header.get(), user, false);
    }

    public CovidHeaderFilterResponse findAllHeaderFilter(
            CovidHeaderFilterRequest request) {

        UserSetting userSetting =
                (UserSetting) SecurityContextHolder.getContext().getAuthentication()
                        .getDetails();

        PaginationObject paginationObject =
                PaginationConverter.fromSimpleValues(request.getSortProperty(),
                        request.getSortOrder(), request.getOffset(), request.getLimit());

        List<CovidHeaderFilterDataDetails> dataList = covidLoadHeaderService
                .findAllFilter(request.isEnabled(), userSetting.getTimeZoneString(),
                        request.getUserId(), request.getDescription(),
                        request.getDateStart(), request.getDateEnd(),
                        paginationObject.limit(), paginationObject.offset(),
                        paginationObject.sort()).stream()
                .map(it -> CovidHeaderFilterDataDetails.builder().id(it.getId())
                        .description(it.getDescription())
                        .userId(it.getUser() != null ? it.getUser().getId() : null)
                        .userName(it.getUser() != null ? it.getUser().getName() : null)
                        .loadDate(
                                DateUtilities.getLocalDateAsString(it.getLoadedDate(),
                                        userSetting)).build()).toList();

        return CovidHeaderFilterResponse.builder()
                .data(CovidHeaderFilterData.builder().dataList(dataList).build()).build();

    }

    public CountResponse countAllHeaderFilter(CovidHeaderFilterRequest request) {

        UserSetting userSetting =
                (UserSetting) SecurityContextHolder.getContext().getAuthentication()
                        .getDetails();

        return CountResponse.builder().data(CountResponseData.builder()
                .total(covidLoadHeaderService.countAllFilter(request.isEnabled(),
                        userSetting.getTimeZoneString(), request.getUserId(),
                        request.getDescription(), request.getDateStart(),
                        request.getDateEnd())).build()).build();
    }

    public CovidDetailFilterResponse findAllDetailFilter(
            CovidDetailFilterRequest request) {

        UserSetting userSetting =
                (UserSetting) SecurityContextHolder.getContext().getAuthentication()
                        .getDetails();

        PaginationObject paginationObject =
                PaginationConverter.fromSimpleValues(request.getSortProperty(),
                        request.getSortOrder(), request.getOffset(), request.getLimit());

        List<CovidDetailFilterDataDetails> dataList = covidLoadDetailService
                .findAllFilter(request.isEnabled(), userSetting.getTimeZoneString(),
                        request.getHeaderId(), request.getCountry(),
                        request.getDateStart(), request.getDateEnd(),
                        paginationObject.limit(), paginationObject.offset(),
                        paginationObject.sort()).stream()
                .map(it -> CovidDetailFilterDataDetails.builder().id(it.getId())
                        .country(it.getCountry().getName())
                        .countryCode(it.getCountry().getCountryCode())
                        .date(DateUtilities.getLocalDateAsString(it.getDate(),
                                userSetting)).new_tested(it.getNew_tested())
                        .new_confirmed(it.getNew_confirmed())
                        .new_persons_vaccinated(it.getNew_persons_vaccinated())
                        .new_deceased(it.getNew_deceased()).new_persons_fully_vaccinated(
                                it.getNew_persons_fully_vaccinated())
                        .new_vaccine_doses_administered(
                                it.getNew_vaccine_doses_administered())
                        .population(it.getPopulation()).build()).toList();

        return CovidDetailFilterResponse.builder()
                .data(CovidDetailFilterData.builder().dataList(dataList).build()).build();

    }

    public CountResponse countAllDetailFilter(CovidDetailFilterRequest request) {

        UserSetting userSetting =
                (UserSetting) SecurityContextHolder.getContext().getAuthentication()
                        .getDetails();

        return CountResponse.builder().data(CountResponseData.builder()
                .total(covidLoadDetailService.countAllFilter(request.isEnabled(),
                        userSetting.getTimeZoneString(), request.getHeaderId(),
                        request.getCountry(), request.getDateStart(),
                        request.getDateEnd())).build()).build();
    }

    public DashboardOneResponse getDataDashboardOne() {

        Optional<DashboardOneResponse> result = redisService.getDashboardOneCache();
        if(result.isEmpty()) {
            List<DashboardOneDataDetails> data = covidLoadDetailService.getDataDashboardOne();
            return DashboardOneResponse.builder()
                    .data(DashboardOneData.builder().details(data).build()).build();
        } else {
            return result.get();
        }
    }

    public DashboardTwoResponse getDataDashboardTwo(DashboardTwoFilterRequest request) {
        UserSetting userSetting =
                (UserSetting) SecurityContextHolder.getContext().getAuthentication()
                        .getDetails();

        DashboardTwoData data = covidLoadDetailService.getDataDashboardTwo(
                userSetting.getTimeZoneString(), request.country(), request.dateStart(),
                request.dateEnd());

        return DashboardTwoResponse.builder().data(data).build();
    }
}
