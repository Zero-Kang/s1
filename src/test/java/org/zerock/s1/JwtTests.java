package org.zerock.s1;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.s1.security.token.JwtUtils;

@SpringBootTest
public class JwtTests {

    @Autowired
    private JwtUtils jwtUtils;

    @Test
    public void testValidate()throws Exception{

        String jwt = jwtUtils.generateJwtToken("user12@zerock.org");

        System.out.println("-----------------------------------------");
        System.out.println(jwt);

        System.out.println(jwtUtils.validateJwtToken(jwt));

    }

}
