package com.capstone.tribillfine.domain.user.controller;

import com.capstone.tribillfine.domain.user.dto.UserSignUpDto;
import com.capstone.tribillfine.domain.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/sign")
    public String hi(){
        return "hihi";
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

    @GetMapping("/jwt-test2")
    public String jwtTest2() {
        return "jwtTest 요청 성공";
    }
}
