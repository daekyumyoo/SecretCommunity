package com.example.secretcommunity.service;

import com.example.secretcommunity.dto.board.BoardDTO;
import com.example.secretcommunity.dto.community.CommunityDTO;
import com.example.secretcommunity.dto.post.PostDTO;
import com.example.secretcommunity.model.Board;
import com.example.secretcommunity.model.Community;
import com.example.secretcommunity.model.Member;
import com.example.secretcommunity.model.Post;
import com.example.secretcommunity.persistence.BoardRepository;
import com.example.secretcommunity.persistence.CommunityRepository;
import com.example.secretcommunity.persistence.MemberRepository;
import com.example.secretcommunity.persistence.PostRepository;
import com.example.secretcommunity.util.MultipartUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final CommunityRepository communityRepository;

    private final MemberRepository memberRepository;

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    private final FileEncryptionService fileEncryptionService;


    @Value("${file.path.post}")
    private String imageFilePath;

    @Value("${src.community}")
    private String srcCommunity;

    @Value("${encryption.password}")
    private String password; // 암호화에 사용할 비밀번호
    public File getImageDirectoryPath() {
        File file = new File(imageFilePath);
        file.mkdirs();

        return file;
    }

    public List<BoardDTO.MainResponseDTO> getBoards(int communityId, UserDetails user) {

        Member member = memberRepository.findMemberByUsername(user.getUsername());

        Community community = communityRepository.findById(communityId);

        List<Board> boards = boardRepository.findAllByCommunityAndState(community, BoardDTO.State.ACTIVE);
        List<BoardDTO.MainResponseDTO> boardDTOs = new ArrayList<>();
        for (Board board : boards) {
            BoardDTO.MainResponseDTO boardDTO = BoardDTO.MainResponseDTO.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .build();
            boardDTOs.add(boardDTO);
        }

        return boardDTOs;
    }

    public int postWrite(UserDetails user, PostDTO.WriteRequestDTO writeRequestDTO) {

        Member member = memberRepository.findMemberByUsername(user.getUsername());
        Board board = boardRepository.findById(writeRequestDTO.getBoardId());
        Post post = Post.builder()
                .writer(member)
                .board(board)
                .title(writeRequestDTO.getTitle())
                .content(writeRequestDTO.getContent())
                .createDate(LocalDateTime.now())
                .state(PostDTO.State.ACTIVE)
                .views(1)
                .build();
        postRepository.save(post);

        File newFile = saveCommunityImage(post.getImgPath(), post.getId(), getImageDirectoryPath(), writeRequestDTO.getImage());

        post.setImgPath(newFile.getName());
        postRepository.save(post);
        return post.getId();


    }

    private File saveCommunityImage(String fileName, int id, File imageDirectoryPath, MultipartFile multipartFile) {
        try{
            // 기존 이미지가 있다면 삭제
            if (fileName != null) {
                deleteLectureImage(fileName, imageDirectoryPath);
            }

            // 이미지 저장 (파일명 : "강의 ID.확장자")
            File newFile = MultipartUtils.saveImage(multipartFile, imageDirectoryPath, String.valueOf(id), password);

            return newFile;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 이미지 삭제
    private void deleteLectureImage(String fileName, File directoryPath) {

        // 이미지 삭제
        String imagePath = fileName;
        if(imagePath != null) {
            File oldImageFile = new File(directoryPath, imagePath);
            oldImageFile.delete();
        }

    }

    public PostDTO.ViewResponseDTO view(int postId, UserDetails user) throws IOException {

        Post post = postRepository.findById(postId);


        PostDTO.ViewResponseDTO responseDTO = PostDTO.ViewResponseDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createDate(post.getCreateDate())
                .views(post.getViews())
                .imgPath(fileEncryptionService.decryptFile(new File(imageFilePath + "\\" + post.getImgPath()), password))
                .build();
        return responseDTO;

    }
}
