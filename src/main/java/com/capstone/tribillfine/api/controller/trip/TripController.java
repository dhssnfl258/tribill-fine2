package com.capstone.tribillfine.api.controller.trip;



import com.capstone.tribillfine.api.controller.trip.dto.RequestTravelCreateDto;
import com.capstone.tribillfine.api.controller.trip.dto.RequestTravelPutDto;
import com.capstone.tribillfine.api.controller.trip.dto.ResponseTravelListDto;
import com.capstone.tribillfine.domain.account.BudgetRepository;
import com.capstone.tribillfine.domain.currency.NationCode;
import com.capstone.tribillfine.domain.currency.NationCodeRepository;
import com.capstone.tribillfine.domain.travel.Travel;
import com.capstone.tribillfine.domain.travel.TravelRepository;
import com.capstone.tribillfine.domain.user.User;
import com.capstone.tribillfine.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/trip")
public class TripController {


    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final NationCodeRepository nationCodeRepository;
    private final BudgetRepository budgetRepository;

    @Autowired
    public TripController(UserRepository userRepository, TravelRepository travelRepository,
                          BudgetRepository budgetRepository,
                          NationCodeRepository nationCodeRepository) {
        this.userRepository = userRepository;
        this.travelRepository = travelRepository;
        this.budgetRepository = budgetRepository;
        this.nationCodeRepository = nationCodeRepository;
    }

    @PostMapping("/user/testTravel")
    public void tester(Authentication authentication, @RequestBody RequestTravelCreateDto requestTravelCreateDto) {
        System.out.println(requestTravelCreateDto.getAmount());
        System.out.println(requestTravelCreateDto.getStartDate());
        System.out.println(requestTravelCreateDto.getEndDate());
        for (String nation : requestTravelCreateDto.getNations()) {
            System.out.println(nation);
        }
        String email = authentication.getName();
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.get();
        System.out.println(user.getName());
        Travel trip = new Travel();
        trip.setTitle("나의 여행");
        LocalDate startDate = LocalDate.parse(requestTravelCreateDto.getStartDate());
        LocalDate endDate = LocalDate.parse(requestTravelCreateDto.getEndDate());
        System.out.println(startDate);
        System.out.println(endDate);

        trip.setStartDate(startDate);
        trip.setEndDate(endDate);
        List<NationCode> nationCodes = new ArrayList<>();
        for (String nation : requestTravelCreateDto.getNations()) {
//            NationCode nationCode = nationCodeRepository.findByNation(nation).orElse(null);
//            if (nationCode != null) {
//                System.out.println(nationCode.getCode());
//                trip.getNationCodes().add(nationCode);
//            }
            Optional<NationCode> nationCodeOptional = nationCodeRepository.findByNation(nation);
            nationCodeOptional.ifPresent(nationCodes::add);
        }
        trip.setNationCodes(nationCodes);

        travelRepository.save(trip);
    }

//    @PostMapping("/user/createTravel")
//    public ResponseEntity<?> createTripV2(Authentication authentication, @RequestBody RequestTravelCreateDto requestTravelCreateDto) {
//        String email = authentication.getName();
//        Optional<User> byEmail = userRepository.findByEmail(email);
//        if (byEmail.isPresent()) {
//            User user = byEmail.get();
//            Travel trip = new Travel();
//            trip.setTitle("나의 여행" + trip.getId());
//            LocalDate startDate = LocalDate.parse(requestTravelCreateDto.getStartDate());
//            LocalDate endDate =LocalDate.parse(requestTravelCreateDto.getEndDate());
//            trip.setStartDate(startDate);
//            trip.setEndDate(endDate);
//            trip.setUser(user);
//            List<NationCode> nationCodeList = new ArrayList<>();
//            for (String nation : requestTravelCreateDto.getNations()) {
//                NationCode nationCode = nationCodeRepository.findByNation(nation).get();
//                nationCodeList.add(nationCode);
//            }
//
//            trip.setNationCodes(nationCodeList);
//            travelRepository.save(trip);
//            return ResponseEntity.ok(trip);
//
//        } else {
//            // 로그인이 필요한 경우에 대한 처리
//            String message = "로그인이 필요합니다.";
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
//        }
//    }
@PostMapping("/user/createTravel")
public ResponseEntity<?> createTravel(Authentication authentication, @RequestBody RequestTravelCreateDto requestTravelCreateDto) {
    String email = authentication.getName();
    Optional<User> byEmail = userRepository.findByEmail(email);
    if (byEmail.isPresent()) {
        User user = byEmail.get();

        // 여행 생성
        Travel travel = new Travel();
        travel.setTitle("나의 여행");

        LocalDate startDate = LocalDate.parse(requestTravelCreateDto.getStartDate());
        LocalDate endDate = LocalDate.parse(requestTravelCreateDto.getEndDate());
        travel.setAmount(requestTravelCreateDto.getAmount());
        travel.setStartDate(startDate);
        travel.setEndDate(endDate);
        travel.getUsers().add(user); // 다른 사용자를 여행에 추가
        travelRepository.save(travel);
        List<NationCode> nationCodes = new ArrayList<>();
        for (String nation : requestTravelCreateDto.getNations()) {
            //check
            NationCode nationCode = nationCodeRepository.findByNation(nation).orElseThrow(() -> new NoSuchElementException());
            nationCodes.add(nationCode);
        }
        travel.setNationCodes(nationCodes);
        travelRepository.save(travel);

        return ResponseEntity.ok("ok");
    } else {
        // 로그인이 필요한 경우에 대한 처리
        String message = "로그인이 필요합니다.";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }
}

    /**
     * 여행 수정
     * @param travelId
     * @param requestTravelPutDto
     * @return
     */
    @PutMapping("/user/Travel/{travelId}/update")
    public ResponseEntity<?> UpdateTravel(@PathVariable Long travelId, @RequestBody RequestTravelPutDto requestTravelPutDto){
        Travel travel = travelRepository.findById(travelId).get();
        System.out.println(travelId);

        Double amount = requestTravelPutDto.getAmount();
        String title = requestTravelPutDto.getTitle();

        String startDate = requestTravelPutDto.getStartDate();
        String endDate = requestTravelPutDto.getEndDate();
        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);

        travel.setTitle(title);
        travel.setAmount(amount);
        travel.setStartDate(sDate);
        travel.setEndDate(eDate);
        List<NationCode> nationCodes = new ArrayList<>();
        for (String nation : requestTravelPutDto.getNations()) {
            //check it!
            NationCode nationCode = nationCodeRepository.findByNation(nation).orElseThrow(() -> new NullPointerException());
            nationCodes.add(nationCode);
        }
        travel.setNationCodes(nationCodes);
        travelRepository.save(travel);
//        requestTravelPutDto.getNations();


        return ResponseEntity.ok("ok");
    }




    /**
     * show Travel list
     * @param authentication jwt
     * @return List Travel
     */
    // UserId 어떻게 할것인가.
    @GetMapping("/user/TravelList")
    public List<ResponseTravelListDto> showTravel(Authentication authentication){
        log.info("this is user info {}", authentication.getName());
//        ResponseEntity.ok(service.authenticate(request));
        String email = authentication.getName();
        Optional<User> byEmail = userRepository.findByEmail(email);
        User a = byEmail.get();
        log.info("user::{}", a.getName());
        List<ResponseTravelListDto> travelList = new ArrayList<>();
        if(!travelRepository.findAllByUsers(a).isEmpty()){
            List<Travel> t =
            travelRepository.findAllByUsers(a);
            log.info("{}", t);
            for (Travel travel : t) {
                ResponseTravelListDto responseTravelListDto = new ResponseTravelListDto();
                responseTravelListDto.setTitle(travel.getTitle());
                responseTravelListDto.setStartDate(travel.getStartDate().toString());
                responseTravelListDto.setEndDate(travel.getEndDate().toString());
                for (NationCode nationCode : travel.getNationCodes()) {
                    responseTravelListDto.getNation().add(nationCode.getNation());
                }
                travelList.add(responseTravelListDto);
            }
        }
        return travelList;
    }

    /**
     *  delete travel
     * @param authentication jwt
     * @param tripId travel id
     * @return ok
     */

    @DeleteMapping("/user/Travel/{tripId}/delete")
    public ResponseEntity<?> delTravel(Authentication authentication,
//                            @RequestBody Travel travel,
                              @PathVariable Long tripId
    ){
        travelRepository.deleteById(tripId);
        log.info("여행 삭제 완료 : {} ",tripId);
        return ResponseEntity.ok(tripId);
    }

//    @GetMapping("/test")
//    public void test(){
//        System.out.println("this is test!");
//    }

}
