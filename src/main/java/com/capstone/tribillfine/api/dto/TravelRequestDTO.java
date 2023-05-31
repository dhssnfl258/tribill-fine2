package com.capstone.tribillfine.api.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class TravelRequestDTO {

    private ArrayList<String> nation;
    //목표금액
    private Integer money;
    //시작일
    private LocalDate startDate;
    //종료일
    private LocalDate endDate;

}
