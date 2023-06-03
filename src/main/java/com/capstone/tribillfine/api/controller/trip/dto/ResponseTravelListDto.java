package com.capstone.tribillfine.api.controller.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseTravelListDto {
    private Long id;
    private String title;
    private String startDate;
    private String endDate;
    private final List<String> nation = new ArrayList<>();

    //시작일 종료일 x -> 국가로 변경
}
