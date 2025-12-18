package com.myorg.covid_analytics.services.security;

import com.myorg.covid_analytics.models.security.ProfileUser;
import com.myorg.covid_analytics.models.security.User;
import com.myorg.covid_analytics.repositories.security.ProfileUserRepository;
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
public class ProfileUserService extends BaseService<ProfileUser, Long> {

    private final ProfileUserRepository profileUserRepository;


    @Override
    protected JpaRepository<ProfileUser, Long> getRepository() {
        return profileUserRepository;
    }

    public List<ProfileUser> findAllByEnabledAndUser(boolean enabled, User user) {
        return profileUserRepository.findAllByEnabledAndUser(enabled, user);
    }
}
