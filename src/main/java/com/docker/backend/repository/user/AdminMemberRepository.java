package com.docker.backend.repository.user;

import com.docker.backend.entity.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMemberRepository extends JpaRepository<Admin, Long> {


}
