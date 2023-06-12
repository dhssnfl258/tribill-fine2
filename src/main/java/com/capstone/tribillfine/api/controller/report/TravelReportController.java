package com.capstone.tribillfine.api.controller.report;


import com.amazonaws.Response;
import com.capstone.tribillfine.api.controller.report.dto.CategoryBudgetDto;
import com.capstone.tribillfine.api.controller.report.dto.CategoryDto;
import com.capstone.tribillfine.api.controller.report.dto.NationsDto;
import com.capstone.tribillfine.api.controller.report.dto.ShowTravelDto;
import com.capstone.tribillfine.domain.account.Budget;
import com.capstone.tribillfine.domain.account.BudgetRepository;
import com.capstone.tribillfine.domain.currency.NationCode;
import com.capstone.tribillfine.domain.travel.Travel;
import com.capstone.tribillfine.domain.travel.TravelRepository;
import com.capstone.tribillfine.domain.user.User;
import com.capstone.tribillfine.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
public class TravelReportController {
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final BudgetRepository budgetRepository;

    public TravelReportController(UserRepository userRepository , TravelRepository travelRepository, BudgetRepository budgetRepository) {
        this.userRepository = userRepository;
        this.travelRepository = travelRepository;
        this.budgetRepository = budgetRepository;
    }

    @GetMapping("/api/report")
    public ResponseEntity<NationsDto> report(Authentication authentication) {
        String email = authentication.getName();
        log.info("email: {}", email);
        ArrayList<String> nations = new ArrayList<>();
        User user = userRepository.findByEmail(email).get();
        List<Travel> allByUsers = travelRepository.findAllByUsers(user);
        for (Travel allByUser : allByUsers) {
            List<NationCode> nationCodes = allByUser.getNationCodes();
            for (NationCode nationCode : nationCodes) {
                //duplicate check
                if(nations.contains(nationCode.getNation())){
                    continue;
                }
                nations.add(nationCode.getNation());
            }
        }
        NationsDto nationsDto = new NationsDto();
        nationsDto.setNations(nations);

        return  ResponseEntity.ok(nationsDto);
    }

    @GetMapping("/api/report/travel/list")
    public ResponseEntity<?> ShowTravelFindByNationAndUser(Authentication authentication, @RequestParam String nation) {
        String email = authentication.getName();
        log.info("email: {}", email);
        User user = userRepository.findByEmail(email).get();
        List<Travel> allByUsers = travelRepository.findAllByUsers(user);
        ArrayList<ShowTravelDto> travelList = new ArrayList<>();
        for (Travel allByUser : allByUsers) {
            List<NationCode> nationCodes = allByUser.getNationCodes();
            for (NationCode nationCode : nationCodes) {
                if(nationCode.getNation().equals(nation)){
                    ShowTravelDto showTravelDto = new ShowTravelDto();
                    showTravelDto.setId(allByUser.getId());
                    showTravelDto.setTitle(allByUser.getTitle());
                    showTravelDto.setStartDate(allByUser.getStartDate().toString());
                    showTravelDto.setEndDate(allByUser.getEndDate().toString());
                    travelList.add(showTravelDto);}
            }
        }
        return ResponseEntity.ok(travelList);
    }
    @GetMapping("/api/report/travel/{travelId}/detail")
    public ResponseEntity<?> DetailsShower(@PathVariable Long travelId){
        Travel travel = travelRepository.findById(travelId).orElseThrow(NoSuchElementException::new);
        //가계부 가져온다 -> 가계부에서 카테고리별로 더한다 -> 카테고리별로 더한것을 뿌려준다
        List<Budget> allByTravel = budgetRepository.findAllByTravel(travel);
        CategoryDto categoryDto = new CategoryDto();
        //각 가계부의 카테고리별로 더해서 categorydto의 도메인에 담는다.
for (Budget budget : allByTravel) {
            switch (budget.getCategory().getValue()){
                case "Food":
                    categoryDto.setFood(categoryDto.getFood()+budget.getKoreaMoney());
                    break;
                case "Transportation":
                    categoryDto.setTransportation(categoryDto.getTransportation()+budget.getKoreaMoney());
                    break;
                case "Accommodation":
                    categoryDto.setAccommodation(categoryDto.getAccommodation()+budget.getKoreaMoney());
                    break;
                case "Shopping":
                    categoryDto.setShopping(categoryDto.getShopping()+budget.getKoreaMoney());
                    break;
                case "Sightseeing":
                    categoryDto.setSightseeing(categoryDto.getSightseeing()+budget.getKoreaMoney());
                    break;
                case "Others":
                    categoryDto.setOthers(categoryDto.getOthers()+budget.getKoreaMoney());
                    break;
            }
            categoryDto.setTotal(categoryDto.getTotal()+budget.getKoreaMoney());
            categoryDto.setRest(travel.getAmount() - categoryDto.getTotal());
        }
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping("/api/report/travel/{travelId}/detail/category")
    public ResponseEntity<?> DetailsShower2(@PathVariable Long travelId, @RequestParam String category){
        Travel travel = travelRepository.findById(travelId).orElseThrow(NoSuchElementException::new);
        List<Budget> allByTravel = budgetRepository.findAllByTravel(travel);
        ArrayList<Budget> budgets = new ArrayList<>();
        ArrayList<CategoryBudgetDto> categoryBudgetDtos = new ArrayList<>();
        for (Budget budget : allByTravel) {
            if(budget.getCategory().getValue().equals(category)){
                CategoryBudgetDto categoryBudgetDto = new CategoryBudgetDto();
                categoryBudgetDto.setTitle(budget.getTitle());
                categoryBudgetDto.setKoreaMoney(budget.getKoreaMoney());
                categoryBudgetDto.setNation(budget.getNation());
                categoryBudgetDto.setAmount(budget.getNationMoney());
                categoryBudgetDto.setRegisterDate(budget.getRegisterTime().toString());
                categoryBudgetDtos.add(categoryBudgetDto);
                budgets.add(budget);
            }
        }
        return ResponseEntity.ok(categoryBudgetDtos);
    }

}