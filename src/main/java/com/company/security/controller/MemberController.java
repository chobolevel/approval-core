package com.company.security.controller;

import com.company.security.entity.Member;
import com.company.security.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("login")
    public String getLoginPage(Model model,
                               @RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "exception", required = false) String exception) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/member/login";
    }

    @GetMapping("signup")
    public String getSignup() {
        return "/member/signup";
    }

    @PostMapping("signup")
    public String postSignup(Member member) throws Exception {
        memberService.create(member);
        return "redirect:/member/login";
    }

    @GetMapping("success")
    public String success() {
        return "/member/success";
    }

}
