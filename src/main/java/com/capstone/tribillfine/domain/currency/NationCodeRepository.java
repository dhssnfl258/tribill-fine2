package com.capstone.tribillfine.domain.currency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface NationCodeRepository extends JpaRepository<NationCode, Integer> {

    Optional<NationCode> findByNation(String nation);
    Optional<NationCode> findByCode(String code);
}
