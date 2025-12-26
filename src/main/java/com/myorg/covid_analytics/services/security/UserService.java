package com.myorg.covid_analytics.services.security;

import com.myorg.covid_analytics.converter.PaginationConverter;
import com.myorg.covid_analytics.dto.PaginationObject;
import com.myorg.covid_analytics.dto.request.security.UserFilterRequest;
import com.myorg.covid_analytics.dto.request.security.UserRequest;
import com.myorg.covid_analytics.dto.response.CountResponse;
import com.myorg.covid_analytics.dto.response.CountResponseData;
import com.myorg.covid_analytics.dto.response.security.ProfileRow;
import com.myorg.covid_analytics.dto.response.security.UserFilterData;
import com.myorg.covid_analytics.dto.response.security.UserFilterDataDetails;
import com.myorg.covid_analytics.dto.response.security.UserFilterResponse;
import com.myorg.covid_analytics.dto.response.security.UserFindAllData;
import com.myorg.covid_analytics.dto.response.security.UserFindAllDataDetails;
import com.myorg.covid_analytics.dto.response.security.UserFindAllResponse;
import com.myorg.covid_analytics.dto.response.security.UserResponse;
import com.myorg.covid_analytics.dto.response.security.UserResponseData;
import com.myorg.covid_analytics.exceptions.InvalidActionException;
import com.myorg.covid_analytics.exceptions.InvalidDataFormat;
import com.myorg.covid_analytics.exceptions.ResourceExistsException;
import com.myorg.covid_analytics.exceptions.ResourceNotFoundException;
import com.myorg.covid_analytics.models.security.Profile;
import com.myorg.covid_analytics.models.security.ProfileUser;
import com.myorg.covid_analytics.models.security.Token;
import com.myorg.covid_analytics.models.security.User;
import com.myorg.covid_analytics.repositories.security.TokenRepository;
import com.myorg.covid_analytics.repositories.security.UserRepository;
import com.myorg.covid_analytics.services.BaseService;
import com.myorg.covid_analytics.utils.OffsetBasedPageRequest;
import com.myorg.covid_analytics.utils.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService extends BaseService<User, Long> {

    private final UserRepository     userRepository;
    private final ProfileService     profileService;
    private final ProfileUserService profileUserService;
    private final PasswordEncoder    passwordEncoder;
    private final TokenRepository    tokenRepository;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    public void bootstrap(AuthenticationService authenticationService) {
        User user = findByUsername("admin");
        if (user == null) {
            user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("123"));
            user.setName("Administrator");
            user.setAdmin(true);
            user.setMail("mail@mail.com");
            user = saveAndFlush(user);
        }

        try {
            authenticationService.generateToken(user);
        } catch (Exception e) {
            log.error("Error when generating the token for the admin user: "
                    + e.getMessage());
        }

        log.info("Created admin user");
    }

    @Transactional(readOnly = true)
    public User findByUsername(String string) {
        return userRepository.findByUsername(string);
    }

    @Transactional(readOnly = true)
    public User findByMail(String string) {
        return userRepository.findByMail(string);
    }

    @Transactional(readOnly = true)
    public User findByUsernameOrMail(String string) {
        return userRepository.findByUsernameOrMail(string);
    }

    @Transactional(readOnly = true)
    public List<User> findAllFilter(Boolean enabled, String name, String mail,
            Boolean admin, int limit, int offset, Sort sort) {
        return userRepository.findAllFilter(enabled, name, mail, admin,
                sort == null ? new OffsetBasedPageRequest(limit, offset)
                             : new OffsetBasedPageRequest(limit, offset, sort));
    }

    @Transactional(readOnly = true)
    public Integer countAllFilter(Boolean enabled, String name, String mail,
            Boolean admin) {
        return userRepository.countAllFilter(enabled, name, mail, admin);
    }

    public User createUser(UserRequest request) {

        String username =
                Utilities.cleanStringAndDeleteWhitespaceToLowerCase(request.username());
        String mail =
                Utilities.cleanStringAndDeleteWhitespaceToLowerCase(request.email());

        if (StringUtils.isBlank(username)) {
            throw new InvalidDataFormat("The username can not be blank");
        }

        if (StringUtils.isBlank(mail)) {
            throw new InvalidDataFormat("The mail can not be blank");
        }

        User tmp;

        tmp = findByUsername(request.username());

        if (tmp != null && !tmp.getId().equals(request.id())) {
            throw new ResourceExistsException(
                    "This username has already been taken. Please select a new one.");
        }

        tmp = findByMail(request.email());

        if (tmp != null && !tmp.getId().equals(request.id())) {
            throw new ResourceExistsException(
                    "This mail has already been taken. Please select a new one.");
        }

        if (tmp != null && !request.password().equals(tmp.getPassword())) {
            tmp.setPassword(passwordEncoder.encode(request.password()));
        }

        tmp = get(request.id()).orElse(null);
        if (tmp == null) {
            tmp = new User();
            tmp.setPassword(passwordEncoder.encode(request.password()));
        }

        tmp.setUsername(username);
        tmp.setName(request.name());
        tmp.setMail(mail);
        tmp.setAdmin(request.admin());

        tmp = saveAndFlush(tmp);

        return tmp;
    }

    public void delete(User user) {
        if (user == null) {
            throw new ResourceNotFoundException("User can not be null");
        }

        for (ProfileUser profile : profileUserService.findAllByEnabledAndUser(true,
                user)) {
            profileUserService.delete(profile);
        }

        disable(user);
    }

    public UserFindAllResponse fetchUsers() {
        return UserFindAllResponse.builder().data(UserFindAllData.builder().dataList(
                findAll().stream()
                        .map(it -> UserFindAllDataDetails.builder().id(it.getId())
                                .name(it.getName()).build()).toList()).build()).build();
    }

    private UserResponse generateResponse(User user, List<ProfileUser> profiles) {
        return UserResponse.builder().data(UserResponseData.builder().id(user.getId())
                .username(user.getUsername()).name(user.getName()).mail(user.getMail())
                .password(user.getPassword()).admin(user.isAdmin()).profiles(
                        profiles != null ? profiles.stream()
                                .map(it -> ProfileRow.builder().profileUserId(it.getId())
                                        .id(it.getProfile().getId())
                                        .profile(it.getProfile().getName()).build())
                                .toList() : null).build()).build();
    }

    public UserResponse saveUserRequest(UserRequest request) {

        User user = createUser(request);

        if (!CollectionUtils.isEmpty(request.profilesDelete())) {
            for (Long it : request.profilesDelete().stream()
                    .map(ProfileRow::profileUserId).toList()) {
                Optional<ProfileUser> tmp = profileUserService.get(it);
                tmp.ifPresent(profileUserService::delete);
            }
        }

        List<ProfileUser> profiles = new ArrayList<>();
        for (ProfileRow it : request.profiles()) {
            Optional<Profile> tmp = profileService.get(it.id());
            if (tmp.isPresent() && (it.profileUserId() == null
                    || it.profileUserId() == 0L)) {
                ProfileUser profileUser = new ProfileUser();
                profileUser.setUser(user);
                profileUser.setProfile(tmp.get());
                profiles.add(profileUser);
            }
        }

        if (!profiles.isEmpty()) {
            profiles = profileUserService.saveAllAndFlush(profiles);
        }

        return generateResponse(user, profiles);
    }

    public UserFilterResponse findAllUserFilter(UserFilterRequest request) {

        PaginationObject paginationObject =
                PaginationConverter.fromSimpleValues(request.getSortProperty(),
                        request.getSortOrder(), request.getOffset(), request.getLimit());

        List<UserFilterDataDetails> dataList =
                findAllFilter(request.isEnabled(), request.getName(), request.getMail(),
                        null, paginationObject.limit(), paginationObject.offset(),
                        paginationObject.sort()).stream()
                        .map(it -> UserFilterDataDetails.builder().id(it.getId())
                                .id(it.getId()).name(it.getName()).email(it.getMail())
                                .admin(it.isAdmin()).build()).toList();

        return UserFilterResponse.builder()
                .data(UserFilterData.builder().dataList(dataList).build()).build();

    }

    public CountResponse countAllUserFilter(UserFilterRequest request) {

        return CountResponse.builder().data(CountResponseData.builder()
                .total(countAllFilter(request.isEnabled(), request.getName(),
                        request.getMail(), null)).build()).build();
    }

    public UserResponse delete(Long id) {
        Optional<User> header = get(id);
        if (header.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Object by ID [" + id + "] does not exist");
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        if (header.get().isAdmin() && !user.isAdmin()) {
            throw new InvalidActionException(
                    "A non admin user can not delete any user that has admin privileges");
        }

        Token token = tokenRepository.findFirstByUserAndEnabled(header.get(), true);
        if (token != null) {
            tokenRepository.delete(token);
        }

        delete(header.get());

        return generateResponse(header.get(), null);
    }

    public UserResponse getUserById(Long id) {
        Optional<User> header = get(id);
        if (header.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Object by ID [" + id + "] does not exist");
        }

        return generateResponse(header.get(),
                profileUserService.findAllByEnabledAndUser(true, header.get()));
    }

    public UserResponse getUserByUsername(String username) {
        User header = findByUsernameOrMail(username);
        if (header == null) {
            throw new ResourceNotFoundException(
                    "User by username or mail [" + username + "] does not exist");
        }

        return generateResponse(header, null);
    }
}
