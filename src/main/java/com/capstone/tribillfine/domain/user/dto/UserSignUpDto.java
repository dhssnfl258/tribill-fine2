package com.capstone.tribillfine.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSignUpDto {

    private String email;
    private String password;
    private String nickname;
//    private int age;
//    private String city;
}
