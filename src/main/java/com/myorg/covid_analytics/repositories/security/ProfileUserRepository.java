package com.myorg.covid_analytics.repositories.security;

import com.myorg.covid_analytics.models.security.ProfileUser;
import com.myorg.covid_analytics.models.security.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileUserRepository extends JpaRepository<ProfileUser, Long> {

    @EntityGraph(attributePaths = {"profile"})
    List<ProfileUser> findAllByEnabledAndUser(boolean enabled, User user);
}
