package com.docker.backend.entity.user;

import com.docker.backend.enums.MemberRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("ADMIN")
public class Admin extends Member {
    private String adminNumber;
}