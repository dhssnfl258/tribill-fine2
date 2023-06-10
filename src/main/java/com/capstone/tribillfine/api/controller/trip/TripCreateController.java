package com.capstone.tribillfine.api.controller.trip;


import com.capstone.tribillfine.api.controller.trip.tripcreate.AmountDto;
import com.capstone.tribillfine.api.controller.trip.tripcreate.DatesDto;
import com.capstone.tribillfine.api.controller.trip.tripcreate.NationDto;
import com.capstone.tribillfine.domain.currency.NationCode;
import com.capstone.tribillfine.domain.currency.NationCodeRepository;
import com.capstone.tribillfine.domain.travel.Travel;
import com.capstone.tribillfine.domain.travel.TravelRepository;
import com.capstone.tribillfine.domain.user.User;
import com.capstone.tribillfine.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@RestController
public class TripCreateController {

    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final NationCodeRepository nationCodeRepository;

    public TripCreateController(UserRepository userRepository, TravelRepository travelRepository, NationCodeRepository nationCodeRepository) {
        this.userRepository = userRepository;
        this.travelRepository = travelRepository;
        this.nationCodeRepository = nationCodeRepository;
    }

    @PostMapping("/api/trip/create/nation")
    public ResponseEntity<?> createNation(Authentication authentication, @RequestBody NationDto nations){
        log.info("nations: {}", nations.getNations().toString());
        String email = authentication.getName();
        log.info("email: {}", email);
        Travel travel = new Travel();

        travel.setTitle("My Trip");

        User user = userRepository.findByEmail(email).get();
        travel.getUsers().add(user);
        List<NationCode> nationCodes = new ArrayList<>();
        for (String nation : nations.getNations()) {
            NationCode nationCode = nationCodeRepository.findByNation(nation).orElseThrow(() -> new NoSuchElementException());
            nationCodes.add(nationCode);
        }

        travel.setNationCodes(nationCodes);
        for (NationCode nationCode : nationCodes) {
            log.info("nationCode: {}", nationCode.getNation());
        }

        travelRepository.save(travel);

        return  ResponseEntity.ok(travel.getId());
    }
    //Post add startdate and endDate;
    @PostMapping("/api/trip/{tripId}/create/date")
    public ResponseEntity<?> createDate( @PathVariable Long tripId, @RequestBody DatesDto datesDto) {

//            String email = authentication.getName();
//            log.info("email: {}", email);
//            User user = userRepository.findByEmail(email).get();
            Travel travel = travelRepository.findById(tripId).get();
            travel.setStartDate(LocalDate.parse(datesDto.getStartDate()));
            log.info("datesDto.getStartDate(): {}", datesDto.getStartDate());
            travel.setEndDate(LocalDate.parse(datesDto.getEndDate()));
            log.info("datesDto.getEndDate(): {}", datesDto.getEndDate());

            travelRepository.save(travel);
            travel.getId();
            log.info("travel.getId(): {}", travel.getId());
            return  ResponseEntity.ok(travel.getId());
    }

    //postmapping set amounts
    @PostMapping("/api/trip/{tripId}/create/amount")
    public ResponseEntity<?> createAmount(@PathVariable Long tripId, @RequestBody AmountDto amounts) {
        Travel travel = travelRepository.findById(tripId).get();
        log.info("amounts: {}", amounts.getAmount());
        travel.setAmount(amounts.getAmount());
        //paresLong to get amount
        travelRepository.save(travel);

        return  ResponseEntity.ok(travel.getId());
    }
}
