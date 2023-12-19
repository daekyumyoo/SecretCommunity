package com.example.secretcommunity.persistence;

import com.example.secretcommunity.model.Community;
import com.example.secretcommunity.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByBoardCommunityAndState(Community community, int state);

    Post findById(int id);
}
