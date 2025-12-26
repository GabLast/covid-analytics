package com.myorg.covid_analytics.services.configuration;

import com.myorg.covid_analytics.converter.PaginationConverter;
import com.myorg.covid_analytics.dto.PaginationObject;
import com.myorg.covid_analytics.dto.request.configuration.CountryFilterRequest;
import com.myorg.covid_analytics.dto.request.configuration.CountryRequest;
import com.myorg.covid_analytics.dto.response.CountResponse;
import com.myorg.covid_analytics.dto.response.CountResponseData;
import com.myorg.covid_analytics.dto.response.configuration.CountryData;
import com.myorg.covid_analytics.dto.response.configuration.CountryFilterData;
import com.myorg.covid_analytics.dto.response.configuration.CountryFilterDataDetails;
import com.myorg.covid_analytics.dto.response.configuration.CountryFilterResponse;
import com.myorg.covid_analytics.dto.response.configuration.CountryFindAllData;
import com.myorg.covid_analytics.dto.response.configuration.CountryFindAllDataDetails;
import com.myorg.covid_analytics.dto.response.configuration.CountryFindAllResponse;
import com.myorg.covid_analytics.dto.response.configuration.CountryResponse;
import com.myorg.covid_analytics.exceptions.InvalidDataFormat;
import com.myorg.covid_analytics.exceptions.ResourceExistsException;
import com.myorg.covid_analytics.exceptions.ResourceNotFoundException;
import com.myorg.covid_analytics.models.configurations.Country;
import com.myorg.covid_analytics.repositories.configuration.CountryRepository;
import com.myorg.covid_analytics.services.BaseService;
import com.myorg.covid_analytics.utils.OffsetBasedPageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CountryService extends BaseService<Country, Long> {

    private final CountryRepository repository;

    @Override
    protected JpaRepository<Country, Long> getRepository() {
        return repository;
    }

    public void bootstrap() {
        for (Country.CountryEnum value : Country.CountryEnum.values()) {
            Optional<Country> toSave =
                    repository.findByAnyIdentifier(true, value.getCountryCode());
            if (toSave.isEmpty()) {
                saveAndFlush(Country.builder()
                        .name(value.getName())
                        .countryCode(value.getCountryCode()).placeId(value.getPlaceId())
                        .wikiDataId(value.getWikiDataId())
                        .dataCommonsId(value.getDataCommonsId())
                        .iso_3166_1_alpha_2(value.getIso_3166_1_alpha_2())
                        .iso_3166_1_alpha_3(value.getIso_3166_1_alpha_3())
                        .build());
            }
        }
        log.info("Country bootstrap done");
    }

    @Transactional(readOnly = true)
    public Optional<Country> findByAnyIdentifier(boolean enabled, String identifier) {
        return repository.findByAnyIdentifier(enabled, identifier);
    }

    @Transactional(readOnly = true)
    public List<Country> findAllByEnabled(boolean enabled) {
        return repository.findAllByEnabled(enabled);
    }

    public CountryFindAllResponse findAllResponse() {
        return CountryFindAllResponse.builder()
                .data(CountryFindAllData.builder()
                        .dataList(findAllByEnabled(true).stream()
                                .map(it -> CountryFindAllDataDetails.builder()
                                        .id(it.getId())
                                        .code(it.getCountryCode())
                                        .name(it.getName())
                                        .build()).toList())
                        .build())
                .build();
    }

    @Transactional(readOnly = true)
    public List<Country> findAllFilter(Boolean enabled, String name, String description,
            Integer limit, Integer offset, Sort sort) {
        return repository.findAllFilter(enabled, name, description,
                sort == null ? new OffsetBasedPageRequest(limit, offset)
                             : new OffsetBasedPageRequest(limit, offset, sort));
    }

    @Transactional(readOnly = true)
    public Integer countAllFilter(Boolean enabled, String name, String description) {
        return repository.countAllFilter(enabled, name, description);
    }

    public CountryFilterResponse findAllProfileFilter(CountryFilterRequest request) {

        PaginationObject paginationObject =
                PaginationConverter.fromSimpleValues(request.getSortProperty(),
                        request.getSortOrder(), request.getOffset(), request.getLimit());

        List<CountryFilterDataDetails> dataList =
                findAllFilter(request.isEnabled(), request.getName(),
                        request.getCountryCode(), paginationObject.limit(),
                        paginationObject.offset(), paginationObject.sort()).stream()
                        .map(it -> CountryFilterDataDetails.builder()
                                .id(it.getId()).name(it.getName())
                                .dataCommonsId(it.getDataCommonsId())
                                .iso_3166_1_alpha_2(it.getIso_3166_1_alpha_2())
                                .iso_3166_1_alpha_3(it.getIso_3166_1_alpha_3())
                                .wikiDataId(it.getWikiDataId())
                                .placeId(it.getPlaceId())
                                .code(it.getCountryCode()).build()).toList();

        return CountryFilterResponse.builder()
                .data(CountryFilterData.builder().dataList(dataList).build()).build();

    }

    public CountResponse countAllFilter(CountryFilterRequest request) {

        return CountResponse.builder().data(CountResponseData.builder()
                .total(countAllFilter(request.isEnabled(), request.getName(),
                        request.getCountryCode())).build()).build();
    }

    public CountryResponse save(CountryRequest request) {

        Country saveObject = get(request.id()).orElse(new Country());

        if (StringUtils.isBlank(request.name())) {
            throw new InvalidDataFormat("The name can not be blank");
        }

        if (StringUtils.isBlank(request.code())) {
            throw new InvalidDataFormat("The code can not be blank");
        }

        if (StringUtils.isBlank(request.wikiDataId())) {
            throw new InvalidDataFormat("The wikiDataId can not be blank");
        }

        if (StringUtils.isBlank(request.placeId())) {
            throw new InvalidDataFormat("The placeId can not be blank");
        }

        if (StringUtils.isBlank(request.dataCommonsId())) {
            throw new InvalidDataFormat("The dataCommonsId can not be blank");
        }

        if (StringUtils.isBlank(request.iso_3166_1_alpha_2())) {
            throw new InvalidDataFormat("The iso_3166_1_alpha_2 can not be blank");
        }

        if (StringUtils.isBlank(request.iso_3166_1_alpha_3())) {
            throw new InvalidDataFormat("The iso_3166_1_alpha_3 can not be blank");
        }

        if(!saveObject.getId().equals(request.id())) {

            if(findByAnyIdentifier(true, request.name()).isPresent()) {
                throw new ResourceExistsException("A country with the value " + request.name() + " already exists.");
            }

            if(findByAnyIdentifier(true, request.code()).isPresent()) {
                throw new ResourceExistsException("A country with the value " + request.code() + " already exists.");
            }

            if(findByAnyIdentifier(true, request.wikiDataId()).isPresent()) {
                throw new ResourceExistsException("A country with the value " + request.wikiDataId() + " already exists.");
            }

            if(findByAnyIdentifier(true, request.placeId()).isPresent()) {
                throw new ResourceExistsException("A country with the value " + request.placeId() + " already exists.");
            }

            if(findByAnyIdentifier(true, request.dataCommonsId()).isPresent()) {
                throw new ResourceExistsException("A country with the value " + request.dataCommonsId() + " already exists.");
            }

            if(findByAnyIdentifier(true, request.iso_3166_1_alpha_2()).isPresent()) {
                throw new ResourceExistsException("A country with the value " + request.iso_3166_1_alpha_2() + " already exists.");
            }

            if(findByAnyIdentifier(true, request.iso_3166_1_alpha_3()).isPresent()) {
                throw new ResourceExistsException("A country with the value " + request.iso_3166_1_alpha_3() + " already exists.");
            }
        }

        saveObject.setName(request.name());
        saveObject.setCountryCode(request.code());
        saveObject.setWikiDataId(request.wikiDataId());
        saveObject.setPlaceId(request.placeId());
        saveObject.setDataCommonsId(request.dataCommonsId());
        saveObject.setIso_3166_1_alpha_2(request.iso_3166_1_alpha_2());
        saveObject.setIso_3166_1_alpha_3(request.iso_3166_1_alpha_3());

        saveObject = saveAndFlush(saveObject);

        return generateResponse(saveObject);
    }

    public CountryResponse getCountryById(Long id) {
        Optional<Country> header = get(id);
        if (header.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Object by ID [" + id + "] does not exist");
        }

        return generateResponse(header.get());
    }

    public CountryResponse delete(Long id) {
        Optional<Country> header = get(id);
        if (header.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Object by ID [" + id + "] does not exist");
        }

        disable(header.get());

        return generateResponse(header.get());
    }

    private CountryResponse generateResponse(Country country) {
        return CountryResponse.builder()
                .data(CountryData.builder()
                        .id(country.getId()).name(country.getName())
                        .dataCommonsId(country.getDataCommonsId())
                        .iso_3166_1_alpha_2(country.getIso_3166_1_alpha_2())
                        .iso_3166_1_alpha_3(country.getIso_3166_1_alpha_3())
                        .wikiDataId(country.getWikiDataId())
                        .placeId(country.getPlaceId())
                        .code(country.getCountryCode()).build())
                .build();
    }
}
