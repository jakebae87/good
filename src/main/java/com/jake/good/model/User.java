package com.jake.good.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // User 클래스가 MySQL에 테이블이 생성된다.
public class User {
    @Id //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 프로젝트에서 연결된 DB의 넘버링 전략을 따른다. (예, MySql의 auto_increment)
    private int id;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false, length = 100) //이후에 해쉬로 비밀번호를 암호화
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role; // 추후에 Enum을 사용한다. admin, user, manager의 도메인 설정을 위한 설정

    @CreationTimestamp //시간이 자동입력
    private Timestamp createDate;

}
