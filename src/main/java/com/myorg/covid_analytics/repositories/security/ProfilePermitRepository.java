package com.myorg.covid_analytics.repositories.security;

import com.myorg.covid_analytics.models.security.Profile;
import com.myorg.covid_analytics.models.security.ProfilePermit;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfilePermitRepository extends JpaRepository<ProfilePermit, Long> {

    @EntityGraph(attributePaths = {"permit"})
    List<ProfilePermit> findAllByEnabledAndProfileAndPermit_EnabledIsTrueAndProfile_EnabledIsTrue(boolean enabled, Profile profile);

    List<ProfilePermit> findAllByEnabledIsTrue();
}
