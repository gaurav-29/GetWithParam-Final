package com.example.getwithparam.PostWithParam;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.getwithparam.GetWithParam.RegisterActivity;
import com.example.getwithparam.Model.GeneralModel;
import com.example.getwithparam.R;
import com.example.getwithparam.Utils.GeneralAPIClient;
import com.example.getwithparam.Utils.GeneralAPIInterface;
import com.example.getwithparam.databinding.ActivityLoginBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity2 extends AppCompatActivity {

    ActivityLoginBinding mBinding;
    LoginActivity2 ctx = LoginActivity2.this;
    GeneralAPIInterface loginApiInterface;
    String BASE_URL = "http://api.cssolution.in/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(ctx, R.layout.activity_login);

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
        mBinding.btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!Patterns.EMAIL_ADDRESS.matcher(mBinding.etEmail2.getText().toString().trim()).matches() ||
                        mBinding.etEmail2.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(ctx, "Please enter valid email", Toast.LENGTH_SHORT).show();

                else if (mBinding.etPassword2.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(ctx, "Please enter password", Toast.LENGTH_SHORT).show();

                else GetResponse();
            }
        });

        mBinding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void GetResponse() {

        mBinding.progressBar.getIndeterminateDrawable();
        mBinding.progressBar.setVisibility(View.VISIBLE);

        if(isInternetConnected()) {

            loginApiInterface = GeneralAPIClient.getClient(BASE_URL).create(GeneralAPIInterface.class);

            Call<GeneralModel> call2 = loginApiInterface.getDetails(mBinding.etEmail2.getText().toString(), mBinding.etPassword2.getText().toString());

            call2.enqueue(new Callback<GeneralModel>() {
                @Override
                public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {

                    Log.d("SHIV", response.body().toString());

                    if (response.code() == 200) {
                        Toast.makeText(LoginActivity2.this, response.body().msg, Toast.LENGTH_SHORT).show();

                        GeneralModel res = response.body();
                        ArrayList<GeneralModel.Data> dataList = res.data;

                        Log.d("SHIV", dataList.toString());

                    } else {
                        Toast.makeText(ctx, "Response not received successfully", Toast.LENGTH_LONG).show();
                    }
                    mBinding.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<GeneralModel> call, Throwable t) {

                    Toast.makeText(ctx, "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("ERROR", t.getMessage());
                    mBinding.progressBar.setVisibility(View.GONE);
                }
            });
        }
        else{
            Toast.makeText(ctx, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
}