package org.androidtown.termi02;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUp extends AppCompatActivity {

    private EditText signUp_idEditText;
    private EditText signUp_pwdEditText;
    private Button signedUp_btn;
    private String myPhoneNumber;
    private String profile;
    private String responseCode;
    private String fcmToken;
    private String msg;
    private final String url = "http://52.78.240.168";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        fcmToken = FirebaseInstanceId.getInstance().getToken();
        msg = getString(R.string.msg_token_fmt,fcmToken);
        signedUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostSignUp postSignUp = new PostSignUp();


                try {
                    postSignUp.doPostRequest(url+"/api/signup",phoneNumber());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void init(){
        signUp_idEditText = (EditText)findViewById(R.id.signUp_idEditText);
        signUp_pwdEditText = (EditText)findViewById(R.id.signUp_pwdEditText);
        signedUp_btn = (Button)findViewById(R.id.signedUp_btn);
        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        myPhoneNumber = tMgr.getLine1Number();
    }

    void responseCode(){
        switch (responseCode){
            case "2":
                Toast.makeText(getApplicationContext(),"회원가입성공",Toast.LENGTH_LONG).show();
                finish();
                break;
            case "3":
                Toast.makeText(getApplicationContext(),"회원가입실패",Toast.LENGTH_LONG).show();
                break;
            case "4":
                Toast.makeText(getApplicationContext(),"아이디 이미 존재",Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
        }
    }

    private String phoneNumber(){
        JSONObject profileObj = new JSONObject();
        try {
            profileObj.put("id",signUp_idEditText.getText().toString());
            profileObj.put("password",signUp_pwdEditText.getText().toString());
            profileObj.put("telephone",myPhoneNumber);
            profileObj.put("fcmToken",fcmToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        profile = profileObj.toString();

        return profile;
    }

    private class PostSignUp{
        OkHttpClient client = new OkHttpClient();
        private String doPostRequest(String url, String json) throws IOException {
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .header("Content-Type", "application/json")
                    .url(url)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(callbackAfterSignUp);
            return null;
        }
    }


    private Callback callbackAfterSignUp = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String strJsonOutput = response.body().string();

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
            response.body().close();
        }
    };
}
