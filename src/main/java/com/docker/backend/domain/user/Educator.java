package com.docker.backend.domain.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("EDUCATOR") // 이 자식 클래스는 EDUCATOR 라는 구분값을 가짐
public class Educator extends Member {
    private String educatorNumber;
}