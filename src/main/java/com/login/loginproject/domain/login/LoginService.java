package com.login.loginproject.domain.login;

import com.login.loginproject.domain.member.Member;
import com.login.loginproject.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {


    private final MemberRepository memberRepository;

    public Member login(String loginId, String password){
        /*
        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
        Member member = findMemberOptional.get();
        if(member.getPassword().equals(password)){
            return member;
        }else return null;
         */

        return memberRepository.findByLoginId(loginId)
                .filter(m ->m.getPassword().equals(password))
                .orElse(null);

    }


}
