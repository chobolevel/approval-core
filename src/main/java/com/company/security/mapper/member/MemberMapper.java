package com.company.security.mapper.member;

import com.company.security.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    Member findOne(Member member);

    void create(Member member);

}
