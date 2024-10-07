package org.example.security1.controller;



import org.example.security1.auth.PrincipalDetails;
import org.example.security1.model.User;
import org.example.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.Authenticator;

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


    //, @AuthenticationPrincipal 이거는 userDetails 을 가지고 있는데 userDetails는 PrincipalDetails 로 extend 시켰다.
    @GetMapping("/test/login")
    public @ResponseBody String loingTest(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails){
        System.out.println("/test/login==========================");
        PrincipalDetails principalDetails=((PrincipalDetails)authentication.getPrincipal());
        System.out.println(principalDetails.getUser());
        System.out.println(userDetails.getUsername());
        return "세션정보 확인하기";

    }

    //oauth2인경우
    //authentication 이 객체로 받아서 OAuth2User 객체로 다운 케스팅
    @GetMapping("/test/oauth")
    public @ResponseBody String oauthTest(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2Users){
        System.out.println("/test/oauth==========================");
        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();
        System.out.println(oAuth2User);
        System.out.println(oAuth2User.getAttributes());
        return "세션정보 확인하기";

    }

    @GetMapping("/test")
    public @ResponseBody String Test( @AuthenticationPrincipal PrincipalDetails userDetails){
        System.out.println("/test/oauth==========================");
        System.out.println(userDetails);
        System.out.println(userDetails.getAttributes());
        System.out.println(userDetails.getUser());
        return "세션정보 확인하기";

    }


}
