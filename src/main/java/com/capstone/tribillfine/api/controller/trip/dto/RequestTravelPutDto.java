package com.capstone.tribillfine.api.controller.trip.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequestTravelPutDto {
    private String Title;
    private Double amount;
    private String startDate;
    private String endDate;
    private List<String> nations;
}
