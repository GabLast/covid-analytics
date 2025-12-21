package com.myorg.covid_analytics.models.process;

import com.myorg.covid_analytics.models.BaseModel;
import com.myorg.covid_analytics.models.security.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class CovidLoadHeader extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    private User      user;
    private LocalDate loadedDate;
    @Column(columnDefinition = "text")
    private String    description;
    @Column(columnDefinition = "text")
    private String    jsonURL;
    @Column(columnDefinition = "text")
    private String    jsonString;
}
