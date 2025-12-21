package com.myorg.covid_analytics.dao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myorg.covid_analytics.dto.JsonResponse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public record CovidDataSet(
        List<String> columns, List<List<Object>> data
) implements JsonResponse {

    @JsonCreator
    public CovidDataSet(@JsonProperty("columns") List<String> columns,
            @JsonProperty("data") List<List<Object>> data) {

        this.columns = List.copyOf(columns);
        this.data = List.copyOf(data);
    }

    public Map<String, Object> rowAsMap(int rowIndex) {
        List<Object> row = data.get(rowIndex);

        if (row.size() != columns.size()) {
            throw new IllegalStateException(
                    "Row size (" + row.size() + ") does not match columns size ("
                            + columns.size() + ")");
        }

        Map<String, Object> mapped = new LinkedHashMap<>();
        for (int i = 0; i < columns.size(); i++) {
            mapped.put(columns.get(i), row.get(i));
        }
        return mapped;
    }
}
