package com.capstone.tribillfine.api.controller.trip.dto.bg;

import com.capstone.tribillfine.domain.account.CATEGORY;
import com.capstone.tribillfine.domain.account.Type;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class ResponseBudgetDetailsDto {
    //타이틀
    private String title;
    //한화
    private Double KoreaMoney;
    //설명
    private String description;
    //사용국가
    private String nation;
    //사용국가 돈
    private Double nationMoney;
    //작성일
    private LocalDate registerDate;
    //함께 쓴사람
    private List<String> SpendWith;
    private List<String> WhoPay;
    //이미지
    private String FileUrl;
    //타입
    private Type type;
    //카테고리
    private CATEGORY category;

}
