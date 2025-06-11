package com.docker.backend;

import com.docker.backend.entity.user.Member;
import com.docker.backend.repository.member.MemberRepository;
import com.docker.backend.repository.user.AdminMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@SpringBootTest
class BackendApplicationTests {
	private static final Logger log = LoggerFactory.getLogger(BackendApplicationTests.class);
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private AdminMemberRepository adminMemberRepository;

	// mysql연결 확인용
	@Test
	public void showAllMembers() {
		List<Member> members = memberRepository.findAll();
		for (Member m : members) {

			System.out.println(m.getName());
			System.out.println(m.getCreatedAt());
			System.out.println(m.getUpdatedAt());
		}
	}

	//@Test
	public void showDescMember(){
		List<Member> mem = memberRepository.findAllByOrderByCreatedAtDesc();
		log.info("조회된 관리자 수: {}", mem.size());
		for (Member m : mem){
			log.info("관리자 이름: {}",m.getName());
		}
	}

}
