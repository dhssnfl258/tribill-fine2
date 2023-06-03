package com.capstone.tribillfine.api.controller.trip.dto;

import com.capstone.tribillfine.domain.account.Budget;
import com.capstone.tribillfine.domain.user.User;
import lombok.Data;

import java.util.List;

@Data
public class ResponseTravelDetailDto {

    List<User> userList;
    List<Budget> budgetList;
    String startDate;
    String endDate;

}
