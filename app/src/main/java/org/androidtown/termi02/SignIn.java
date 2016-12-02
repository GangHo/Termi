package org.androidtown.termi02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.androidtown.termi02.R.id.idEditText;
import static org.androidtown.termi02.R.id.pwdEditText;
import static org.androidtown.termi02.SignUp.JSON;

public class SignIn extends AppCompatActivity {

    private final String url = "http://52.78.240.168";
    private Button mSignUpButton;
    private Button mLoginButton;
    private EditText mIdEditText;
    private EditText mPwdEditText;
    private String idPwd;
    private String responseCode;
    private CheckBox mCheckBox;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
        signUp();
        signIn();
    }

    void init(){
        mSignUpButton = (Button)findViewById(R.id.signUp);
        mLoginButton = (Button)findViewById(R.id.login_button);
        mIdEditText = (EditText)findViewById(idEditText);
        mPwdEditText = (EditText)findViewById(pwdEditText);
        mCheckBox = (CheckBox)findViewById(R.id.checkBox);
    }

    void signUp(){
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        SignUp.class);
                startActivity(intent);
            }
        });
    }

    private String idPwd(){
        String id = mIdEditText.getText().toString();
        String pwd = mPwdEditText.getText().toString();

        JSONObject idPwdObj = new JSONObject();

        try {
            idPwdObj.put("id",id);
            idPwdObj.put("password",pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        idPwd = idPwdObj.toString();
        return idPwd;
    }

    void signIn(){
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    UserToken.setPreferences(getApplicationContext(),"token",token);
                }
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostSignIn postSignIn = new PostSignIn();
                try {
                    postSignIn.doPostRequest(url+"/api/signin",idPwd());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void responseCode(){
        switch (responseCode){
            case "5":
                Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case "6":
                Toast.makeText(getApplicationContext(),"로그인 실패",Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getApplicationContext(),"errorCode: "+responseCode,Toast.LENGTH_LONG).show();
        }
    }

    public class PostSignIn {
        OkHttpClient client = new OkHttpClient();

        private String doPostRequest(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .header("Content-Type", "application/json")
                    .url(url)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(callbackAfterSignIn);
            return null;
        }
    }

    private Callback callbackAfterSignIn = new Callback() {

        @Override
        public void onFailure(Call call, IOException e) {

        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String strJsonOutput = response.body().string();
            token = response.headers().get("authorization");

            try {
                JSONObject jsonOutput = new JSONObject(strJsonOutput);
                responseCode = jsonOutput.getString("responseCode");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(),responseCode+"",Toast.LENGTH_LONG).show();
                        responseCode();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
