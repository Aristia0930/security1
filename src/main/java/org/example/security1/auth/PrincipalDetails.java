package org.example.security1.auth;

//시큐리티가 login를 낚아채서 로그인을 진행시킨다
//이때 로그인 진행이 완료가 되면 session을 만들어준다.
//Security ContextHolder 세션에 저장한다.
//Authentication 타입의 객체로 저장
// 이 안에는 User 정보가 있어
// 이 오브젝트의 타입은 UserDetails 타입객체이다.

//이걸해주면 userDetails 객체를 만드는거다 이거 다음으로는
//Authentication 를 만들어야 한다.
import lombok.Data;
import org.example.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails,OAuth2User {
    private User user;
    private Map<String, Object> attributes;

    //생성자
    //일반 로그인할때 사용
    public PrincipalDetails (User user){
        this.user=user;
    }
    //oauth로그인 사용
    public PrincipalDetails (User user,Map<String, Object> attributes){
        this.user=user;
        this.attributes=attributes;
    }



    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    //해당 유저의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //계정의 만료 여부를
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //이인증정보 만료되었는지 판단
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //계정활성하여부
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
