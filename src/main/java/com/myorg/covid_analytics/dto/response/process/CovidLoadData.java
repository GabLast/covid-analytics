package com.myorg.covid_analytics.dto.response.process;

import com.myorg.covid_analytics.dao.CovidLoadDao;
import com.myorg.covid_analytics.dao.CovidRowDao;
import com.myorg.covid_analytics.dto.JsonResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record CovidLoadData(
        Long headerId,
        String loadDate,
        String description,
        List<CovidRowDao> details
) implements JsonResponse {
}
