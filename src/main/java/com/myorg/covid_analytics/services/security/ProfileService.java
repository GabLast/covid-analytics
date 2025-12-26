package com.myorg.covid_analytics.services.security;

import com.myorg.covid_analytics.converter.PaginationConverter;
import com.myorg.covid_analytics.dto.PaginationObject;
import com.myorg.covid_analytics.dto.request.security.ProfileFilterRequest;
import com.myorg.covid_analytics.dto.request.security.ProfileRequest;
import com.myorg.covid_analytics.dto.response.CountResponse;
import com.myorg.covid_analytics.dto.response.CountResponseData;
import com.myorg.covid_analytics.dto.response.security.PermitRow;
import com.myorg.covid_analytics.dto.response.security.ProfileFetchData;
import com.myorg.covid_analytics.dto.response.security.ProfileFetchDataDetails;
import com.myorg.covid_analytics.dto.response.security.ProfileFetchResponse;
import com.myorg.covid_analytics.dto.response.security.ProfileFilterData;
import com.myorg.covid_analytics.dto.response.security.ProfileFilterDataDetails;
import com.myorg.covid_analytics.dto.response.security.ProfileFilterResponse;
import com.myorg.covid_analytics.dto.response.security.ProfileResponse;
import com.myorg.covid_analytics.dto.response.security.ProfileResponseData;
import com.myorg.covid_analytics.exceptions.ResourceNotFoundException;
import com.myorg.covid_analytics.models.configurations.UserSetting;
import com.myorg.covid_analytics.models.security.Permit;
import com.myorg.covid_analytics.models.security.Profile;
import com.myorg.covid_analytics.models.security.ProfilePermit;
import com.myorg.covid_analytics.repositories.security.ProfileRepository;
import com.myorg.covid_analytics.services.BaseService;
import com.myorg.covid_analytics.utils.OffsetBasedPageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
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
public class ProfileService extends BaseService<Profile, Long> {

    private final ProfileRepository    profileRepository;
    private final ProfilePermitService profilePermitService;
    private final PermitService        permitService;

    @Override
    protected JpaRepository<Profile, Long> getRepository() {
        return profileRepository;
    }

    @Transactional(readOnly = true)
    public List<Profile> findAllFilter(Boolean enabled, String name, String description,
            Integer limit, Integer offset, Sort sort) {
        return profileRepository.findAllFilter(enabled, name, description,
                sort == null ? new OffsetBasedPageRequest(limit, offset)
                             : new OffsetBasedPageRequest(limit, offset, sort));
    }

    @Transactional(readOnly = true)
    public Integer countAllFilter(Boolean enabled, String name, String description) {
        return profileRepository.countAllFilter(enabled, name, description);
    }

    public Profile create(Profile profile) {
        if (profile == null) {
            throw new ResourceNotFoundException("Profile is null");
        }

        profile = saveAndFlush(profile);

        return profile;
    }

    public void delete(Profile profile, UserSetting userSetting) {
        if (profile == null) {
            throw new ResourceNotFoundException("Profile is null");
        }

        for (ProfilePermit profilePermit : profilePermitService.findAllByEnabledAndProfile(
                true, profile)) {
            profilePermitService.delete(profilePermit);
        }

        disable(profile);
    }

    @Transactional(readOnly = true)
    public List<Profile> findAllByEnabled(boolean enabled) {
        return profileRepository.findAllByEnabled(enabled);
    }

    public ProfileResponse saveProfileRequest(ProfileRequest request) {

        Profile profile = get(request.id()).orElse(new Profile());

        profile.setName(request.name());
        profile.setDescription(request.description());

        profile = create(profile);

        if(!CollectionUtils.isEmpty(request.permitsDelete())) {
            for (Long it : request.permitsDelete().stream().map(PermitRow::profilePermitId)
                    .toList()) {
                Optional<ProfilePermit> tmp = profilePermitService.get(it);
                tmp.ifPresent(profilePermitService::delete);
            }
        }

        List<ProfilePermit> permits = new ArrayList<>();
        if(!CollectionUtils.isEmpty(request.permits())) {
            for (PermitRow it : request.permits()) {
                Optional<Permit> tmp = permitService.get(it.id());
                if (tmp.isPresent() && (it.profilePermitId() == null
                        || it.profilePermitId() == 0L)) {
                    ProfilePermit profileUser = new ProfilePermit();
                    profileUser.setProfile(profile);
                    profileUser.setPermit(tmp.get());
                    permits.add(profileUser);
                }
            }
        }

        if (!permits.isEmpty()) {
            permits = profilePermitService.saveAllAndFlush(permits);
        }

        List<PermitRow> permitRows = permits.stream()
                .map(it -> PermitRow.builder().profilePermitId(it.getId())
                        .id(it.getPermit().getId()).permit(it.getPermit().getName())
                        .code(it.getPermit().getCode()).build()).toList();

        return generateResponse(profile, permitRows);
    }

    private ProfileResponse generateResponse(Profile profile, List<PermitRow> permits) {
        return ProfileResponse.builder()
                .data(ProfileResponseData.builder()
                        .id(profile.getId())
                        .name(profile.getName())
                        .description(profile.getDescription()).permits(permits).build())
                .build();
    }

    public ProfileResponse getProfileById(Long id) {
        Optional<Profile> header = get(id);
        if (header.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Object by ID [" + id + "] does not exist");
        }

        List<PermitRow> permitRows =
                profilePermitService.findAllByEnabledAndProfile(true, header.get())
                        .stream()
                        .map(it -> PermitRow.builder().profilePermitId(it.getId())
                                .id(it.getPermit().getId())
                                .permit(it.getPermit().getName())
                                .code(it.getPermit().getCode()).build()).toList();

        return generateResponse(header.get(), permitRows);
    }

    public ProfileFilterResponse findAllProfileFilter(ProfileFilterRequest request) {

        PaginationObject paginationObject =
                PaginationConverter.fromSimpleValues(request.getSortProperty(),
                        request.getSortOrder(), request.getOffset(), request.getLimit());

        List<ProfileFilterDataDetails> dataList =
                findAllFilter(request.isEnabled(), request.getName(),
                        request.getDescription(), paginationObject.limit(),
                        paginationObject.offset(), paginationObject.sort()).stream()
                        .map(it -> ProfileFilterDataDetails.builder().id(it.getId())
                                .id(it.getId()).name(it.getName())
                                .description(it.getDescription()).build()).toList();

        return ProfileFilterResponse.builder()
                .data(ProfileFilterData.builder().dataList(dataList).build()).build();

    }

    public CountResponse countAllProfileFilter(ProfileFilterRequest request) {

        return CountResponse.builder().data(CountResponseData.builder()
                .total(countAllFilter(request.isEnabled(), request.getName(),
                        request.getDescription())).build()).build();
    }

    public ProfileResponse delete(Long id) {
        Optional<Profile> header = get(id);
        if (header.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Object by ID [" + id + "] does not exist");
        }

        List<ProfilePermit> details =
                profilePermitService.findAllByEnabledAndProfile(true, header.get());

        for (ProfilePermit it : details) {
            profilePermitService.delete(it);
        }

        disable(header.get());

        return ProfileResponse.builder()
                .data(ProfileResponseData.builder().id(id).name(header.get().getName())
                        .build()).build();
    }

    public ProfileFetchResponse fetchProfiles() {
        return ProfileFetchResponse.builder().data(ProfileFetchData.builder().profiles(
                findAllByEnabled(true).stream()
                        .map(it -> ProfileFetchDataDetails.builder().id(it.getId())
                                .name(it.getName()).build()).toList()).build()).build();
    }
}
