package com.myorg.covid_analytics.models.configurations;

import com.myorg.covid_analytics.models.BaseModel;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

        private final String name;
        private final String countryCode;
        private final String placeId;
        private final String wikiDataId;
        private final String dataCommonsId;
        private final String iso_3166_1_alpha_2;
        private final String iso_3166_1_alpha_3;
    }

    private String name;
    private String countryCode;
    private String placeId;
    private String wikiDataId;
    private String dataCommonsId;
    private String iso_3166_1_alpha_2;
    private String iso_3166_1_alpha_3;
}
