package com.example.secretcommunity.controller;

import com.example.secretcommunity.dto.CommunityDTO;
import com.example.secretcommunity.service.CommunityService;
import com.example.secretcommunity.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/create")
    public String showSignUpPage() {
        return "community/create";
    }

    @PostMapping("/create-proc")
    public String createCommunity(@AuthenticationPrincipal UserDetails user, CommunityDTO.CreateRequestDTO createRequestDTO, Model model) {

        int communityId = communityService.createCommunity(user, createRequestDTO);
        CommunityDTO.IntroResponseDTO introResponseDTO = communityService.getIntro(communityId);
        model.addAttribute("introData", introResponseDTO);

        return "community/intro";
    }


}
