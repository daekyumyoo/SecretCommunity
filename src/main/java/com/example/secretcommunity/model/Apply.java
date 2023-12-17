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
@Table(name = "apply")
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "applier")
    private Member applier;

    @ManyToOne(optional = false)
    @JoinColumn(name = "target_community")
    private Community targetCommunity;

    @Column(name = "apply_day")
    private LocalDateTime applyDay;

    @Column(name = "state")
    private int state;

    @Column(name = "comment")
    private String comment;

    @Column(name = "attached_img")
    private String attachedImg;

}
