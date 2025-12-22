package com.myorg.covid_analytics.services.security;

import com.myorg.covid_analytics.dto.response.security.PermitFetchData;
import com.myorg.covid_analytics.dto.response.security.PermitFetchDetail;
import com.myorg.covid_analytics.dto.response.security.PermitFetchResponse;
import com.myorg.covid_analytics.models.security.Permit;
import com.myorg.covid_analytics.repositories.security.PermitRepository;
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
public class PermitService extends BaseService<Permit, Long> {

    private final PermitRepository permitRepository;

    @Override
    protected JpaRepository<Permit, Long> getRepository() {
        return permitRepository;
    }

    public void bootstrap() {
        Permit dashboard = create(null, Permit.DASHBOARD, "Dashboard", "Dashboard");
        create(dashboard, Permit.DASHBOARD_TAB_ONE, "Dashboard Tab One", "Dashboard Tab One");
        create(dashboard, Permit.DASHBOARD_TAB_TWO, "Dashboard Tab Two", "Dashboard Tab Two");

        //********************************************************************

        Permit processes = create(null, Permit.PROCESSES_MODULE, "Processes Module", "Processes Module");

        Permit testData = create(processes, Permit.MENU_LOAD_COVID_DATA, "Menu - Load Covid Data", "Menu - Load Covid Data");
        create(testData, Permit.LOAD_COVID_DATA_CREATE, "Create Load Covid Data", "Create Load Covid Data");
        create(testData, Permit.LOAD_COVID_DATA_EDIT, "Edit Load Covid Data", "Edit Load Covid Data");
        create(testData, Permit.LOAD_COVID_DATA_VIEW, "Visualize Load Covid Data", "Visualize Load Covid Data");
        create(testData, Permit.LOAD_COVID_DATA_DELETE, "Delete Load Covid Data", "Delete Load Covid Data");

        //********************************************************************

        Permit security = create(null, Permit.SECURITY_MODULE, "Security Module", "Security Module");

        Permit profiles = create(security, Permit.MENU_PROFILE, "Menu - Profile", "Menu - Profile");
        create(profiles, Permit.PROFILE_CREATE, "Create Profile", "Create Profile");
        create(profiles, Permit.PROFILE_EDIT, "Edit Profile", "Edit Profile");
        create(profiles, Permit.PROFILE_VIEW, "Visualize Profile", "Visualize Profile");
        create(profiles, Permit.PROFILE_DELETE, "Delete Profile", "Delete Profile");

        Permit users = create(security, Permit.MENU_USER, "Menu - User", "Menu - User");
        create(users, Permit.USER_CREATE, "Create User", "Create User");
        create(users, Permit.USER_EDIT, "Edit User", "Edit User");
        create(users, Permit.USER_VIEW, "Visualize User", "Visualize User");
        create(users, Permit.USER_DELETE, "Delete User", "Delete User");
        create(users, Permit.USER_TOKEN, "User Token", "User Token");

        log.info("Created Permits");
    }

    private Permit create(Permit father, String code, String nameI18, String descriptionI18) {
        Permit tmp = findFirstByCodeAndEnabled(true, code);
        if (tmp == null) {
            tmp = saveAndFlush(Permit.builder()
                    .permitFather(father)
                    .code(code)
                    .name(nameI18)
                    .description(descriptionI18)
                    .build());
        }
        return tmp;
    }

    @Transactional(readOnly = true)
    public Permit findFirstByCodeAndEnabled(boolean enabled, String code) {
        return permitRepository.findFirstByEnabledAndCode(enabled, code);
    }

    @Transactional(readOnly = true)
    public List<Permit> findAllByEnabled(boolean enabled) {
        return permitRepository.findAllByEnabled(enabled);
    }

    @Transactional(readOnly = true)
    public List<Permit> findAllByEnabledAndPermitFatherIsNull(boolean enabled) {
        return permitRepository.findAllByEnabledAndPermitFatherIsNull(enabled);
    }

    @Transactional(readOnly = true)
    public List<Permit> findAllByEnabledAndPermitFather(boolean enabled, Permit father) {
        return permitRepository.findAllByEnabledAndPermitFather(enabled, father);
    }

    public PermitFetchResponse fetchPermits() {
        return PermitFetchResponse.builder()
                .data(PermitFetchData.builder()
                        .permits(findAllByEnabled(true).stream().map(it -> PermitFetchDetail
                                .builder()
                                .id(it.getId())
                                .permit(it.getName())
                                .code(it.getCode())
                                .build()).toList())
                        .build())
                .build();
    }
}
