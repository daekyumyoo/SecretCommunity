package com.example.secretcommunity.persistence;

import com.example.secretcommunity.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
