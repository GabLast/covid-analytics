package com.myorg.covid_analytics.services.security;

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
        create(dashboard, Permit.DASHBOARD_TAB, "Dashboard Tab", "Dashboard Tab");

        //********************************************************************

        Permit processes = create(null, Permit.PROCESSES_MODULE, "Processes Module", "Processes Module");

        Permit testData = create(processes, Permit.MENU_LOAD_COVID_DATA, "Menu - Test Data", "Menu - Test Data");
        create(testData, Permit.LOAD_COVID_DATA_CREATE, "Menu - Create Test Data", "Menu - Create Test Data");
        create(testData, Permit.LOAD_COVID_DATA_EDIT, "Menu - Edit Test Data", "Menu - Edit Test Data");
        create(testData, Permit.LOAD_COVID_DATA_VIEW, "Menu - Visualize Test Data", "Menu - Visualize Test Data");
        create(testData, Permit.LOAD_COVID_DATA_DELETE, "Menu - Delete Test Data", "Menu - Delete Test Data");

        //********************************************************************

        Permit security = create(null, Permit.SECURITY_MODULE, "Security Module", "Security Module");

        Permit profiles = create(security, Permit.MENU_PROFILE, "Menu - Profile", "Menu - Profile");
        create(profiles, Permit.PROFILE_CREATE, "Menu - Create Profile", "Menu - Create Profile");
        create(profiles, Permit.PROFILE_EDIT, "Menu - Edit Profile", "Menu - Edit Profile");
        create(profiles, Permit.PROFILE_VIEW, "Menu - Visualize Profile", "Menu - Visualize Profile");
        create(profiles, Permit.PROFILE_DELETE, "Menu - Delete Profile", "Menu - Delete Profile");

        Permit users = create(security, Permit.MENU_USER, "Menu - User", "Menu - User");
        create(users, Permit.USER_CREATE, "Menu - Create User", "Menu - Create User");
        create(users, Permit.USER_EDIT, "Menu - Edit User", "Menu - Edit User");
        create(users, Permit.USER_VIEW, "Menu - Visualize User", "Menu - Visualize User");
        create(users, Permit.USER_DELETE, "Menu - Delete User", "Menu - Delete User");
        create(users, Permit.USER_TOKEN, "Menu - Create User", "Menu - Create User");

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

    public Permit findFirstByCodeAndEnabled(boolean enabled, String code) {
        return permitRepository.findFirstByEnabledAndCode(enabled, code);
    }

    public List<Permit> findAllByEnabled(boolean enabled) {
        return permitRepository.findAllByEnabled(enabled);
    }

    public List<Permit> findAllByEnabledAndPermitFatherIsNull(boolean enabled) {
        return permitRepository.findAllByEnabledAndPermitFatherIsNull(enabled);
    }

    public List<Permit> findAllByEnabledAndPermitFather(boolean enabled, Permit father) {
        return permitRepository.findAllByEnabledAndPermitFather(enabled, father);
    }

}
