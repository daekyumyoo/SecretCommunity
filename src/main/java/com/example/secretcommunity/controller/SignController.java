package com.example.secretcommunity.controller;

import com.example.secretcommunity.dto.member.MemberDTO;
import com.example.secretcommunity.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

    private final SignService signService;

    @GetMapping("/sign-up")
    public String showSignUpPage() {
        return "sign/sign-up";
    }

    @GetMapping("/sign-in")
    public String showSignInPage() {
        return "sign/sign-in";
    }

    @GetMapping("/home")
    public String showHomePage(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("user", user.getUsername());
        return "home";
    }

    @PostMapping("/sign-up-proc")
    public String getPostList(MemberDTO.SignUpRequestDTO signUpDTO) {

        signService.signUp(signUpDTO);
        return "sign/sign-in";
    }

}
