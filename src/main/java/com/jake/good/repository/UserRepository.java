package com.jake.good.repository;

import com.jake.good.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// DAO 역할을 한다.
// 자동으로 bean 등록이 된다.
public interface UserRepository extends JpaRepository<User,Integer> {
}
