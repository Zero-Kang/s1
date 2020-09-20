package org.zerock.s1.security.user;

import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.zerock.s1.entity.ZerockMember;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class CustomAuthUser extends User implements OAuth2User, Serializable {

    private String name;
    private boolean social;

    private ZerockMember zerockMember;

    private Map<String, Object> attributes;

    public CustomAuthUser(ZerockMember zerockMember) {
        super(zerockMember.getEmail(),
                zerockMember.getPassword(),
                zerockMember.getRoleSet().stream().map(autority -> new SimpleGrantedAuthority("ROLE_"+autority.name())).collect(Collectors.toSet()));
        this.name = zerockMember.getName();
        this.zerockMember = zerockMember;
        this.social = false;
    }

    public CustomAuthUser(String email, String password, Map<String, Object> attributes){
        super(email,password, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

        this.name = email;
        this.social = true;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

}
