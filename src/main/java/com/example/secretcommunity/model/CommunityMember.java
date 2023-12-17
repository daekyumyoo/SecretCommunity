package com.example.secretcommunity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "community_member")
public class CommunityMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "community_id")
    private Community communityId;

    @Column(name = "join_date")
    private LocalDateTime joinDate;

    @Column(name = "grade")
    private int grade;

}
