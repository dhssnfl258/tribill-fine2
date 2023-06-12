package com.capstone.tribillfine.api.controller.currency;

import com.capstone.tribillfine.api.controller.currency.PushNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushNotificationRepository extends JpaRepository<PushNotification, Long> {
    // 추가적인 메소드 또는 쿼리를 정의할 수 있습니다.
    // JpaRepository에서 제공하는 기본 CRUD 기능은 이미 포함되어 있습니다.
}
