package org.zerock.s1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.s1.entity.ZerockMember;

public interface ZerockMemberRepository extends JpaRepository<ZerockMember, String> {

}
