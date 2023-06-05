package com.capstone.tribillfine.domain.account;

import com.capstone.tribillfine.domain.travel.Travel;
import com.capstone.tribillfine.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findAllByTravel(Travel travel);
    List<Budget> findAllByTravelAndUsers(Travel travel, User user);
    List<Budget> findByTravelAndSpendWidth(Travel travel, String spendWidth);

    Budget findByTitle(String title);
}
