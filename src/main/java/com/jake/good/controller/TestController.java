package com.jake.good.controller;

import com.jake.good.model.Role;
import com.jake.good.model.User;
import com.jake.good.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) throws Throwable {
        //람다식 활용
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 사용자는 없습니다. :" + id);
        });
        /*
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 사용자는 없습니다. :" + id);
            }
        });
        */
        return user;

        //요청이 웹브라으저로부터 들어오면, 서버는 user 객체(자바오브젝트)로 반환한다.
        //웹브라우저는 자바 객체를 이해할 수 없기 때문에, 스프링부트가 response시에 MessageConverter를 작동시킨다.
        //이는 반환된 자바 오브젝트를 Jackson 라이브러리를 통하여 json 타입으로 변환하여 브라우저에 전달한다.
    }

    @PostMapping("/dummy/join")
    public String join(User user) {
        System.out.println("id: " + user.getId());
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("email: " + user.getEmail());
        System.out.println("role: " + user.getRole());
        System.out.println("createDate: " + user.getCreateDate());

        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return "가입완료";

    }
}
