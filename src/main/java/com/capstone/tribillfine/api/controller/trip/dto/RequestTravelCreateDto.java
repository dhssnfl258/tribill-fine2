package com.capstone.tribillfine.api.controller.trip.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestTravelCreateDto {
    //예산
    private Double amount;
    private String startDate;
    private String endDate;
    private List<String> nations;
}
