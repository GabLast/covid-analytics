package com.myorg.covid_analytics.services.configuration;

import com.myorg.covid_analytics.models.configurations.Country;
import com.myorg.covid_analytics.models.configurations.UserSetting;
import com.myorg.covid_analytics.models.security.User;
import com.myorg.covid_analytics.repositories.configuration.CountryRepository;
import com.myorg.covid_analytics.repositories.configuration.UserSettingRepository;
import com.myorg.covid_analytics.services.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                        .country(value.getCountry())
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

}
