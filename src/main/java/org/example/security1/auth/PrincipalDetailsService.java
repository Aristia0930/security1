package org.example.security1.auth;
////Authentication => 여기서 리턴된 값이 들어간다.

import org.example.security1.model.User;
import org.example.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//시큐리티 설정에서  login.loginProcessingUrl("/login");
// login 주소로 요청이 오면 여기로  자동으로 IOC 되어
//loadUserByUsername 가 실행된다.
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    //여기서 username는 파라미터로 넘어온 값이다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity=userRepository.findAllByUsername(username);
        if(userEntity!=null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
