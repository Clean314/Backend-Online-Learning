package com.docker.backend.domain.user;


import com.docker.backend.domain.enums.MemberRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

// Member 는 사용자인데, Security 에서 제공하는 "USER"를 커스텀하기 위해 선언.
// Security 에 등록이 되어있고, 이 클래스를 ADMIN, EDUCATOR, STUDENT 가 상속받음.

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED) // JPA 에서도 상속 관계를 표현하기 위함
@DiscriminatorColumn(name = "member_type") // Member 의 자식 클래스 구분을 위해 사용 (DB 에는 저장되지 않음)
public abstract class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // JSON 응답에서 제외, 요청 시에만 사용
    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 30, nullable = false)
    private String name;

    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}