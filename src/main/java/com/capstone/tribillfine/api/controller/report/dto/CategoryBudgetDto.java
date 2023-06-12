package com.capstone.tribillfine.api.controller.report.dto;

import lombok.Data;

@Data
public class CategoryBudgetDto {
    private String title;
    private String registerDate;
    private Double amount;
    private Double KoreaMoney;
    private String nation;
}
