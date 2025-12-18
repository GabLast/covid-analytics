package com.myorg.covid_analytics.models.security;

import com.myorg.covid_analytics.models.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ProfileUser extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
