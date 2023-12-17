package com.example.secretcommunity.persistence;

import com.example.secretcommunity.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Object> findByUsername(String username);

    @Query(value = "SELECT m FROM Member m WHERE m.username = :username ")
    Member findMemberByUsername(@Param("username") String username);
}
