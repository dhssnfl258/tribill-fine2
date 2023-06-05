package com.capstone.tribillfine.api.controller.currency;

import com.capstone.tribillfine.domain.currency.Currency;
import com.capstone.tribillfine.domain.currency.CurrencyRepository;
import com.capstone.tribillfine.domain.currency.NationCode;
import com.capstone.tribillfine.domain.currency.NationCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
public class CurrencyVisibleController {
    private final CurrencyRepository currencyRepository;
    private final NationCodeRepository nationCodeRepository;

    public CurrencyVisibleController(CurrencyRepository currencyRepository, NationCodeRepository nationCodeRepository) {
        this.currencyRepository = currencyRepository;
        this.nationCodeRepository = nationCodeRepository;
    }


    @GetMapping("/api/currency/v1")
    public ResponseEntity<?> getCurrency(@RequestParam String Nation){
        ResponseCurrencyDto responseCurrencyDto = new ResponseCurrencyDto();

//        Optional<NationCode> byNation = nationCodeRepository.findByCode(Nation);
//
//        NationCode nationCode = byNation.get();
//        String code = nationCode.getCode();
        Optional<Currency> topByNationOrderByDateDesc = currencyRepository.findTopByNationOrderByDateDesc(Nation);
        Currency currency = topByNationOrderByDateDesc.get();
        responseCurrencyDto.setNation(currency.getNation());
        responseCurrencyDto.setRate(currency.getRate());

        return ResponseEntity.ok(responseCurrencyDto);
    }
}
