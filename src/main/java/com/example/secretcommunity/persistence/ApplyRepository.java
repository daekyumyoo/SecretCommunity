package com.example.secretcommunity.persistence;

import com.example.secretcommunity.model.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply, Integer> {
}
