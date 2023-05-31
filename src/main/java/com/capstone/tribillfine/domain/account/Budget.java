package com.capstone.tribillfine.domain.account;


import com.capstone.tribillfine.domain.travel.Travel;
import com.capstone.tribillfine.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
    private String title;
    //한화
    private Double KoreaMoney;
    //사용국가
    private String nation;
    //설명
    private String description;
    //사용국가 돈
    private Double nationMoney;
    private LocalDate registerTime;
    private String fileUrl;
    // CREDIT, CASH
    @Enumerated(EnumType.STRING)
    private Type type;
    @Enumerated(EnumType.STRING)
    private CATEGORY category;

    @ManyToMany
    @JoinTable(
            name = "budget_user",
            joinColumns = @JoinColumn(name = "budget_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    public Budget() {
        // 빈 리스트로 초기화
        this.users = new ArrayList<>();
    }
    @ElementCollection
    private List<String> spendWidth = new ArrayList<>();
    @ElementCollection
    private List<String> WhoPay = new ArrayList<>();

}
