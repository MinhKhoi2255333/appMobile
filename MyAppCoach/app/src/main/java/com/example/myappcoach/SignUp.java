package com.example.myappcoach;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUp extends Fragment {


    SharedPreferences sharedPreferences;
    private TextInputEditText user, pass, phone, confirmPass;
    private CheckBox rememberUser;
    private Button btSignup;
    private String url = MainActivity.url + "/insertAccount.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_singup, container, false);

        initialize(view);

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Objects.requireNonNull(user.getText()).toString().trim();
                String ipPhone = Objects.requireNonNull(phone.getText()).toString().trim();
                String password = Objects.requireNonNull(pass.getText()).toString().trim();
                String password2 = Objects.requireNonNull(confirmPass.getText()).toString().trim();

                checkInput(name, ipPhone, password, password2);
                inputAccountDatabase(name, password, ipPhone);
            }
        });

        return view;
    }

    public void inputAccountDatabase(String name, String password, String phoneinput){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            setDataSharedPreferences(name, password);
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                            Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.trim().equals("errorphone")){
                            phone.setError("This phone number is already");
                        }
                        else if(response.trim().equals("errorusername")){
                            user.setError("This username is already");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("Login error", error+"");
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", name);
                params.put("password", password);
                params.put("phone", phoneinput);
                return params;
            }
        };
        requestQueue.add(stringRequest);

        setDataSharedPreferences(name, phoneinput);
//        Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
    }

    public void checkInput(String name, String ipPhone, String password, String password2){
        if(name.isEmpty()){
            user.setError("Please enter your username");
        }
        if(ipPhone.isEmpty()){
            phone.setError("Please enter your phone");
        }
        if(password.isEmpty()){
            pass.setError("Please enter your password");
        }
        if(password.length() < 6){
            pass.setError("Please enter a password of at least 6 characters");
        }
        if(password2.isEmpty() ){
            confirmPass.setError("Please confirm your password");
        }
        if(!password2.equals(password)){
            confirmPass.setError("Please confirm your password again");
        }
    }

    private void setDataSharedPreferences(String name, String password) {

            sharedPreferences = getActivity().getSharedPreferences("DataAccount", Context.MODE_PRIVATE);

            if(rememberUser.isChecked()){
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", name);
                editor.putString("password", password);
                editor.putBoolean("remember", true);
                editor.commit();
            }
            else{
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("username");
                editor.remove("password");
                editor.remove("remember");
                editor.commit();
            }
    }


    private void initialize(View view){
        user = view.findViewById(R.id.enterUsername);
        pass = view.findViewById(R.id.enterPassword);
        phone = view.findViewById(R.id.enterPhone);
        confirmPass = view.findViewById(R.id.enterConfirmPassword);
        rememberUser = view.findViewById(R.id.cbRememberUser);
        btSignup = view.findViewById(R.id.btSignup);
    }


}
