package com.discordwebhookmessagesender;

public class WebhookToken {

    private String token;

    public WebhookToken() {
        token = new String();
    }

    public WebhookToken(String string) {
        this.token = string;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "[\"" + token + "\"]";
    }
}
