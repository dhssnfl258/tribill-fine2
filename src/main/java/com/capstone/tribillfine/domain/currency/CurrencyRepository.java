package com.capstone.tribillfine.domain.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
//    Currency findByNationAndDate(String nation, LocalDate date);
@Query("SELECT c FROM Currency c WHERE c.nation = :nation AND c.date = :date")
Currency findByNationAndDate(@Param("nation") String nation, @Param("date") LocalDate date);

Optional<Currency> findTopByNationOrderByDateDesc(String nation);
//Optional<Currency> findTopByCodeOrderByDateDesc(String code);
List<Currency> findTop7ByNationOrderByDateDesc(String nation);

    @Query("SELECT c FROM Currency c WHERE c.date = :date")
    List<Currency> findCurrenyByDate(@Param("date") LocalDate date);

    @Query("SELECT c FROM Currency c")
    List<Currency> findCurrenyAll();

}
