package com.docker.backend;

import com.docker.backend.entity.user.Member;
import com.docker.backend.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BackendApplicationTests {
	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void showAllMembers() {
		List<Member> members = memberRepository.findAll();
		for (Member m : members) {

			System.out.println(m.getName());
		}
	}

}
