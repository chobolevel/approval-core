package com.company.security.service.member;

import com.company.security.entity.Member;

public interface MemberService {

    Member findOne(Member member) throws Exception;

    void create(Member member) throws Exception;

}
