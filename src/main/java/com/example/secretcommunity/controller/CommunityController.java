package com.example.secretcommunity.controller;

import com.example.secretcommunity.dto.CommunityDTO;
import com.example.secretcommunity.service.CommunityService;
import com.example.secretcommunity.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@Slf4j
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
    public RedirectView createCommunity(@AuthenticationPrincipal UserDetails user, CommunityDTO.CreateRequestDTO createRequestDTO, Model model) throws IOException {

        int communityId = communityService.createCommunity(user, createRequestDTO);
        /*CommunityDTO.IntroResponseDTO introResponseDTO = communityService.getIntro(communityId);
        model.addAttribute("introData", introResponseDTO);*/

        return new RedirectView("intro/" + communityId);
    }


    @GetMapping("/intro/{communityId}")
    public String showIntro(@PathVariable("communityId")int communityId, @AuthenticationPrincipal UserDetails user, Model model) throws IOException {

        CommunityDTO.IntroResponseDTO introResponseDTO = communityService.getIntro(communityId, user);
        model.addAttribute("introData", introResponseDTO);

        return "community/intro";
    }


}
