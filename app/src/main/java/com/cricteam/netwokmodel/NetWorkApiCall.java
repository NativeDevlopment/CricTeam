package com.cricteam.netwokmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.cricteam.listner.OnApiResponse;
import com.cricteam.utils.CommonUtils;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Amar on 9/18/2017.
 */

public class NetWorkApiCall {
   static NetWorkApiCall netWorkApiCall;
    private NetWorkApiCall (){

    }
    public static NetWorkApiCall getInstance(){
       if(netWorkApiCall==null)
           netWorkApiCall= new NetWorkApiCall();
        return netWorkApiCall;

    }



  public void  getApiResponse (final Context context , Call<Response> call , final OnApiResponse onApiResponse){
if(CommonUtils.isOnline(context)) {
  Log.e( "url",""+ call.request().url());
    call.enqueue(new Callback<Response>() {
        @Override
        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
            System.out.println("API Data" + new Gson().toJson(response.body()));
            if (response != null && response.body() != null) {
                if (response.body().getStatusCode() == 200) {
                    if(response.body().data!=null){
                    onApiResponse.onResponse(response.body());}else {
                        onApiResponse.onResponse(null);
                        ShowMessage(context, response.body().message);
                    }
                } else {
                    onApiResponse.onResponse(null);
                    ShowMessage(context, response.body().message);
                }
            }
        }

        @Override
        public void onFailure(Call<Response> call, Throwable t) {

            onApiResponse.onResponse(null);
            ShowMessage(context, "Server not responding");
        }
    });
}else{
    Toast.makeText(context,"No network Availabel",Toast.LENGTH_SHORT).show();
    onApiResponse.onResponse(null);
}
   }
  void ShowMessage (Context context ,String message){

   AlertDialog.Builder builder= new AlertDialog.Builder(context).setMessage(message).
           setPositiveButton("Ok", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   dialogInterface.dismiss();
               }
           });
      builder.show();
  }
}
