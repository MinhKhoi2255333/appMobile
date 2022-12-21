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
import com.android.volley.DefaultRetryPolicy;
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

public class Login extends Fragment {

    SharedPreferences sharedPreferences;
    private TextInputEditText user, pass;
    private CheckBox rememberUser;
    private Button btLogin;
    private String url = MainActivity.url + "/checkAccount.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        sharedPreferences = getActivity().getSharedPreferences("DataAccount", Context.MODE_PRIVATE);
        initialize(view);
        getDataSharedPreferences();

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Objects.requireNonNull(user.getText()).toString().trim();
                String password = Objects.requireNonNull(pass.getText()).toString().trim();
                checkValue(name, password);
                checkAccount(name, password);
            }
        });
        return view;
    }

    public void checkAccount(String name, String password){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            setDataSharedPreferences(name, password);
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                            Toast.makeText(getActivity(), response.trim(), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            pass.setText("");
                            Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
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
                        return params;
                    }
                    };
        requestQueue.add(stringRequest);
    }

    private void getDataSharedPreferences(){
        user.setText(sharedPreferences.getString("username", ""));
        pass.setText(sharedPreferences.getString("password", ""));
        rememberUser.setChecked(sharedPreferences.getBoolean("remember", false));
    }

    private void setDataSharedPreferences(String name, String password) {
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
        user = view.findViewById(R.id.inputUsername);
        pass = view.findViewById(R.id.inputPassword);
        rememberUser = view.findViewById(R.id.cbRememberPass);
        btLogin = view.findViewById(R.id.btLogin);
    }

    public void checkValue(String name,String password){
        if(name.isEmpty()){
            user.setError("Please enter your username");
        }
        if(password.isEmpty()){
            pass.setError("Please enter your password");
        }
    }


}
