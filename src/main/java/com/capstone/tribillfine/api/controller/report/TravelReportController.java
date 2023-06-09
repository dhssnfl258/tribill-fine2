package com.capstone.tribillfine.api.controller.report;


import com.capstone.tribillfine.domain.currency.NationCode;
import com.capstone.tribillfine.domain.travel.Travel;
import com.capstone.tribillfine.domain.travel.TravelRepository;
import com.capstone.tribillfine.domain.user.User;
import com.capstone.tribillfine.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
public class TravelReportController {
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;

    public TravelReportController(UserRepository userRepository , TravelRepository travelRepository) {
        this.userRepository = userRepository;
        this.travelRepository = travelRepository;
    }

    @GetMapping("/api/report")
    public ResponseEntity<ArrayList<String>> report(Authentication authentication) {
        String email = authentication.getName();
        log.info("email: {}", email);
        ArrayList<String> nations = new ArrayList<>();
        User user = userRepository.findByEmail(email).get();
        List<Travel> allByUsers = travelRepository.findAllByUsers(user);
        for (Travel allByUser : allByUsers) {
            List<NationCode> nationCodes = allByUser.getNationCodes();
            for (NationCode nationCode : nationCodes) {
                nations.add(nationCode.getNation());
            }
        }

        return  ResponseEntity.ok(nations);
    }
}
