package com.study.service.user;

public enum Role {
    ADMIN, MEMBER;

    private final static String ROLE_PREFIX = "ROLE_";

    public String getRoleType() {
        return ROLE_PREFIX + this.name();
    }
}
