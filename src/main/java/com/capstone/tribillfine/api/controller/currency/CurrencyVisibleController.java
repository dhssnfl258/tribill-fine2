package com.capstone.tribillfine.api.controller.currency;

import com.capstone.tribillfine.domain.currency.Currency;
import com.capstone.tribillfine.domain.currency.CurrencyRepository;
import com.capstone.tribillfine.domain.currency.NationCode;
import com.capstone.tribillfine.domain.currency.NationCodeRepository;
import com.capstone.tribillfine.domain.user.User;
import com.capstone.tribillfine.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class CurrencyVisibleController {
    private final CurrencyRepository currencyRepository;
    private final NationCodeRepository nationCodeRepository;
    private final PushAlarmRepository pushAlarmRepository;
    private final UserRepository userRepository;

    private final PushNotificationRepository pushNotificationRepository;

    public CurrencyVisibleController(CurrencyRepository currencyRepository, NationCodeRepository nationCodeRepository,
                                     PushAlarmRepository pushAlarmRepository, UserRepository userRepository,
                                     PushNotificationRepository pushNotificationRepository) {
        this.currencyRepository = currencyRepository;
        this.nationCodeRepository = nationCodeRepository;
        this.pushAlarmRepository = pushAlarmRepository;
        this.userRepository = userRepository;
        this.pushNotificationRepository = pushNotificationRepository;
    }


    @GetMapping("/api/currency/v1")
    public ResponseEntity<?> getCurrency(@RequestParam String Nation){
        ResponseCurrencyDto responseCurrencyDto = new ResponseCurrencyDto();

        Optional<Currency> topByNationOrderByDateDesc = currencyRepository.findTopByNationOrderByDateDesc(Nation);
        Currency currency = topByNationOrderByDateDesc.get();
        responseCurrencyDto.setNation(currency.getNation());
        responseCurrencyDto.setRate(currency.getRate());

        List<Double> cList = new ArrayList<>();
        //Currency의 최근 7개의 환율을 찾는다
        List<Currency> currencies = currencyRepository.findTop7ByNationOrderByDateDesc(Nation);
        for (Currency currency1 : currencies) {
            log.info("currency1: {}", currency1.getRate());
            cList.add(currency1.getRate());
        }

        //오늘 기준으로 최근 7일의 월-일(local date)을 리스트에 담는다.
        //그리고 그 리스트를 responseCurrencyDto에 담는다.
        List<String> dates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        for (int i = 0; i < 7; i++) {
            dates.add(currency.getDate().minusDays(i).format(formatter));
            log.info("dates: {}", dates);
        }
        responseCurrencyDto.setLabels(dates);
        responseCurrencyDto.setData(cList);
        //responseCurrencyDto.setDates(dates);
        return ResponseEntity.ok(responseCurrencyDto);
    }

    @PostMapping("/api/currency/v1/push/currency")
    public ResponseEntity<?> pushCurrency(
                                          @RequestBody PushNotification pushNotification){
        //Authentication authentication

//        String email = authentication.getName();
//        log.info("email: {}", email);
//        User user = userRepository.findByEmail(email).get();
        PushNotification pushNotification1 = pushNotification;
            log.info("pushNotification1: {}", pushNotification1.getPushToken());
            log.info("pushNotification1: {}", pushNotification1.getNationCode());
            log.info("pushNotification1: {}", pushNotification1.getRate());

            pushNotificationRepository.save(pushNotification1);
//        PushAlarm pushAlarm = new PushAlarm();
//        pushAlarm.setNationCode(Nation);
//        pushAlarm.setRate(rate);
//        pushAlarm.setUserId(user.getId());
//        pushAlarmRepository.save(pushAlarm);
        return ResponseEntity.ok().build();
    }




}
