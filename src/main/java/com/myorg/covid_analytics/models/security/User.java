package com.myorg.covid_analytics.models.security;

import com.myorg.covid_analytics.models.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sec_user")
@Builder
@AllArgsConstructor
public class User extends BaseModel {

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    private String name;
    @Column(unique = true, nullable = false)
    private String mail;

    @Column(nullable = false)
    private boolean admin = false;

    public String toString() {
        return username;
    }
}
