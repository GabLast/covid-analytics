package com.myorg.covid_analytics.repositories.configuration;

import com.myorg.covid_analytics.models.configurations.UserSetting;
import com.myorg.covid_analytics.models.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingRepository extends JpaRepository<UserSetting, Long> {

    UserSetting findByEnabledAndUser(boolean enabled, User user);
}
