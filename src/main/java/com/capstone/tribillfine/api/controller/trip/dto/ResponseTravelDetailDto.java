package com.capstone.tribillfine.api.controller.trip.dto;

import com.capstone.tribillfine.domain.user.User;
import lombok.Data;

import java.util.List;

@Data
public class ResponseTravelDetailDto {

    List<User> userList;
    List<SimplebudgetDto> budgetList;
    String startDate;
    String endDate;

}
