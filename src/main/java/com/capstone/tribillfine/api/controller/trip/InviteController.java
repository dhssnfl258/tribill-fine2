package com.capstone.tribillfine.api.controller.trip;

import com.capstone.tribillfine.domain.travel.Travel;
import com.capstone.tribillfine.domain.travel.TravelRepository;
import com.capstone.tribillfine.domain.user.User;
import com.capstone.tribillfine.domain.user.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/invite")
public class InviteController {
    private final TravelRepository travelRepository;
    private final UserRepository userRepository;

    public InviteController(TravelRepository travelRepository, UserRepository userRepository) {
        this.travelRepository = travelRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/{inviteCode}/user")
    public ResponseEntity<?> invite(@PathVariable String inviteCode, @RequestParam  String Name){
        // 초대 코드를 기반으로 여행을 찾는다.
        Travel travel = travelRepository.findByInviteCode(inviteCode).get();
        User user = userRepository.findByName(Name).get();
        travel.getUsers().add(user);
        travelRepository.save(travel);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/trip/{travelId}")
    public ResponseEntity<String> generateInviteCode(@PathVariable Long travelId) {
        // travelNumber를 기반으로 초대 코드 생성
        String inviteCode = generateCode(travelId);
        // 초대 코드를 저장하고 응답
        saveInviteCode(travelId, inviteCode);
        return ResponseEntity.ok(inviteCode);
    }

    private String generateCode(Long travelNumber) {
        // travelNumber를 기반으로 초대 코드 생성 로직 작성
        String code = RandomString.make(8); // 8자리 알파벳/숫자 조합 코드 생성
        return code;
    }

    private void saveInviteCode(Long travelNumber, String inviteCode) {
        // 초대 코드를 저장하는 로직 작성
        // 데이터베이스에 저장하거나 캐시에 저장하는 등의 방식으로 구현 가능
        // 여기서는 간단히 출력만 하도록 작성
        Travel travel = travelRepository.findById(travelNumber).get();
        travel.setInviteCode(inviteCode);
        travelRepository.save(travel);

        System.out.println("Travel Number: " + travelNumber + ", Invite Code: " + inviteCode);
    }



}
