package com.docker.backend.entity.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("EDUCATOR")
public class Educator extends Member {
    private String educatorNumber;
}
