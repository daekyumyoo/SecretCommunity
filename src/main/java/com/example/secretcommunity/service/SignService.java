package com.example.secretcommunity.service;

import com.example.secretcommunity.config.SecurityPasswordEncoder;
import com.example.secretcommunity.dto.member.MemberDTO;
import com.example.secretcommunity.model.Member;
import com.example.secretcommunity.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * @param signUpDTO 회원 가입 입력 정보
     */
    public void signUp(MemberDTO.SignUpRequestDTO signUpDTO) {

        Member member = Member.builder()
                .username(signUpDTO.getUsername())
                .password(passwordEncoder.encode(signUpDTO.getPassword()))
                .email(signUpDTO.getEmail())
                .name(signUpDTO.getName())
                .tel(signUpDTO.getTel())
                .birthDate(signUpDTO.getBirthDate())
                .joinDate(LocalDateTime.now())
                .gender(signUpDTO.getGender())
                .address(signUpDTO.getAddress())
                .role(MemberDTO.Role.USER)
                .state(MemberDTO.State.ACTIVE)
                .build();
        memberRepository.save(member);

    }
}
