package org.zerock.s1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.s1.entity.ZerockMember;
import org.zerock.s1.entity.ZerockMemberRole;

import java.util.stream.IntStream;

@SpringBootTest
public class ZerockMemberRepositoryTests {

    @Autowired
    private ZerockMemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies() {

        IntStream.rangeClosed(1,100).forEach(i -> {

            ZerockMember member = ZerockMember.builder()
                    .email("user"+i+"@zerock.org")
                    .name("사용자"+i)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            //default
            member.addMemberRole(ZerockMemberRole.USER);

            if(i > 80){
                member.addMemberRole(ZerockMemberRole.MANAGER);
            }
            if(i > 90) {
                member.addMemberRole(ZerockMemberRole.ADMIN);
            }

            repository.save(member);

        });

    }

    @Test
    public void testRead() {

        ZerockMember member = repository.findById("user95@zerock.org").get();

        System.out.println(member);

        member.getRoleSet().forEach(role -> System.out.println(role.name()));
    }

}
