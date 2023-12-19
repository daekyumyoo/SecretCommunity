package com.example.secretcommunity.service;

import com.example.secretcommunity.dto.board.BoardDTO;
import com.example.secretcommunity.dto.community.CommunityDTO;
import com.example.secretcommunity.dto.post.PostDTO;
import com.example.secretcommunity.model.Board;
import com.example.secretcommunity.model.Community;
import com.example.secretcommunity.model.Member;
import com.example.secretcommunity.model.Post;
import com.example.secretcommunity.persistence.*;
import com.example.secretcommunity.util.MultipartUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityService {
    
    private final MemberRepository memberRepository;
    
    private final CommunityRepository communityRepository;

    private final CommunityMemberRepository communityMemberRepository;

    private final PostRepository postRepository;

    private final BoardRepository boardRepository;

    private final FileEncryptionService fileEncryptionService;

    @Value("${file.path.community}")
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

    public List<CommunityDTO.HomePageCommunityResponseDTO> getAllCommunity() throws IOException {

        List<Community> communities = communityRepository.findAllByState(CommunityDTO.State.ACTIVE);
        List<CommunityDTO.HomePageCommunityResponseDTO> allCommunityResponseDTOS = new ArrayList<>();
        for (Community community : communities) {
            CommunityDTO.HomePageCommunityResponseDTO allCommunityResponseDTO = CommunityDTO.HomePageCommunityResponseDTO.builder()
                    .id(community.getId())
                    .name(community.getName())
                    .profileImgPath(fileEncryptionService.decryptFile(new File(imageFilePath + "\\" + community.getProfileImg()), password))
                    .build();
            allCommunityResponseDTOS.add(allCommunityResponseDTO);
        }
        return allCommunityResponseDTOS;

    }

    public List<CommunityDTO.HomePageCommunityResponseDTO> getAllMyCommunity(UserDetails user) throws IOException {

        Member member = memberRepository.findMemberByUsername(user.getUsername());

        List<Community> communities = communityRepository.findByStateAndCreater(CommunityDTO.State.ACTIVE, member);
        List<CommunityDTO.HomePageCommunityResponseDTO> myCommunityResponseDTOS = new ArrayList<>();
        for (Community community : communities) {
            CommunityDTO.HomePageCommunityResponseDTO myCommunityResponseDTO = CommunityDTO.HomePageCommunityResponseDTO.builder()
                    .id(community.getId())
                    .name(community.getName())
                    .profileImgPath(fileEncryptionService.decryptFile(new File(imageFilePath + "\\" + community.getProfileImg()), password))
                    .build();
            myCommunityResponseDTOS.add(myCommunityResponseDTO);
        }
        return myCommunityResponseDTOS;

    }

    public List<CommunityDTO.HomePageCommunityResponseDTO> getAllJoinCommunity(UserDetails user) throws IOException {

        Member member = memberRepository.findMemberByUsername(user.getUsername());

        List<Community> communities = communityMemberRepository.findJoinCommunity(member, CommunityDTO.State.ACTIVE);
        List<CommunityDTO.HomePageCommunityResponseDTO> myCommunityResponseDTOS = new ArrayList<>();
        for (Community community : communities) {
            CommunityDTO.HomePageCommunityResponseDTO myCommunityResponseDTO = CommunityDTO.HomePageCommunityResponseDTO.builder()
                    .id(community.getId())
                    .name(community.getName())
                    .profileImgPath(fileEncryptionService.decryptFile(new File(imageFilePath + "\\" + community.getProfileImg()), password))
                    .build();
            myCommunityResponseDTOS.add(myCommunityResponseDTO);
        }
        return myCommunityResponseDTOS;

    }


    /**
     * 커뮤니티 생성
     * @param user
     * @param createRequestDTO
     */
    public int createCommunity(UserDetails user, CommunityDTO.CreateRequestDTO createRequestDTO) {

        Member member = memberRepository.findMemberByUsername(user.getUsername());

        Community community = Community.builder()
                .creater(member)
                .createDate(LocalDateTime.now())
                .name(createRequestDTO.getName())
                .rule(createRequestDTO.getRule())
                .state(CommunityDTO.State.ACTIVE)
                .description(createRequestDTO.getDescription())
                .build();
        communityRepository.save(community);

        File newFile = saveCommunityImage(community.getProfileImg(), community.getId(), getImageDirectoryPath(), createRequestDTO.getProfileImg());

        community.setProfileImg(newFile.getName());

        communityRepository.save(community);

        return community.getId();

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

    //커뮤니티 소개
    public CommunityDTO.IntroResponseDTO getIntro(int communityId, UserDetails user) throws IOException {

        Member member = memberRepository.findMemberByUsername(user.getUsername());

        Community community = communityRepository.findById(communityId);

        boolean communityMember = communityMemberRepository.existsByCommunityAndMember(community, member) || communityRepository.existsByCreaterAndId(member, communityId);

        CommunityDTO.IntroResponseDTO introResponseDTO = CommunityDTO.IntroResponseDTO.builder()
                .id(community.getId())
                .name(community.getName())
                .createDate(community.getCreateDate().toLocalDate())
                .rule(community.getRule())
                .profileImgPath(fileEncryptionService.decryptFile(new File(imageFilePath + "\\" + community.getProfileImg()), password))
                .description(community.getDescription())
                .communityMember(communityMember)
                .build();
        return introResponseDTO;

    }


    public CommunityDTO.MainResponseDTO getMain(int communityId, UserDetails user) {

        Member member = memberRepository.findMemberByUsername(user.getUsername());

        Community community = communityRepository.findById(communityId);

        boolean communityMember = communityMemberRepository.existsByCommunityAndMember(community, member) || communityRepository.existsByCreaterAndId(member, communityId);

        if (!communityMember) {
            throw new RuntimeException("권한이 없습니다.");
        }

        List<PostDTO.MainResponseDTO> postDTOs = new ArrayList<>();
        List<Post> posts = postRepository.findAllByBoardCommunityAndState(community, PostDTO.State.ACTIVE);

        for (Post post : posts) {
            PostDTO.MainResponseDTO postDTO = PostDTO.MainResponseDTO.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .createDate(post.getCreateDate())
                    .views(post.getViews())
                    .build();
            postDTOs.add(postDTO);
        }

        List<BoardDTO.MainResponseDTO> boardDTOs = new ArrayList<>();
        List<Board> boards = boardRepository.findAllByCommunityAndState(community, BoardDTO.State.ACTIVE);

        for (Board board : boards) {
            BoardDTO.MainResponseDTO boardDTO = BoardDTO.MainResponseDTO.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .build();
            boardDTOs.add(boardDTO);
        }

        return new CommunityDTO.MainResponseDTO(boardDTOs, postDTOs);

    }
}
