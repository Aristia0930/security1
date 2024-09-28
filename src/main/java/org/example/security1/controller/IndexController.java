package org.example.security1.controller;

import org.example.security1.model.User;
import org.example.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //view를 리턴하겠다.
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //머스테치 사용
    @GetMapping({"","/"})
    public String index(){
        return "index";
    }
    //@ResponseBody를 넣으면 데이터값으로 전달한다. view 가아닌.
    @GetMapping("/user")
    public @ResponseBody  String user(){
        return "user";

    }

    @GetMapping("/admin")
    public @ResponseBody  String admin(){
        return "admin";

    }

    @GetMapping("/manager")
    public @ResponseBody  String manager(){
        return "manager";

    }
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";

    }
    @GetMapping("/login")
    public String login(){
        return "login";

    }


    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";

    }
    @PostMapping("/join")
    public String join(User user){
        user.setRole("ROLE_USER");
        //패스워드 암호화 해주어지만 시큐리티로 접속 가능
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/loginForm";

    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/info2")
    public @ResponseBody String info2(){
        return "개인정보2";
    }

}
