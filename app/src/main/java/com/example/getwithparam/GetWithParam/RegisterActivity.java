package com.example.getwithparam.GetWithParam;

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

import com.example.getwithparam.Utils.GeneralAPIClient;
import com.example.getwithparam.Utils.GeneralAPIInterface;
import com.example.getwithparam.Model.GeneralModel;
import com.example.getwithparam.GetWithDirectUrl.LoginActivity;
import com.example.getwithparam.R;
import com.example.getwithparam.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding mBinding;
    public RegisterActivity ctx = RegisterActivity.this;
    String BASE_URL = "http://api.cssolution.in/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);

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
        mBinding.btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mBinding.etFirstName.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(ctx, "Please enter first name", Toast.LENGTH_SHORT).show();

                else if (mBinding.etLastName.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(ctx, "Please enter last name", Toast.LENGTH_SHORT).show();

                else if (!Patterns.EMAIL_ADDRESS.matcher(mBinding.etEmail.getText().toString().trim()).matches() ||
                        mBinding.etEmail.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(ctx, "Please enter valid email", Toast.LENGTH_SHORT).show();

                else if (mBinding.etMobile.getText().toString().trim().equalsIgnoreCase("") ||
                        mBinding.etMobile.getText().toString().trim().length() < 10)
                    Toast.makeText(ctx, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();

                else if (mBinding.etCity.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(ctx, "Please enter city", Toast.LENGTH_SHORT).show();

                else if (mBinding.etPassword.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(ctx, "Please enter password", Toast.LENGTH_SHORT).show();

                else GetResponse();

            }
        });

        mBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void GetResponse() {

        mBinding.progressBar.getIndeterminateDrawable();
        mBinding.progressBar.setVisibility(View.VISIBLE);

        if(isInternetConnected()) {

            GeneralAPIClient.getClient(BASE_URL).create(GeneralAPIInterface.class).getStatus(mBinding.etFirstName.getText().toString(), mBinding.etLastName.getText().toString(),
                    mBinding.etEmail.getText().toString(), mBinding.etMobile.getText().toString(), mBinding.etCity.getText().toString(),
                    mBinding.etPassword.getText().toString()).enqueue(new Callback<GeneralModel>() {
                @Override
                public void onResponse(Call<GeneralModel> call, Response<GeneralModel> response) {
                    Log.d("SHIV", response.body().toString());

                    if(response.code()==200) {
                        Toast.makeText(RegisterActivity.this, response.body().msg, Toast.LENGTH_SHORT).show();
                    }
                    else{
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
        else {
                Toast.makeText(ctx, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
    }
}