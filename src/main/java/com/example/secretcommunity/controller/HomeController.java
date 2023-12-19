package com.example.secretcommunity.controller;

import com.example.secretcommunity.dto.CommunityDTO;
import com.example.secretcommunity.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final CommunityService communityService;

    /*@GetMapping("/")
    public String showHomePage(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("user", user.getUsername());
        return "home";
    }*/

    /*@PostMapping("/list")
    public String getList(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("user", user.getUsername());
        return "home";
    }*/

    @GetMapping("/")
    public String getList(@AuthenticationPrincipal UserDetails user, Model model) throws IOException {
        List<CommunityDTO.HomePageCommunityResponseDTO> allCommunityDTOs = communityService.getAllCommunity();
        model.addAttribute("allCommunityList", allCommunityDTOs);
        List<CommunityDTO.HomePageCommunityResponseDTO> myCommunityDTOs = communityService.getAllMyCommunity(user);
        model.addAttribute("myCommunityList", myCommunityDTOs);
        List<CommunityDTO.HomePageCommunityResponseDTO> joinCommunityDTOs = communityService.getAllJoinCommunity(user);
        model.addAttribute("joinCommunityList", joinCommunityDTOs);
        return "home";
    }

}
