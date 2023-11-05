package com.alzzaipo.hexagonal.member.application.port.out.member;

import com.alzzaipo.hexagonal.common.Uid;
import com.alzzaipo.hexagonal.member.domain.member.Member;

import java.util.Optional;

public interface FindMemberPort {

    Optional<Member> findMember(Uid uid);
}
