package com.myorg.covid_analytics.models.security;

import com.myorg.covid_analytics.models.BaseModel;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Profile extends BaseModel {

    private String name;
    private String description;

    public String toString() {
        return name;
    }

}
