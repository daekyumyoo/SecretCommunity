package com.example.secretcommunity.controller;

import com.example.secretcommunity.dto.board.BoardDTO;
import com.example.secretcommunity.dto.community.CommunityDTO;
import com.example.secretcommunity.dto.post.PostDTO;
import com.example.secretcommunity.persistence.PostRepository;
import com.example.secretcommunity.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/write/{communityId}")
    public String showWritePage(@PathVariable("communityId")int communityId, @AuthenticationPrincipal UserDetails user, Model model) {
        List<BoardDTO.MainResponseDTO> responseDTOS = postService.getBoards(communityId, user);
        model.addAttribute("boards", responseDTOS);
        return "post/write";
    }

    @PostMapping("/write-proc")
    public RedirectView createCommunity(@AuthenticationPrincipal UserDetails user, PostDTO.WriteRequestDTO writeRequestDTO, Model model) throws IOException {

        int id = postService.postWrite(user, writeRequestDTO);
        return new RedirectView("view/" + id);
    }

    @GetMapping("/view/{postId}")
    public String view(@PathVariable("postId")int postId, @AuthenticationPrincipal UserDetails user, Model model) throws IOException {

        PostDTO.ViewResponseDTO viewResponseDTO = postService.view(postId, user);
        model.addAttribute("viewData", viewResponseDTO);

        return "post/view";
    }
}
