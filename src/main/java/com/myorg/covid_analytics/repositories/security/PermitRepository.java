package com.myorg.covid_analytics.repositories.security;

import com.myorg.covid_analytics.models.security.Permit;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermitRepository extends JpaRepository<Permit, Long> {

    Permit findFirstByEnabledAndCode(boolean enabled, String code);

    @EntityGraph(attributePaths = "permitFather")
    List<Permit> findAllByEnabled(boolean enabled);
    List<Permit> findAllByEnabledAndPermitFatherIsNull(boolean enabled);
    List<Permit> findAllByEnabledAndPermitFather(boolean enabled, Permit father);

    List<Permit> findAllByEnabledAndPermitFather_Id(boolean enabled, Long father);
}
