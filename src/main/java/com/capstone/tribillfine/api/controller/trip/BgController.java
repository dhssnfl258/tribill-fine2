package com.capstone.tribillfine.api.controller.trip;


import com.capstone.tribillfine.api.controller.trip.dto.bg.RequestBudgetRegisterDto;
import com.capstone.tribillfine.api.controller.trip.dto.bg.ResponseBgRegister;
import com.capstone.tribillfine.api.controller.trip.dto.bg.ResponseBudgetDetailsDto;
import com.capstone.tribillfine.domain.account.Budget;
import com.capstone.tribillfine.domain.account.BudgetRepository;
import com.capstone.tribillfine.domain.account.CATEGORY;
import com.capstone.tribillfine.domain.account.Type;
import com.capstone.tribillfine.domain.currency.Currency;
import com.capstone.tribillfine.domain.currency.CurrencyRepository;
import com.capstone.tribillfine.domain.currency.NationCode;
import com.capstone.tribillfine.domain.currency.NationCodeRepository;
import com.capstone.tribillfine.domain.travel.Travel;
import com.capstone.tribillfine.domain.travel.TravelRepository;
import com.capstone.tribillfine.domain.user.User;
import com.capstone.tribillfine.domain.user.repository.UserRepository;
import com.capstone.tribillfine.s3.S3Upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
public class BgController {

    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final CurrencyRepository currencyRepository;
    private final NationCodeRepository nationCodeRepository;
    private final BudgetRepository budgetRepository;
    private final S3Upload fileUploadService;


    @Autowired
    public BgController(UserRepository userRepository, TravelRepository travelRepository,
                          BudgetRepository budgetRepository,
                          NationCodeRepository nationCodeRepository,
                          CurrencyRepository currencyRepository,
                        S3Upload fileUploadService) {
        this.userRepository = userRepository;
        this.travelRepository = travelRepository;
        this.budgetRepository = budgetRepository;
        this.nationCodeRepository = nationCodeRepository;
        this.currencyRepository = currencyRepository;
        this.fileUploadService = fileUploadService;
    }

    /**
     * 여행 디테일 페이지
     * @param tripId
     * @param userName
     * @return
     */
    @GetMapping("api/budget/trip/{tripId}/details")
    public ResponseEntity<?> travelDetails(@PathVariable Long tripId, @RequestParam("userName") String userName){
        log.info("{}, {}",tripId, userName);

        Travel travel = travelRepository.findById(tripId).get();
        log.info("travel::{}",travel.getTitle());
        User user = userRepository.findByName(userName).get();
        log.info("user::{}",user.getName());

        List<Budget> allByTravelAndUsers = budgetRepository.findAllByTravelAndUsers(travel, user);

        for (Budget allByTravelAndUser : allByTravelAndUsers) {
            log.info("{}",allByTravelAndUser.getTitle());
        }
        List<User> users = travel.getUsers();
        for (User user1 : users) {
            log.info("users::{}",user1);
        }

        return ResponseEntity.ok("ok");
    }


    /**
     * 가계부 등록시에 보내줄것들
     * @param tripId
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/api/budget/trip/{tripId}/show")
    public ResponseEntity<?> showBgStatus(@PathVariable Long tripId){
        ResponseBgRegister bgRegister = new ResponseBgRegister();
        log.info("{}", tripId);
        Optional<Travel> byId = travelRepository.findById(tripId);
        Travel trip = byId.get();
        List<String> member = new ArrayList<>();

        for (User user : trip.getUsers()) {
            String n = user.getName();
            member.add(n);
        }

        Map<String, Double> Er = new HashMap<>();
        for (NationCode nationCode : trip.getNationCodes()) {
            String nCode = nationCode.getCode();
            LocalDate d = LocalDate.now();
//            Currency byNationAndDate = currencyRepository.findByNationAndDate(nCode, d);
            Optional<Currency> topByNationOrderByDateDesc = currencyRepository.findTopByNationOrderByDateDesc(nCode);
            Currency byNationAndDate = topByNationOrderByDateDesc.get();
            log.info("currency {} ", byNationAndDate.getRate());
            Er.put(nationCode.getCode(), byNationAndDate.getRate());
        }
        if(!Er.containsKey("USD")){
            Optional<Currency> usd = currencyRepository.findTopByNationOrderByDateDesc("USD");
            Currency currency = usd.get();
            Er.put(currency.getNation(), currency.getRate());

        }
        if(!Er.containsKey("EUR")){
            Optional<Currency> eur = currencyRepository.findTopByNationOrderByDateDesc("EUR");
            Currency currency = eur.get();
            Er.put(currency.getNation(), currency.getRate());
        }

        bgRegister.setMember(member);
        bgRegister.setExchangeRate(Er);

        return ResponseEntity.ok(bgRegister);
    }




    @GetMapping("/test")
    public String hello(){
        InetAddress local = null;
        try {
            local = InetAddress.getLocalHost();
        }
        catch ( UnknownHostException e ) {
            e.printStackTrace();
        }

        if( local == null ) {
            return "";
        }
        else {
            String ip = local.getHostAddress();
            System.out.println(ip);
            return ip;
        }

    }

    @PostMapping("/api/budget/trip/{tripId}/register")
    public ResponseEntity<?> BudgetRegister(@PathVariable Long tripId, MultipartFile multiPartFile,  RequestBudgetRegisterDto requestBudgetRegisterDto){
            try{
                Budget budget = new Budget();
                String title = requestBudgetRegisterDto.getTitle();
                String nation = requestBudgetRegisterDto.getNation();
                Double nationMoney = requestBudgetRegisterDto.getNationMoney();
                Double koreaMoney = requestBudgetRegisterDto.getKoreaMoney();
                Type type = requestBudgetRegisterDto.getType();
                CATEGORY category = requestBudgetRegisterDto.getCategory();

                Travel travel = travelRepository.findById(tripId).get();
                budget.setTravel(travel);

                //검증할것
                List<String> whoPay = requestBudgetRegisterDto.getWhoPay();
                budget.setWhoPay(whoPay);
                //수정할것
                List<String> spendWith = requestBudgetRegisterDto.getSpendWith();
                for (String s : spendWith) {
                    User user = userRepository.findByName(s).get();
                    budget.getUsers().add(user);
                }
                budget.setSpendWidth(spendWith);


                LocalDate registerDate = LocalDate.parse( requestBudgetRegisterDto.getRegisterDate());
//                String fileUrl = fileUploadService.uploadFile(requestBudgetRegisterDto.getImgFile());
                log.info("file name :: {}", multiPartFile.getOriginalFilename());
                String fileUrl = fileUploadService.upload(multiPartFile);
                budget.setFileUrl(fileUrl);
                budget.setTitle(title);
                budget.setNation(nation);
                budget.setNationMoney(nationMoney);
                budget.setKoreaMoney(koreaMoney);
                budget.setRegisterTime(registerDate);
                budget.setCategory(category);
                budget.setType(type);


                budgetRepository.save(budget);
                return ResponseEntity.ok("ok");
            }catch(IOException e){
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }


//        budget.setUsers();
//        budget.setSpendWidth();
    }
    @DeleteMapping("api/budget/delete")
    public ResponseEntity<?> deleteBudget(@RequestParam String Name){
        budgetRepository.delete(budgetRepository.findByTitle(Name));
        return ResponseEntity.ok("ok");
    }

    /**
     * 가계부 상세
     *
     * @param budgetId
     * @return
     */
    @GetMapping("/api/budget/{budgetId}/details")

    public ResponseEntity<?> budgetDetails(@PathVariable Long budgetId){
        Optional<Budget> byId = budgetRepository.findById(budgetId);
        Budget budget = byId.get();
        CATEGORY category = budget.getCategory();
        String nation = budget.getNation();
        Double nationMoney = budget.getNationMoney();
        Double koreaMoney = budget.getKoreaMoney();
        String fileUrl = budget.getFileUrl();
        String title = budget.getTitle();
        LocalDate registerTime = budget.getRegisterTime();
        List<String> spendWidth = budget.getSpendWidth();
        List<String> whoPay = budget.getWhoPay();
        String description = budget.getDescription();
        Type type = budget.getType();
        Long id = budget.getId();

        ResponseBudgetDetailsDto budgetDetailsDto = new ResponseBudgetDetailsDto();
        budgetDetailsDto.setTitle(title);
        budgetDetailsDto.setNation(nation);
        budgetDetailsDto.setNationMoney(nationMoney);
        budgetDetailsDto.setKoreaMoney(koreaMoney);
        budgetDetailsDto.setType(type);
        budgetDetailsDto.setRegisterDate(registerTime);
        budgetDetailsDto.setFileUrl(fileUrl);
        budgetDetailsDto.setSpendWith(spendWidth);
        budgetDetailsDto.setWhoPay(whoPay);
        budgetDetailsDto.setCategory(category);
        budgetDetailsDto.setDescription(description);


        return ResponseEntity.ok(budgetDetailsDto);
    }



    @PutMapping("/api/budget/{budgetId}/update")
    public ResponseEntity<?> budgetUpdate(@PathVariable Long budgetId,MultipartFile multiPartFile, RequestBudgetRegisterDto requestBudgetRegisterDto){

        try{
            Budget budget = budgetRepository.findById(budgetId).get();
            String title = requestBudgetRegisterDto.getTitle();
            String nation = requestBudgetRegisterDto.getNation();
            Double nationMoney = requestBudgetRegisterDto.getNationMoney();
            Double koreaMoney = requestBudgetRegisterDto.getKoreaMoney();
            Type type = requestBudgetRegisterDto.getType();
            CATEGORY category = requestBudgetRegisterDto.getCategory();

            //검증할것
            List<String> whoPay = requestBudgetRegisterDto.getWhoPay();
            budget.setWhoPay(whoPay);

            //수정할것
            List<String> spendWith = requestBudgetRegisterDto.getSpendWith();
            for (String s : spendWith) {
                User user = userRepository.findByName(s).get();
                budget.getUsers().add(user);
            }


            LocalDate registerDate = LocalDate.parse(requestBudgetRegisterDto.getRegisterDate());
//                String fileUrl = fileUploadService.uploadFile(requestBudgetRegisterDto.getImgFile());
            String fileUrl = fileUploadService.upload(multiPartFile);
            budget.setFileUrl(fileUrl);
            budget.setTitle(title);
            budget.setNation(nation);
            budget.setNationMoney(nationMoney);
            budget.setKoreaMoney(koreaMoney);
            budget.setRegisterTime(registerDate);
            budget.setCategory(category);
            budget.setType(type);

            budgetRepository.save(budget);
            return ResponseEntity.ok(requestBudgetRegisterDto);
        }catch(IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }
}
