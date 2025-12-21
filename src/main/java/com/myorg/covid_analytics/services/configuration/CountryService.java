package com.myorg.covid_analytics.services.configuration;

import com.myorg.covid_analytics.dto.response.configuration.CountryFindAllData;
import com.myorg.covid_analytics.dto.response.configuration.CountryFindAllDataDetails;
import com.myorg.covid_analytics.dto.response.configuration.CountryFindAllResponse;
import com.myorg.covid_analytics.models.configurations.Country;
import com.myorg.covid_analytics.repositories.configuration.CountryRepository;
import com.myorg.covid_analytics.services.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
