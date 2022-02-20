package com.example.getwithparam.PostWithRawJson;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.getwithparam.Model.RawModel;
import com.example.getwithparam.PostWithRaw.RawActivity;
import com.example.getwithparam.R;
import com.example.getwithparam.Utils.GeneralAPIClient;
import com.example.getwithparam.Utils.GeneralAPIInterface;
import com.example.getwithparam.databinding.ActivityRawBinding;
import com.example.getwithparam.databinding.ActivityRawJsonBinding;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RawJsonActivity extends AppCompatActivity {

    ActivityRawJsonBinding mBinding;
    RawJsonActivity mContext = RawJsonActivity.this;
    GeneralAPIInterface jsonApiInterface;
    String BASE_URL = "http://18.219.53.32/api/ChefCommon/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_raw_json);
        
        onClickListner();
    }

    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())) {
            return true;
        }
        else{
            return false;
        }
    }

    private void onClickListner() {
        mBinding.tvGetResponse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               GetResponse();
            }
        });
    }

    private void GetResponse() {

        mBinding.progressBar.getIndeterminateDrawable();
        mBinding.progressBar.setVisibility(View.VISIBLE);

        if(isInternetConnected()) {

            String token = "qnVgS6yATquwH8VtYxM2vyrKeibF5tsK1dfB506rAb5Ub0VmeXy8_Oyy0UhqJcfMvHFsm_Guqfo6o_RtNld7CfqyIsAIiXcOll8qAimGf4eW_P7LA9Hr1ByjrIMLBLcrXx0Zoj96bn61oCBB7628asqXZK-U2ydKadshJBjPJA4RrySz66QGQq7GMM7hzf0jWg44aPZ4sDVI1imU0ZxQ9ryOKBMZfrnMqCBoEuH-oV7F5Is7pibRslx0Otdoy9xffvkr5MPwAphGgE4BWL3NTW7T58PRWxod1o2lepcBDrA75vjEisMrCAJmxNkEaoI7";
            jsonApiInterface = GeneralAPIClient.getClient(BASE_URL).create(GeneralAPIInterface.class);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id","b922f43b-3363-4d42-a266-65bd9204b8e5");

                Call<RawModel> call2 = jsonApiInterface.getNotifications("Bearer "+token, jsonObject);

                call2.enqueue(new Callback<RawModel>() {
                    @Override
                    public void onResponse(Call<RawModel> call, Response<RawModel> response) {

                        Log.d("SHIV", response.body().toString());

                        if (response.code() == 200) {

                            Toast.makeText(mContext, response.body().Message, Toast.LENGTH_SHORT).show();

                            RawModel res = response.body();
                            ArrayList<RawModel.Data> dataList = res.Data;

                            Log.d("SHIV", dataList.toString());

                        } else {
                            Toast.makeText(mContext, "Response not received successfully", Toast.LENGTH_LONG).show();
                        }
                        mBinding.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<RawModel> call, Throwable t) {

                        Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
                        Log.d("ERROR", t.getMessage());
                        mBinding.progressBar.setVisibility(View.GONE);
                    }
                });
        }
        else{
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
}