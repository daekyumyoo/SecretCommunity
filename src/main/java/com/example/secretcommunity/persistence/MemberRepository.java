package com.example.secretcommunity.persistence;

import com.example.secretcommunity.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Object> findByUsername(String username);

//    Member findByUsername(String username);
}
