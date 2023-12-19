package com.example.secretcommunity.dto;

import com.example.secretcommunity.model.Member;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class CommunityDTO {

    private int id;
    private Member creater;

    private String name;

    @Data
    @AllArgsConstructor
    @Builder
    public static class HomePageCommunityResponseDTO {

        private int id;

        private String name;

        private String profileImgPath;

    }

    @Data
    @NoArgsConstructor
    public static class CreateRequestDTO {

        private String name;

        private int rule;

        private MultipartFile profileImg;

        private String description;

    }

    @Data
    @Builder
    public static class IntroResponseDTO {

        private String name;

        private LocalDate createDate;

        private int rule;

        private String profileImgPath;

        private String description;

        private boolean communityMember;

    }

    // 가입 규칙
    public static class Rule {

        // 바로 가입
        public static final int NOW = 0;

        // 승인 대기
        public static final int WAIT = 1;

    }

    // 커뮤니티 상태
    public static class State {

        // 활성화
        public static final int ACTIVE = 0;

        // 비활성화
        public static final int DEACTIVE = 1;

        // 삭제
        public static final int DELETE = 2;

    }

}
