package com.example.kanj.api.response;

import java.util.ArrayList;

public class PullRequest {
    public String title;
    public String body;
    public PullUser user;
    public String html_url;

    public static ArrayList<PullRequest> mock(int start, int end) {
        ArrayList<PullRequest> list = new ArrayList<>();
        for (; start < end; start++) {
            PullRequest pr = new PullRequest();
            pr.title = start + " Title Text";
            pr.body = "Body Text can be long or blank or null. Need to handle all cases.\nLine 2 of Body\nLine 3";
            pr.user = PullUser.mock();
            list.add(pr);
        }
        return list;
    }
}
