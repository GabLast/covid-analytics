package com.myorg.covid_analytics.services.security;

import com.myorg.covid_analytics.models.security.Profile;
import com.myorg.covid_analytics.models.security.ProfilePermit;
import com.myorg.covid_analytics.repositories.security.ProfilePermitRepository;
import com.myorg.covid_analytics.services.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProfilePermitService extends BaseService<ProfilePermit, Long> {

    private final ProfilePermitRepository profilePermitRepository;


    @Override
    protected JpaRepository<ProfilePermit, Long> getRepository() {
        return profilePermitRepository;
    }

    public List<ProfilePermit> findAllByEnabledAndProfile(boolean enabled, Profile profile) {
        return profilePermitRepository.findAllByEnabledAndProfileAndPermit_EnabledIsTrueAndProfile_EnabledIsTrue(enabled, profile);
    }
}
