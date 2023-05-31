package com.capstone.tribillfine.domain.currency;


import com.capstone.tribillfine.domain.travel.Travel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

//국가, 국가코드 엔티티

@Entity
//@Table(name = "currency")
@Getter
@Setter
@ToString
public class NationCode {
    @Id
    private String nation;
    private String code;

    @ManyToMany(mappedBy = "nationCodes")
    private List<Travel> travels;
}
