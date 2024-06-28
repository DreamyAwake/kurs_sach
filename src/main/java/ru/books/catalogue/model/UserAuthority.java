package ru.books.catalogue.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
    PLACE_ACCES,
    MANAGE_ACCES,
    FULL;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
