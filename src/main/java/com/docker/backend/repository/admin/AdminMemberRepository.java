package com.docker.backend.repository.admin;

import com.docker.backend.entity.user.Admin;
import com.docker.backend.enums.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdminMemberRepository extends JpaRepository<Admin, Long> {


}
