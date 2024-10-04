package org.example.security1.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
//여기서 후처리한다 => 구글로 부터 유저정보를 후처리한다.
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("유저요청정보"+userRequest);
        System.out.println("유저요청정보"+userRequest.getAccessToken());
        System.out.println("유저요청정보"+userRequest.getClientRegistration());
        System.out.println("유저요청정보"+userRequest.getClientRegistration().getClientId());
        System.out.println("getAttributes"+super.loadUser(userRequest).getAttributes());

        //회원가입

        return super.loadUser(userRequest);
    }
}
