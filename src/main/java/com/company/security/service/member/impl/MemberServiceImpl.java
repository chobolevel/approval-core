package com.company.security.service.member.impl;

import com.company.security.entity.Member;
import com.company.security.mapper.member.MemberMapper;
import com.company.security.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Member findOne(Member member) throws Exception {
        return memberMapper.findOne(member);
    }

    @Override
    public void create(Member member) throws Exception {
        member.setId(UUID.randomUUID().toString());
        member.setPw(passwordEncoder.encode(member.getPassword()));
        member.setRole("SYSTEM_ADMIN");
        memberMapper.create(member);
    }

}
