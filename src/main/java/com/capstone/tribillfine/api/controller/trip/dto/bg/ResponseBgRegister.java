package com.capstone.tribillfine.api.controller.trip.dto.bg;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ResponseBgRegister {
    List<String> member;
    Map<String, Double>  exchangeRate;
}
