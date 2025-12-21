package com.myorg.covid_analytics.repositories.process;

import com.myorg.covid_analytics.models.process.CovidLoadHeader;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CovidLoadHeaderRepository extends JpaRepository<CovidLoadHeader, Long> {

    @EntityGraph(attributePaths = "user")
    @Query("select " +
            "u " +
            "from CovidLoadHeader as u " +
            "where u.enabled = :enabled " +
            "and (:description is null or u.description like '' or u.description is null or lower(u.description) like lower(trim(concat('%', :description,'%')))) " +
            "and (:userId is null or u.user.id = :userId) " +
            "and (:start is null or u.loadedDate >= :start) " +
            "and (:end is null or u.loadedDate <= :end) "
    )
    List<CovidLoadHeader> findAllFilter(
            @Param("enabled") boolean enabled,
            @Param("userId") Long userId,
            @Param("description") String description,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            Pageable pageable
    );

    @Query("select " +
            "count (u) " +
            "from CovidLoadHeader as u " +
            "where u.enabled = :enabled " +
            "and (:description is null or u.description like '' or u.description is null or lower(u.description) like lower(trim(concat('%', :description,'%')))) " +
            "and (:userId is null or u.user.id = :userId) " +
            "and (:start is null or u.loadedDate >= :start) " +
            "and (:end is null or u.loadedDate <= :end) "
    )
    Integer countAllFilter(
            @Param("enabled") boolean enabled,
            @Param("userId") Long userId,
            @Param("description") String description,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Query("select " + "count (a) " + "from CovidLoadHeader as a "
                    + "where a.enabled = :enabled " + "and a.dateCreated >= :start "
                    + "and a.dateCreated < :end")
    Integer countCovidLoadHeaderByLoadedDateAndEnabled(
            @Param("start") LocalDateTime start, @Param("end") LocalDateTime end,
            @Param("enabled") boolean enabled);

}
