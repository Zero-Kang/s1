package org.zerock.s1.security.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.s1.entity.ZerockMember;
import org.zerock.s1.repository.ZerockMemberRepository;
import org.zerock.s1.security.user.CustomAuthUser;

import java.util.Arrays;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ZerockMemberRepository zerockMemberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("..loadUserByUsername: " + username);

        //email
        Optional<ZerockMember> result = zerockMemberRepository.findById(username);

        log.info("result: " + result);

        if(result.isEmpty()){
            throw new UsernameNotFoundException("Not Exist");
        }

        ZerockMember zerockMember = result.get();

        log.info("zorockMember: " + zerockMember);

        return new CustomAuthUser(zerockMember);
    }
}
