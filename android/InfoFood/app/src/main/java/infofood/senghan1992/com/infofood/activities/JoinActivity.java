package infofood.senghan1992.com.infofood.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import infofood.senghan1992.com.infofood.R;
import infofood.senghan1992.com.infofood.ServerInfo.ServerInfo;

public class JoinActivity extends AppCompatActivity {

    EditText edit_email, edit_pwd, edit_pwd_check, edit_nikname;
    Button btn_join;
    ImageView check_image;

    String email, pwd, nikname;

    //중복체크를 했는지 판단할 boolean 변수
    boolean is_checked_email;
    boolean is_checked_nikname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        is_checked_email = false;
        is_checked_nikname = false;

        edit_email = findViewById(R.id.edit_email);
        edit_pwd = findViewById(R.id.edit_pwd);
        edit_pwd_check = findViewById(R.id.edit_pwd_check);
        edit_nikname = findViewById(R.id.edit_nikname);
        btn_join = findViewById(R.id.btn_join);

        edit_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String email = edit_email.getText().toString();
                String result = "email="+email;
                new Task2().execute(result);
            }
        });

        edit_nikname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String nikname = edit_nikname.getText().toString();
                String result = "nikname="+nikname;
                new Task1().execute(result);
            }
        });

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_checked_email && is_checked_nikname){
                    email = edit_email.getText().toString();
                    pwd = edit_pwd.getText().toString();
                    nikname = edit_nikname.getText().toString();

                    if(email.isEmpty()){
                        Toast.makeText(getApplicationContext(),"이메일을 입력해주세요",Toast.LENGTH_SHORT).show();
                    }else if(pwd.isEmpty()){
                        Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                    }else if(nikname.isEmpty()){
                        Toast.makeText(getApplicationContext(),"닉네임을 입력해주세요",Toast.LENGTH_SHORT).show();
                    }else{
                        String result = "email=" + email + "&pwd=" + pwd + "&nikname=" + nikname;
                        new Task3().execute(result);
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"중복체크를 해주세요!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }//onCreate

    class Task1 extends AsyncTask<String, Void, String> {

        String sendMsg, receiveMsg;
        String serverip = ServerInfo.SERVER_IP + "nikname_check";

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str = "";
                URL url = new URL(serverip);

                //서버연결
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                //서버로 전달할 내용
                sendMsg = strings[0];

                //서버로 값 전송
                osw.write(sendMsg);
                osw.flush();

                //서버로 값 전송이 완료되면 서버에서 처리한 결과를 받는다
                //getResponseCode() : 200 -> 정상
                //getResponseCode() : 404,500 -> 비정상
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "utf-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();

                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }

                    //서버에서 넘겨준 JSON 형식의 결과값
                    receiveMsg = buffer.toString();

                    JSONArray jarray = new JSONObject(receiveMsg).getJSONArray("res");
                    JSONObject jObject = jarray.getJSONObject(0);
                    String result = jObject.optString("result");

                    if (result.equals("can")) {
                        receiveMsg = "yes";
                    } else {
                        receiveMsg = "no";
                    }
                    Log.i("돌아온 값", receiveMsg);
                }

            } catch (Exception e) {

            }
            return receiveMsg;
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            if (s.equals("yes")){
                is_checked_nikname = true;
                btn_join.setEnabled(true);
                edit_nikname.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.join_slash,0,
                        R.mipmap.after_check,0);
            }else{
                is_checked_nikname = false;
                edit_nikname.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.join_slash,0,
                        R.mipmap.after_check2,0);

            }
        }
    }

    class Task2 extends AsyncTask<String, Void, String> {

        String sendMsg, receiveMsg;
        String serverip = ServerInfo.SERVER_IP + "email_check";

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str = "";
                URL url = new URL(serverip);

                //서버연결
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                //서버로 전달할 내용
                sendMsg = strings[0];

                //서버로 값 전송
                osw.write(sendMsg);
                osw.flush();

                //서버로 값 전송이 완료되면 서버에서 처리한 결과를 받는다
                //getResponseCode() : 200 -> 정상
                //getResponseCode() : 404,500 -> 비정상
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "utf-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();

                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }

                    //서버에서 넘겨준 JSON 형식의 결과값
                    receiveMsg = buffer.toString();
                    JSONArray jarray = new JSONObject(receiveMsg).getJSONArray("res");
                    JSONObject jObject = jarray.getJSONObject(0);
                    String result = jObject.optString("result");

                    if (result.equals("can")) {
                        receiveMsg = "yes";
                    } else {
                        receiveMsg = "no";
                    }
                    Log.i("돌아온 값", receiveMsg);
                }

            } catch (Exception e) {

            }
            return receiveMsg;
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            if (s.equals("yes")){
                is_checked_email = true;
                btn_join.setEnabled(true);
                edit_email.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.login_email,0,
                        R.mipmap.after_check,0);
            }else{
                is_checked_email = false;
                edit_email.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.login_email,0,
                        R.mipmap.after_check2,0);

            }
        }
    }

    class Task3 extends AsyncTask<String, Void, String> {

        String sendMsg, receiveMsg;
        String serverip = ServerInfo.SERVER_IP + "join";

        @Override
        protected String doInBackground(String... strings) {
            try {
                String str = "";
                URL url = new URL(serverip);

                //서버연결
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");

                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                //서버로 전달할 내용
                sendMsg = strings[0];

                //서버로 값 전송
                osw.write(sendMsg);
                osw.flush();

                //서버로 값 전송이 완료되면 서버에서 처리한 결과를 받는다
                //getResponseCode() : 200 -> 정상
                //getResponseCode() : 404,500 -> 비정상
                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "utf-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();

                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }

                    //서버에서 넘겨준 JSON 형식의 결과값
                    receiveMsg = buffer.toString();
                    JSONArray jarray = new JSONObject(receiveMsg).getJSONArray("res");
                    JSONObject jObject = jarray.getJSONObject(0);
                    String result = jObject.optString("result");

                    if (result.equals("success")) {
                        receiveMsg = "가입 성공";
                    } else {
                        receiveMsg = "이미 사용중인 아이디 입니다";
                    }
                    Log.i("돌아온 값", receiveMsg);
                }

            } catch (Exception e) {

            }
            return receiveMsg;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(JoinActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
