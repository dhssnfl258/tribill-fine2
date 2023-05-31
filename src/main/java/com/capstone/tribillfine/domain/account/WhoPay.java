package com.capstone.tribillfine.domain.account;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class WhoPay {
    @Id
    Long id;
    String name;
}
