package com.example.secretcommunity.persistence;

import com.example.secretcommunity.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {
}
