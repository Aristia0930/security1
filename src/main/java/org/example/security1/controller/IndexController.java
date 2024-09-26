package org.example.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //view를 리턴하겠다.
public class IndexController {

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

    @GetMapping("/login")
    public String login(){
        return "login";

    }

    @GetMapping("/join")
    public String join(){
        return "join";

    }

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc(){
        return "회원가입 완료됨";

    }

}
