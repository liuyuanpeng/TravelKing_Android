package com.cy.travelking.entity;

public class Captcha {

    private String username;
    private String captcha_session_id;
    private String captcha;
    private String description;
    private int expires;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaptcha_session_id() {
        return captcha_session_id;
    }

    public void setCaptcha_session_id(String captcha_session_id) {
        this.captcha_session_id = captcha_session_id;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }
}
