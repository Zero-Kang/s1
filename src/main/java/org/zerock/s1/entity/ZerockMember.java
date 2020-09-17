package org.zerock.s1.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ZerockMember {

    @Id
    private String email;

    private String password;

    private String name;

    private String picture;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<ZerockMemberRole> roleSet;

    public void addMemberRole(ZerockMemberRole role){

        if(roleSet == null){
            roleSet = new HashSet<>();
        }

        roleSet.add(role);

    }
}
