package com.myorg.covid_analytics.repositories.configuration;

import com.myorg.covid_analytics.models.configurations.Country;
import org.springframework.data.domain.Pageable;
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

    @Query("select " +
            "u " +
            "from Country as u " +
            "where (:enabled is null or u.enabled = :enabled) " +
            "and (:name is null or u.name like '' or u.name like lower(trim(concat('%', :name,'%')))) " +
            "and (:countryCode is null or u.countryCode like '' or u.countryCode like lower(trim(concat('%', :countryCode,'%')))) "
    )
    List<Country> findAllFilter(@Param("enabled") Boolean enabled,
            @Param("name") String name,
            @Param("countryCode") String countryCode,
            Pageable pageable
    );

    @Query("select " +
            "count(u) " +
            "from Country as u " +
            "where (:enabled is null or u.enabled = :enabled) " +
            "and (:name is null or u.name like '' or u.name like lower(trim(concat('%', :name,'%')))) " +
            "and (:countryCode is null or u.countryCode like '' or u.countryCode like lower(trim(concat('%', :countryCode,'%')))) "
    )
    Integer countAllFilter(@Param("enabled") Boolean enabled,
            @Param("name") String name,
            @Param("countryCode") String countryCode
    );
}
