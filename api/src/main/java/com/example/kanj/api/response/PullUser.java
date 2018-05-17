package com.example.kanj.api.response;

public class PullUser {
    public String login;
    public String avatar_url;

    static PullUser mock() {
        PullUser pu = new PullUser();
        pu.login = "Mock User";
        pu.avatar_url = "https://avatars2.githubusercontent.com/u/13774768?v=4";
        return pu;
    }
}
