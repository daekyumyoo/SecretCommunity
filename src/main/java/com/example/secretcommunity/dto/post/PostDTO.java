package com.example.secretcommunity.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class PostDTO {

    @Data
    @Builder
    public static class MainResponseDTO {

        private int id;

        private String title;

        private LocalDateTime createDate;

        private int views;

    }

        // 게시글 상태
    public static class State {

        // 등록됨
        public static final int ACTIVE = 0;

        // 삭제
        public static final int DELETE = 1;

    }
}
