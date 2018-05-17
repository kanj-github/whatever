package com.example.kanj.api;

import com.example.kanj.api.response.PullRequest;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubService {
    @GET("repos/{user}/{repo}/pulls?state=open")
    Observable<List<PullRequest>> getOpenPulls(@Path("user") String user, @Path("repo") String repo, @Query("page") int pageNumber);
}
