package com.docker.backend.repository.admin;

import com.docker.backend.dto.AdminMemberDTO;
import com.docker.backend.entity.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface AdminMemberRepository extends JpaRepository<Admin, Long> {
    @Query("SELECT m FROM Member m WHERE m.role != 'ADMIN' ORDER BY m.createdAt DESC LIMIT 10")
    List<AdminMemberDTO> orderByTOP10Member();

}
