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
@Table(name = "post_comment")
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "writer")
    private Member writer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "content")
    private String content;

    @Column(name = "comment_date")
    private LocalDateTime commentDate;

    @Column(name = "step")
    private int step;

    @Column(name = "group_num")
    private int groupNum;

    @Column(name = "modified")
    private byte modified;

}
