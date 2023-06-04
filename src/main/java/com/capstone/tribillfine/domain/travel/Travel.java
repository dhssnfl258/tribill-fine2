package com.capstone.tribillfine.domain.travel;


import com.capstone.tribillfine.domain.currency.NationCode;
import com.capstone.tribillfine.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//여행 엔티티
@Getter
@Setter
@Entity
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private Long id;
    private String title;
    private Double amount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String inviteCode;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @ManyToMany
    @JoinTable(
            name = "travel_nation",
            joinColumns = @JoinColumn(name = "travel_id"),
            inverseJoinColumns = @JoinColumn(name = "nation_code")
    )
    private List<NationCode> nationCodes;

    @ManyToMany
    @JoinTable(
            name = "travel_user",
            joinColumns = @JoinColumn(name = "travel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )

    private List<User> users = new ArrayList<>();

    public Travel() {
        // 빈 리스트로 초기화
        this.users = new ArrayList<>();
    }
}
