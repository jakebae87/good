package com.jake.good.controller;

import com.jake.good.model.Role;
import com.jake.good.model.User;
import com.jake.good.repository.UserRepository;
import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String deleteUser(@PathVariable int id){
        try{
            userRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            return "삭제 실패";
        }
        return "삭제 완료 : " + id;
    }

    //save 함수는 id를 전달하지 않으면 insert
    //save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update
    @Transactional
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id,@RequestBody User requestUser){
        System.out.println("id : " + id);
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });

        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

//        userRepository.save(user);

        return user;
    }

    @GetMapping("/dummy/users")
    public List<User> list() {
        List<User> list = userRepository.findAll();
        return list;
    }

    //한 페이지당 2건의 데이터를 리턴받기
    @GetMapping("/dummy/user/page")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        List<User> users = userRepository.findAll(pageable).getContent();
        return users;
    }

    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 사용자는 없습니다.");
            }
        });
        return user;
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
