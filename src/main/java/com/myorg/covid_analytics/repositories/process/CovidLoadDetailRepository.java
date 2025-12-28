package com.myorg.covid_analytics.repositories.process;

import com.myorg.covid_analytics.dto.response.dashboard.DashboardOneDataDetails;
import com.myorg.covid_analytics.dto.response.dashboard.DashboardTwoData;
import com.myorg.covid_analytics.models.process.CovidLoadDetail;
import com.myorg.covid_analytics.models.process.CovidLoadHeader;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CovidLoadDetailRepository extends JpaRepository<CovidLoadDetail, Long> {

    @EntityGraph(attributePaths = {"country"})
    @Query("select " +
            "u " +
            "from CovidLoadDetail as u " +
            "where u.enabled = :enabled " +
            "and u.header.id = :headerId " +
            "and (:countryCode is null or u.country is null or lower(u.country.countryCode) like lower(trim(cast(:countryCode as string)))) " +
            "and (:start is null or u.date >= :start) " +
            "and (:end is null or u.date < :end) "
    )
    List<CovidLoadDetail> findAllFilter(
            @Param("enabled") boolean enabled,
            @Param("headerId") Long headerId,
            @Param("countryCode") String countryCode,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            Pageable pageable
    );

    @Query("select " +
            "count(u) " +
            "from CovidLoadDetail as u " +
            "where u.enabled = :enabled " +
            "and u.header.id = :headerId " +
            "and (:countryCode is null or u.country is null or lower(u.country.countryCode) like lower(trim(cast(:countryCode as string)))) " +
            "and (:start is null or u.date >= :start) " +
            "and (:end is null or u.date < :end) "
    )
    Integer countAllFilter(
            @Param("enabled") boolean enabled,
            @Param("headerId") Long headerId,
            @Param("countryCode") String countryCode,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @EntityGraph(attributePaths = {"country"})
    List<CovidLoadDetail> findAllByHeaderAndEnabled(CovidLoadHeader header, boolean enabled);

    @Query("select\n" +
            "co.id as countryId,\n" +
            "co.name as country,\n" +
            "u.population as population,\n" +
            "u.population_male as populationMale,\n" +
            "u.population_female as populationFemale\n" +
            "from CovidLoadDetail as u\n" +
            "join country co on u.country = co\n" +
            "where u.date in (SELECT MAX(detail.date) AS MostRecentDate FROM CovidLoadDetail detail where detail.enabled = true and detail.country = co)\n" +
            "and u.enabled = true and u.population is not null and u.population_male is not null and u.population_female is not null " +
            "group by co, u.population, u.population_male, u.population_female"
    )
    List<DashboardOneDataDetails> getDataDashboardOne();

    @Query("select\n" +
            "      sum(u.new_confirmed) as infections,\n"
            + "    sum(u.new_deceased) as deaths,\n"
            + "    sum(u.new_persons_vaccinated) as newPersonVaccinated,\n"
            + "    sum(u.new_persons_fully_vaccinated) as newPersonFullyVaccinated,\n"
            + "    sum(u.new_tested) as newTested,\n"
            + "    sum(u.new_vaccine_doses_administered) as newVaccineDosesAdministered\n"
            + "from CovidLoadDetail u\n"
            + "join country co on u.country = co\n"
            + "where u.enabled = true\n"
            + "and (:countryCode is null or lower(co.countryCode) like lower(trim(:countryCode)))\n"
            + "and (:start is null or u.date >= :start)\n"
            + "and (:end is null or u.date < :end)")
    DashboardTwoData getDataDashboardTwo(
            @Param("countryCode") String countryCode,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end);
}
