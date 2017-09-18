package com.cricteam.netwokmodel;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


/**
 * The interface Api service.
 */
public interface ApiService {
 
    /*Retrofit get annotation with our URL
       And our method that will return us the list ob Book
    */

    /**
     * Gets town list.
     *
     * @param mobileNo the action
     * @return the otp
     */
    @GET("sendOtp")
    Call<Response> sendOtp(@Path("mobileNo") int mobileNo);

    @POST("veryifyOtp")
    Call<Response> verifyOtp(@Body VerifyOtp  verifyOtp);
    @POST("updateUser")
    Call<Response> updateUser(@Body UserDetails  userDetails);
    @POST("saveTeam")
    Call<Response> saveTeam(@Body TeamRequest  teamRequest);
    @POST("addPlayers")
    Call<Response> addPlayers(@Body AddPlayerRequest  addPlayerRequest);
    @POST("updatePlayer")
    Call<Response> updatePlayers(@Body PlayerDetails  playerDetails);
    @POST("teamRequest")
    Call<Response> sendTeamCircleRequest(@Body TeamCircleRequest  teamCircleRequest);
    @POST("findTeam")
    Call<Response> findTeam(@Body FindTeamRequest  findTeamRequest , QueryMap pageMap);
    @POST("findTeamCircle")
    Call<Response> findTeamCircle(@Body FindTeamRequest  findTeamRequest , QueryMap pageMap);
    @GET("getUser")
    Call<Response> getUser(@Path("userId")int userId);
    /*http://localhost:8181/getTeamDetails?teamId=1&userId=1&ownTeamId=2*/
    @GET("getTeamDetails")
    Call<Response> getTeamDetails(QueryMap teamids);




}