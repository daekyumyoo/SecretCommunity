package com.example.secretcommunity.dto.post;

import com.example.secretcommunity.model.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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

    @Data
    @NoArgsConstructor
    public static class WriteRequestDTO {

        private int boardId;
        private String title;
        private String content;
        private MultipartFile image;
    }

    @Data
    @Builder
    public static class ViewResponseDTO {
private int id;
        private String title;
        private String content;
        private LocalDateTime createDate;
        private int views;
        private String imgPath;
    }


        // 게시글 상태
    public static class State {

        // 등록됨
        public static final int ACTIVE = 0;

        // 삭제
        public static final int DELETE = 1;

    }


}
