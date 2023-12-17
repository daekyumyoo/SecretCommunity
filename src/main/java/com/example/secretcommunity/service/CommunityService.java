package com.example.secretcommunity.service;

import com.example.secretcommunity.dto.CommunityDTO;
import com.example.secretcommunity.model.Community;
import com.example.secretcommunity.model.Member;
import com.example.secretcommunity.persistence.CommunityMemberRepository;
import com.example.secretcommunity.persistence.CommunityRepository;
import com.example.secretcommunity.persistence.MemberRepository;
import com.example.secretcommunity.util.MultipartUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityService {
    
    private final MemberRepository memberRepository;
    
    private final CommunityRepository communityRepository;

    private final CommunityMemberRepository communityMemberRepository;

    @Value("${file.path.community}")
    private String imageFilePath;

    @Value("${src.community}")
    private String srcCommunity;

    @Value("${encryption.password}")
    private String password; // 암호화에 사용할 비밀번호

    @Value("${encryption.salt}")
    private String salt; // 암호화에 사용할 salt

    public File getImageDirectoryPath() {
        File file = new File(imageFilePath);
        file.mkdirs();

        return file;
    }

    public List<CommunityDTO.HomePageCommunityResponseDTO> getAllCommunity() {

        List<Community> communities = communityRepository.findAllByState(CommunityDTO.State.ACTIVE);
        List<CommunityDTO.HomePageCommunityResponseDTO> allCommunityResponseDTOS = new ArrayList<>();
        for (Community community : communities) {
            CommunityDTO.HomePageCommunityResponseDTO allCommunityResponseDTO = CommunityDTO.HomePageCommunityResponseDTO.builder()
                    .id(community.getId())
                    .name(community.getName())
                    .profileImgPath(srcCommunity + community.getProfileImg())
                    .build();
            allCommunityResponseDTOS.add(allCommunityResponseDTO);
        }
        return allCommunityResponseDTOS;

    }

    public List<CommunityDTO.HomePageCommunityResponseDTO> getAllMyCommunity(UserDetails user) {

        Member member = memberRepository.findMemberByUsername(user.getUsername());

        List<Community> communities = communityRepository.findByStateAndCreater(CommunityDTO.State.ACTIVE, member);
        List<CommunityDTO.HomePageCommunityResponseDTO> myCommunityResponseDTOS = new ArrayList<>();
        for (Community community : communities) {
            CommunityDTO.HomePageCommunityResponseDTO myCommunityResponseDTO = CommunityDTO.HomePageCommunityResponseDTO.builder()
                    .id(community.getId())
                    .name(community.getName())
                    .profileImgPath(srcCommunity + community.getProfileImg())
                    .build();
            myCommunityResponseDTOS.add(myCommunityResponseDTO);
        }
        return myCommunityResponseDTOS;

    }

    public List<CommunityDTO.HomePageCommunityResponseDTO> getAllJoinCommunity(UserDetails user) {

        Member member = memberRepository.findMemberByUsername(user.getUsername());

        List<Community> communities = communityMemberRepository.findJoinCommunity(member, CommunityDTO.State.ACTIVE);
        List<CommunityDTO.HomePageCommunityResponseDTO> myCommunityResponseDTOS = new ArrayList<>();
        for (Community community : communities) {
            CommunityDTO.HomePageCommunityResponseDTO myCommunityResponseDTO = CommunityDTO.HomePageCommunityResponseDTO.builder()
                    .id(community.getId())
                    .name(community.getName())
                    .profileImgPath(srcCommunity + community.getProfileImg())
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
            File newFile = MultipartUtils.saveImage(multipartFile, imageDirectoryPath, String.valueOf(id), password, salt);

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
    public CommunityDTO.IntroResponseDTO getIntro(int communityId) {

        Community community = communityRepository.findById(communityId);
        CommunityDTO.IntroResponseDTO introResponseDTO = CommunityDTO.IntroResponseDTO.builder()
                .name(community.getName())
                .createDate(community.getCreateDate().toLocalDate())
                .rule(community.getRule())
                .profileImgPath(srcCommunity + community.getProfileImg())
                .description(community.getDescription())
                .build();
        log.info("ffdf" + introResponseDTO.getProfileImgPath());
        return introResponseDTO;

    }


}