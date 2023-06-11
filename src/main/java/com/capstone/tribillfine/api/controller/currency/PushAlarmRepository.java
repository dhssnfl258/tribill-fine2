package com.capstone.tribillfine.api.controller.currency;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PushAlarmRepository extends JpaRepository<PushAlarm, Long>{
    PushAlarm findByUserId(Long userId);
    //모든 푸시알람을 가져오는 쿼리
    List<PushAlarm> findAll();
}
