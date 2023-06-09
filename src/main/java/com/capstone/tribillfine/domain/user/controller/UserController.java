package com.capstone.tribillfine.domain.user.controller;

import com.capstone.tribillfine.domain.user.TkRepository;
import com.capstone.tribillfine.domain.user.dto.UserSignUpDto;
import com.capstone.tribillfine.domain.user.service.UserService;
import com.capstone.tribillfine.jwt.service.JwtService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TkRepository tkRepository;
    private final JwtService jwtService;

    @PostMapping("/sign-up")
    public ResponseEntity<TokDto> signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
//        System.out.println(userSignUpDto.getEmail());
        TokDto tokDto = new TokDto();

        String accessToken = jwtService.createAccessToken(userSignUpDto.getEmail());
        tokDto.setToken(accessToken);
        if(userService.signUp(userSignUpDto).equals("fail")){
            return ResponseEntity.ok(tokDto);
//            return accessToken;
        }
        return ResponseEntity.ok(tokDto);
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
    @GetMapping("/login/oauth2/code/oauth2/tok")
//receive token from google
    public ResponseEntity<String> getTok() {
        List<String> all = tkRepository.findAll();
        String token = "";
        for (String s : all) {
            token = s;
        }

        tkRepository.deleteAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

        return ResponseEntity.ok(token);
    }


}
