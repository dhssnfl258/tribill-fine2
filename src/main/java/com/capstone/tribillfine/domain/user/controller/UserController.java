package com.capstone.tribillfine.domain.user.controller;

import com.capstone.tribillfine.domain.user.TkRepository;
import com.capstone.tribillfine.domain.user.dto.UserSignUpDto;
import com.capstone.tribillfine.domain.user.service.UserService;
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

//    @GetMapping("/login/oauth2/code/oauth2/sign-up")
//    //receive token from google
//    public String googleLogin() {
//        List<String> all = tkRepository.findAll();
//        String tok = "";
//        for (String s : all) {
//            tok = s;
//        }
//
//        tkRepository.deleteAll();
//        return tok;
//    }



//    @GetMapping("/login/oauth2/code/oauth2/sign-up")
////receive token from google
//    public String googleLogin() {
//        List<String> all = tkRepository.findAll();
//        String token = "";
//        for (String s : all) {
//            token = s;
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + token);
//
////        return ResponseEntity.ok().headers(headers).body("Token sent successfully");
//        return "success";
//    }
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
