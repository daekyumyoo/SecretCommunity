package com.example.secretcommunity.service;

import com.example.secretcommunity.dto.CommunityDTO;
import com.example.secretcommunity.model.Community;
import com.example.secretcommunity.persistence.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final CommunityRepository communityRepository;

    public List<CommunityDTO> testService() {
        List<Community> communities = communityRepository.findAll();

        List<CommunityDTO> communityDTOS = new ArrayList<>();
        for (Community community : communities) {
            CommunityDTO communityDTO = new CommunityDTO(community.getId(),community.getCreater(),community.getName());
            communityDTOS.add(communityDTO);
        }
        return  communityDTOS;
    }
}
