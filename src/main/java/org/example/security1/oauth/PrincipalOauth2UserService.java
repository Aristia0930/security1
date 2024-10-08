package org.example.security1.oauth;

import org.example.security1.auth.PrincipalDetails;
import org.example.security1.model.User;
import org.example.security1.oauth.provider.FacebookUserInfo;
import org.example.security1.oauth.provider.GoogleUserInfo;
import org.example.security1.oauth.provider.OAuth2UserInfo;
import org.example.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

//여기서 후처리한다 => 구글로 부터 유저정보를 후처리한다.
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Autowired
    @Lazy
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("유저요청정보"+userRequest);
        System.out.println("유저요청정보"+userRequest.getAccessToken());
        System.out.println("유저요청정보"+userRequest.getClientRegistration());
        System.out.println("유저요청정보"+userRequest.getClientRegistration().getClientId());
        System.out.println("getAttributes"+super.loadUser(userRequest).getAttributes());

        //userRequest이 정보들은  토큰을 액세스 하는거
        //loadUser(userRequest) 는 회원 프로필 을 받는거

        OAuth2User oAuth2User=super.loadUser(userRequest);
        String provider=userRequest.getClientRegistration().getRegistrationId();


        OAuth2UserInfo oAuth2UserInfo = null;

        if (Objects.equals(provider, "google")){
            //구글 로그인
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
//            id=oAuth2User.getAttribute("sub");
//            email=oAuth2User.getAttribute("email");
//            username="google"+id;



        }
        else if (Objects.equals(provider, "facebook")){
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
//            id = oAuth2User.getAttribute("id");
//            email = oAuth2User.getAttribute("email");
//            username = "facebook" + id;
        }
        else{
            System.out.println("잘못된 정보가 들어왔음");
        }

        String id=oAuth2UserInfo.getProviderId();
        String email=oAuth2UserInfo.getEmail();
        String username=provider+id;


        Random random= new Random();
        char[] charArray={'a','b','c','d','e','f','g'};

        String role="ROLE_USER";

        User userEntity=userRepository.findByUsername(username);

        if(userEntity==null){

            StringBuilder ps= new StringBuilder();
            for (int i=0; i<random.nextInt(5,7); i++){
                ps.append(charArray[random.nextInt(6)]);
            }
            System.out.println(ps);
            String password=bCryptPasswordEncoder.encode(ps);

            userEntity=User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .build();
            userRepository.save(userEntity);


        }




        //회원가입

        return new PrincipalDetails(userEntity,oAuth2User.getAttributes());
    }
}
