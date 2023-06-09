package com.capstone.tribillfine.domain.user.service;

import com.capstone.tribillfine.domain.user.Role;
import com.capstone.tribillfine.domain.user.User;
import com.capstone.tribillfine.domain.user.dto.UserSignUpDto;
import com.capstone.tribillfine.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String signUp(UserSignUpDto userSignUpDto) throws Exception {
        int i = 0;
        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
//            throw new Exception("이미 존재하는 이메일입니다.");
            i = 1;
        }

        if (userRepository.findByName(userSignUpDto.getNickname()).isPresent()) {
//            throw new Exception("이미 존재하는 닉네임입니다.");
//            return "fail";
            i = 1;
        }
        if(i == 0){
            System.out.println("sign-up!!");
            User user = User.builder()
                    .email(userSignUpDto.getEmail())
                    .password(userSignUpDto.getPassword())
                    .name(userSignUpDto.getNickname())
//                .age(userSignUpDto.getAge())
//                .city(userSignUpDto.getCity())
                    .role(Role.USER)
                    .build();

            user.passwordEncode(passwordEncoder);
            userRepository.save(user);
        }

        return "success";
    }
}