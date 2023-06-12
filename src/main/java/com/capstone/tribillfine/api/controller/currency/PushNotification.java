package com.capstone.tribillfine.api.controller.currency;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class PushNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pushToken;
    private String nationCode;
    private double rate;

    public PushNotification() {
    }

    public PushNotification(String pushToken, String nationCode, double rate) {
        this.pushToken = pushToken;
        this.nationCode = nationCode;
        this.rate = rate;
    }

}
