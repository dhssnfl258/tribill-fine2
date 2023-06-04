package com.capstone.tribillfine.domain.travel;

import com.capstone.tribillfine.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TravelRepository extends JpaRepository<Travel, Long> {
    List<Travel> findAllByUsers(User user);
    Optional<Travel> findByTitle(String title);
    Optional<Travel> findByInviteCode(String inviteCode);
}
