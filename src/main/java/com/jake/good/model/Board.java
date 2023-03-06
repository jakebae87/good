package com.jake.good.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob //대용량 데이터
    private String content;

    @ColumnDefault("0")
    private int count; //조회수

    @ManyToOne(fetch = FetchType.EAGER) //Many는 Board이고 One은 User, 즉 User 한명이 여러개(Many)의 게시글을 쓸 수 있다.
    @JoinColumn(name = "userId")
    private User user; // DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // mappedBy 연관관계의 주인이 아니다! (FK가 아니다) DB에 컬럼을 만들지 마라.
    // 단지 board를 선택할 때 join 문을 통해 값을 얻으려고 필요한 것.
    // (mappedBy = "board") board는 Reply 클래스의 필드값 이름
    private List<Reply> reply;

    @CreationTimestamp
    private Timestamp createDate;
}
