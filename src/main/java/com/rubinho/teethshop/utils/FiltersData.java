package com.rubinho.teethshop.utils;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FiltersData {
    private String type;
    private String producer;
    private String section;

    public List<String> getAllFilledInFields(){
        List<String> filters = new ArrayList<>();

        if (!type.equals("none")) {
            filters.add("type");
        }

        if (!producer.equals("none")) {
            filters.add("producer");
        }

        if (!section.equals("none")) {
            filters.add("section");
        }


        return filters;
    }

    public Map<String, String> getValuesMap(){
        Map<String, String> values = new HashMap<>();

        if (!type.equals("none")) {
            values.put("type", type);
        }

        if (!producer.equals("none")) {
            values.put("producer", producer);
        }

        if (!section.equals("none")) {
            values.put("section", section);
        }

        return values;
    }
}
