package com.capstone.tribillfine.domain.user.controller;

import com.capstone.tribillfine.domain.user.TkRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserSuccessController {
    private final TkRepository tkRepository ;

    public UserSuccessController(TkRepository tkRepository) {
        this.tkRepository = tkRepository;
    }

    @GetMapping("/login/oauth2/code/oauth2/sign-up")
//receive token from google
    public String googleLogin() {
        List<String> all = tkRepository.findAll();
        String token = "";
        for (String s : all) {
            token = s;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);

//        return ResponseEntity.ok().headers(headers).body("Token sent successfully");
        return "success";
    }
}
