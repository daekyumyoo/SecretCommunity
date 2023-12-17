package com.example.secretcommunity.persistence;

import com.example.secretcommunity.model.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Integer> {
}
