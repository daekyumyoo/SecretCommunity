package com.example.secretcommunity.persistence;

import com.example.secretcommunity.model.Board;
import com.example.secretcommunity.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    List<Board> findAllByCommunityAndState(Community community, int state);

    Board findById(int id);
}
