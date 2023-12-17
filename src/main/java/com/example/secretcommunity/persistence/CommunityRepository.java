package com.example.secretcommunity.persistence;

import com.example.secretcommunity.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Integer> {
}
