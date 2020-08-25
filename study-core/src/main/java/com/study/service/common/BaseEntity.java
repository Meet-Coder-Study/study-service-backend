package com.study.service.common;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
