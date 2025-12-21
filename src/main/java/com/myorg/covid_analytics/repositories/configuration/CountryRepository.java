package com.myorg.covid_analytics.repositories.configuration;

import com.myorg.covid_analytics.models.configurations.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("select " +
            "u " +
            "from Country as u " +
            "where u.enabled = :enabled " +
            "and ("
            + "(lower(trim(u.name)) like trim(lower(:value))) "
            + "or (lower(trim(u.countryCode)) like trim(lower(:value))) "
            + "or (lower(trim(u.placeId)) like trim(lower(:value))) "
            + "or (lower(trim(u.wikiDataId)) like trim(lower(:value))) "
            + "or (lower(trim(u.dataCommonsId)) like trim(lower(:value))) "
            + "or (lower(trim(u.iso_3166_1_alpha_2)) like trim(lower(:value))) "
            + "or (lower(trim(u.iso_3166_1_alpha_3)) like trim(lower(:value))) "
            + ")"
    )
    Optional<Country> findByAnyIdentifier(@Param("enabled") boolean enabled, @Param("value") String value);

    List<Country> findAllByEnabled(boolean e);
}
