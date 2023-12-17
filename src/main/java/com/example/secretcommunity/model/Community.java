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
@Table(name = "community")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "creater")
    private Member creater;

    @Column(name = "name")
    private String name;

    @Column(name = "create_day")
    private LocalDateTime createDay;

    @Column(name = "role")
    private int role;

    @Column(name = "state")
    private int state;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "description")
    private String description;

}
