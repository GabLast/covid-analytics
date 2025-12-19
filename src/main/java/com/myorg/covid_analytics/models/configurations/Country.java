package com.myorg.covid_analytics.models.configurations;

import com.myorg.covid_analytics.models.BaseModel;
import com.myorg.covid_analytics.models.security.User;
import com.myorg.covid_analytics.utils.GlobalConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.TimeZone;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Country extends BaseModel {

    @Getter
    @AllArgsConstructor
    public enum CountryEnum {
        USA("United States of America", "US", "ChIJCzYy5IS16lQRQrfeQ5K5Oxw", "Q30",
                "country/USA", "US", "USA"),
        CANADA("Canada", "CA", "ChIJ2WrMN9MDDUsRpY9Doiq3aJk", "Q16", "country/CAN", "CA",
                "CAN");

        private final String country;
        private final String countryCode;
        private final String placeId;
        private final String wikiDataId;
        private final String dataCommonsId;
        private final String iso_3166_1_alpha_2;
        private final String iso_3166_1_alpha_3;
    }

    private String country;
    private String countryCode;
    private String placeId;
    private String wikiDataId;
    private String dataCommonsId;
    private String iso_3166_1_alpha_2;
    private String iso_3166_1_alpha_3;
}
