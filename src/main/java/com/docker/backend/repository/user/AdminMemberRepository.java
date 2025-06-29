package com.docker.backend.repository.user;

import com.docker.backend.domain.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMemberRepository extends JpaRepository<Admin, Long> {


}
