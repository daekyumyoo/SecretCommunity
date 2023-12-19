package com.example.secretcommunity.persistence;

import com.example.secretcommunity.model.Community;
import com.example.secretcommunity.model.CommunityMember;
import com.example.secretcommunity.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Integer> {

    @Query(value = "SELECT cm.community FROM CommunityMember cm WHERE cm.member = :member AND cm.community.state = :state ")
    List<Community> findJoinCommunity(@Param("member") Member member, @Param("state") int state);

    boolean existsByCommunityAndMember(Community community, Member member);
}
