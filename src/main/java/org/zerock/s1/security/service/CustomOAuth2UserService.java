package org.zerock.s1.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.s1.entity.ZerockMember;
import org.zerock.s1.entity.ZerockMemberRole;
import org.zerock.s1.repository.ZerockMemberRepository;
import org.zerock.s1.security.user.CustomAuthUser;

import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final ZerockMemberRepository zerockMemberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("---------------------------CustomOAuth2UserService--------------------------------");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();
        log.info(clientName);
        log.info(oAuth2User.getAuthorities());

        //only for google
        String email = null;

        switch (clientName){
            case "Google":
                email = oAuth2User.getAttribute("email");
                break;
            case "naver":
                Map<String, Object> attrs = (Map<String, Object>) oAuth2User.getAttribute("response");

                email = (String)attrs.get("email");
                break;
        }

        log.info("EMAIL: " + email);

        //가입이 되어 있는지 확인 - 네이버나 카카오의 경우는 다르게 처리해야 함..데이터 자체가 다르다.
        Optional<ZerockMember> result = zerockMemberRepository.findById(email);

        log.info("result: " + result);
        //데이터베이스에 이미 있는 사용자라면
        if(result.isPresent()){
            return new CustomAuthUser(result.get());
        }

        //데이터베이스에 없는 사용자라면 임시패스워드 1111로 해서 가입
        String password = passwordEncoder.encode("1111");
        ZerockMember newMember = ZerockMember.builder()
                .email(email)
                .password(password)
                .name(email)
                .build();
        newMember.addMemberRole(ZerockMemberRole.USER);

        zerockMemberRepository.save(newMember);
        log.info("new Member Added...");

        log.info("===================================================================================");
        return new CustomAuthUser(email, password, oAuth2User.getAttributes());
    }
}