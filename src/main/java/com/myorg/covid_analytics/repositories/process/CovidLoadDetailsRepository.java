package com.myorg.covid_analytics.repositories.process;

import com.myorg.covid_analytics.models.process.CovidLoadDetails;
import com.myorg.covid_analytics.models.process.CovidLoadHeader;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CovidLoadDetailsRepository extends JpaRepository<CovidLoadDetails, Long> {

    @Query("select " +
            "u " +
            "from CovidLoadHeader as u " +
            "where u.enabled = :enabled " +
            "and u.header.id = :headerId " +
            "and (:countryCode is null or u.countryCode like '' or u.countryCode is null or lower(u.countryCode) like lower(trim(concat('%', :countryCode,'%')))) " +
            "and (:start is null or u.date >= :start) " +
            "and (:end is null or u.date <= :end) "
    )
    List<CovidLoadDetails> findAllFilter(
            @Param("enabled") boolean enabled,
            @Param("headerId") Long headerId,
            @Param("countryCode") String countryCode,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            Pageable pageable
    );
}
