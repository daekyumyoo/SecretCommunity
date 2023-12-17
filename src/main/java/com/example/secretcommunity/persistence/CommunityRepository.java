package com.example.secretcommunity.persistence;

import com.example.secretcommunity.model.Community;
import com.example.secretcommunity.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Integer> {

    /**
     * @param state 커뮤니티 상태
     * @return Entity 리스트
     */
    List<Community> findAllByState(int state);

    List<Community> findByStateAndCreater(int state, Member creater);

    Community findById(int id);

}
