package com.docker.backend.entity.user;

import com.docker.backend.enums.MemberRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("EDUCATOR")
public class Educator extends Member {
    private String educatorNumber;
}
