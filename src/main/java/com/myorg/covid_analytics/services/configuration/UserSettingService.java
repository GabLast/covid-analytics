package com.myorg.covid_analytics.services.configuration;

import com.myorg.covid_analytics.models.configurations.UserSetting;
import com.myorg.covid_analytics.models.security.User;
import com.myorg.covid_analytics.repositories.configuration.UserSettingRepository;
import com.myorg.covid_analytics.services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSettingService extends BaseService<UserSetting, Long> {

    private final UserSettingRepository userSettingRepository;
    @Override
    protected JpaRepository<UserSetting, Long> getRepository() {
        return userSettingRepository;
    }

    public UserSetting findByEnabledAndUser(boolean enabled, User user) {
        return userSettingRepository.findByEnabledAndUser(enabled, user);
    }

}
