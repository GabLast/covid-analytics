package com.myorg.covid_analytics.services.configuration;

import com.myorg.covid_analytics.dto.request.configuration.UserSettingRequest;
import com.myorg.covid_analytics.dto.response.configuration.UserSettingResponse;
import com.myorg.covid_analytics.dto.response.configuration.UserSettingResponseData;
import com.myorg.covid_analytics.models.configurations.UserSetting;
import com.myorg.covid_analytics.models.security.User;
import com.myorg.covid_analytics.repositories.configuration.UserSettingRepository;
import com.myorg.covid_analytics.services.BaseService;
import com.myorg.covid_analytics.utils.GlobalConstants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSettingService extends BaseService<UserSetting, Long> {

    private final UserSettingRepository userSettingRepository;
    @Override
    protected JpaRepository<UserSetting, Long> getRepository() {
        return userSettingRepository;
    }

    public UserSetting findByEnabledAndUser(boolean enabled, User user) {
        return userSettingRepository.findByEnabledAndUser(enabled, user);
    }

    public UserSettingResponse save(UserSettingRequest request) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        UserSetting toSave = findByEnabledAndUser(true, user);
        if(toSave == null) {
            toSave = new UserSetting();
        }

        toSave.setUser(user);
        toSave.setId(request.id());
        toSave.setTimeZoneString(StringUtils.isBlank(request.timeZoneString()) ? GlobalConstants.DEFAULT_TIMEZONE : request.timeZoneString());
        toSave.setDateFormat(StringUtils.isBlank(request.dateFormat()) ? "dd/MM/yyyy" : request.dateFormat());
        toSave.setDateTimeFormat(StringUtils.isBlank(request.dateTimeFormat()) ? "dd/MM/yyyy hh:mm a" : request.dateTimeFormat());
        toSave.setLanguage(StringUtils.isBlank(request.language()) ? "en" : request.language());
        toSave.setDarkMode(request.darkMode());

        toSave = saveAndFlush(toSave);

        return UserSettingResponse.builder()
                .data(UserSettingResponseData.builder()
                        .timeZoneString(toSave.getTimeZoneString())
                        .darkMode(toSave.isDarkMode())
                        .dateFormat(toSave.getDateFormat())
                        .dateTimeFormat(toSave.getDateTimeFormat())
                        .language(toSave.getLanguage())
                        .build())
                .build();
    }

    @Transactional(readOnly = true)
    public UserSettingResponse getFromUser() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        UserSetting toSave = findByEnabledAndUser(true, user);
        if(toSave == null) {
            toSave = new UserSetting();
        }

        return UserSettingResponse.builder()
                .data(UserSettingResponseData.builder()
                        .id(toSave.getId())
                        .timeZoneString(toSave.getTimeZoneString())
                        .darkMode(toSave.isDarkMode())
                        .dateFormat(toSave.getDateFormat())
                        .dateTimeFormat(toSave.getDateTimeFormat())
                        .language(toSave.getLanguage())
                        .build())
                .build();
    }

}
