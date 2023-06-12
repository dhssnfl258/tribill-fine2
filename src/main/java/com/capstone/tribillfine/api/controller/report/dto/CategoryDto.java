package com.capstone.tribillfine.api.controller.report.dto;

import lombok.Data;

@Data
public class CategoryDto {
    /**
     *     숙소("Accommodation"),
     *     항공("Flight"),
     *     교통("Transportation"),
     *     식비("Food"),
     *     관광("Sightseeing"),
     *     쇼핑("Shopping"),
     *     기타("Others");
     */
    private Double food = 0D;
    private Double accommodation = 0D;
    private Double flight = 0D;

    private Double transportation = 0D;

    private Double sightseeing = 0D;

    private Double shopping = 0D;
    private Double others = 0D;
    private Double total = 0D;

    //나머지
    private Double rest = 0D;

}
