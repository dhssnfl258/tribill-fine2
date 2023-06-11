package com.capstone.tribillfine.api.controller.currency;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PushAlarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // User와 1대1 매핑
    // User의 id를 외래키로 사용
    private Long userId;
    private String NationCode;
    private Double rate;


}
