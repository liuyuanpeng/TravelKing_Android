package com.cy.travelking.entity;

import java.util.List;

public class Login {

    private List<Role> roles;
    private TokenSession token_session;
    private User user;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public TokenSession getToken_session() {
        return token_session;
    }

    public void setToken_session(TokenSession token_session) {
        this.token_session = token_session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
