package com.capstone.tribillfine.api.controller.currency;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ResponseCurrencyDto {
    private String nation;
    private Double rate;
    private List<String> labels;
    private List<Double> data;
}
