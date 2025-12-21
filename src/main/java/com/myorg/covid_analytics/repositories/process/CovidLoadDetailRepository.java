package com.myorg.covid_analytics.repositories.process;

import com.myorg.covid_analytics.dto.response.dashboard.DashboardOneData;
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
//            "and (:countryCode is null or u.country is null or lower(u.country.countryCode) like lower(trim(concat('%', :countryCode,'%')))) " +
            "and (:countryCode is null or u.country is null or lower(u.country.countryCode) like lower(trim(:countryCode))) " +
            "and (:start is null or u.date >= :start) " +
            "and (:end is null or u.date <= :end) "
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
            "and (:countryCode is null or u.country is null or lower(u.country.countryCode) like lower(trim(:countryCode))) " +
            "and (:start is null or u.date >= :start) " +
            "and (:end is null or u.date <= :end) "
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

//    @Query("select " +
//            "sum()" +
//            "from CovidLoadDetail as u " +
//            "where u.enabled = true "
//            + "group by u.id"
//    )
//    DashboardOneData getDataDashboardOne();
}
