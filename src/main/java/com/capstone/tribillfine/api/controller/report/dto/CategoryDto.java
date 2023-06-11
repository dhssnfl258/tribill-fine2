package com.capstone.tribillfine.api.controller.report.dto;

import lombok.Data;

@Data
public class CategoryDto {
    private Double food;
    private Double accommodation;
    private Double flight;

    private Double transportation;

    private Double sightseeing;

    private Double shopping;
    private Double others;
    private Double total;

    /**
     *     숙소("Accommodation"),
     *     항공("Flight"),
     *     교통("Transportation"),
     *     식비("Food"),
     *     관광("Sightseeing"),
     *     쇼핑("Shopping"),
     *     기타("Others");
     */

    //나머지
    private Double rest;

}
