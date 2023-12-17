package com.example.secretcommunity.dto.member;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class MemberDTO {

    /*
    * 회원가입 DTO
    */
    @Data
    @NoArgsConstructor
    public static class SignUpRequestDTO {

        private String username;

        private String password;

        private String email;

        private String name;

        private String tel;

        private LocalDate birthDay;

        private LocalDateTime joinDay;

        private int gender;

        private String address;

        private int role;

        private int state;

    }

    /*
     * 로그인 DTO
     */
    @Data
    @NoArgsConstructor
    public static class SignInRequestDTO {

        private String username;

        private String password;

    }
        // 유저 권한
    public static class Role {

        // 일반 유저
        public static final int USER = 0;

    }

    // 유저 상태
    public static class State {

        // 활성화
        public static final int ACTIVE = 0;

    }
}
