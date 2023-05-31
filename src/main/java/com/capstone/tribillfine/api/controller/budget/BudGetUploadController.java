//package com.capstone.tribillfine.api.controller.budget;
//
//
//
//import com.capstone.tribillfine.api.dto.BudgetRegisterDTO;
//import com.capstone.tribillfine.api.dto.request.BudgetDTO;
//import com.capstone.tribillfine.api.service.budget.FileUploadService;
//import com.capstone.tribillfine.domain.account.Budget;
//import com.capstone.tribillfine.domain.account.BudgetRepository;
//import com.capstone.tribillfine.domain.currency.Currency;
//import com.capstone.tribillfine.domain.currency.CurrencyRepository;
//import com.capstone.tribillfine.domain.currency.NationCode;
//import com.capstone.tribillfine.domain.travel.Travel;
//import com.capstone.tribillfine.domain.travel.TravelRepository;
//import com.capstone.tribillfine.domain.user.repository.UserRepository;
//import com.capstone.tribillfine.s3.S3Upload;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@RestController
//public class BudGetUploadController {
//    private final BudgetRepository budgetRepository;
//    private final UserRepository userRepository;
//    private final S3Upload fileUploadService;
//    private final TravelRepository travelRepository;
//
//    private final CurrencyRepository currencyRepository;
//
//
//    public BudGetUploadController(UserRepository userRepository,
//                                  TravelRepository travelRepository,
//                                  BudgetRepository budgetRepository,
//                                  S3Upload fileUploadService,
//                                  CurrencyRepository currencyRepository)
//    {
//        this.userRepository = userRepository;
//        this.travelRepository = travelRepository;
//        this.budgetRepository = budgetRepository;
//        this.fileUploadService = fileUploadService;
//        this.currencyRepository = currencyRepository;
//    }
//
//    /**
//     *  budget register
//     * @param tripid
//     * @param budgetDTO
//     * @return
//     */
//    @PostMapping("/trip/{tripid}/budget/register")
//    public ResponseEntity<Budget> BudgetRegister(@PathVariable("tripid") Long tripid, @RequestBody BudgetDTO budgetDTO) {
//        try {
//            log.info("tripid ::{} ", tripid);
//            log.info("register ::{} ", budgetDTO.getImgFile());
//
//            String fileUrl = fileUploadService.uploadFile(budgetDTO.getImgFile());
//
//            Budget budget = new Budget();
//
//            budget.setTitle(budgetDTO.getName());
//            budget.setTravel(travelRepository.findById(tripid).get());
//            budget.setFileUrl(fileUrl);
//            budgetRepository.save(budget);
//            return ResponseEntity.ok(budget);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//
//
//    @GetMapping("/trip/{tripid}/budget/register")
//    public ResponseEntity<?> budgetStatus(@PathVariable Long tripid){
//        BudgetRegisterDTO brg = new BudgetRegisterDTO();
//        LocalDate date = LocalDate.now();
//        LocalDate testDate = LocalDate.parse("2023-05-16");
//
//        Travel t = travelRepository.findById(tripid).get();
//
//        List<NationCode> nationCodes = t.getNationCodes();
//        Map<String, Double> nationAndRate = new HashMap<>();
//
//        for (NationCode nationCode : nationCodes) {
//            String nation = nationCode.getNation();
//            String code = nationCode.getCode();
//            System.out.println(nation +" " + code);
//            Currency byNationAndDate = currencyRepository.findByNationAndDate(code, testDate);
//            log.info("byNationAndDate : {}, {}",byNationAndDate.getNation(),byNationAndDate.getDate());
//            nationAndRate.put(nation, byNationAndDate.getRate());
//            log.info("nation::{}, code::{}, rate::{}", nation,code, byNationAndDate.getRate());
//        }
////        String username = t.getUsers().getName();
//        log.info("{}",t.getTitle());
//        log.info("{}", date);
//
////        brg.setName(username);
////        brg.setRate(nationAndRate);
//        return ResponseEntity.ok("ok");
//
//    }
//
//    @GetMapping("/travel/budget")
//    public ResponseEntity<?> Test(){
//        System.out.println("this is test!");
//        return ResponseEntity.ok("ok");
//    }
//}
