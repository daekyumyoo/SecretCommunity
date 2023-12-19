package com.example.secretcommunity.controller;

import com.example.secretcommunity.dto.community.CommunityDTO;
import com.example.secretcommunity.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

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

    @GetMapping("/main/{communityId}")
    public String showMainPage(@PathVariable("communityId")int communityId, @AuthenticationPrincipal UserDetails user, Model model) {
        try {

            CommunityDTO.MainResponseDTO mainResponseDTO = communityService.getMain(communityId, user);
            model.addAttribute("mainData" ,mainResponseDTO);
            return "community/main";
        } catch (Exception e) {
            model.addAttribute("errorMsg", e.getMessage());
            return "redirect:/community/intro/" + communityId;
        }
    }


}
