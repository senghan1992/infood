package infofood.senghan1992.com.infofood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import infofood.senghan1992.com.infofood.ServerInfo.ServerInfo;

public class MainActivity extends AppCompatActivity {

    TextView btn_join;
    Button btn_login;
    EditText edit_email, edit_pwd;
    CheckBox check_login;

    String email, pwd, nikname, user_idx;

    //로그인 정보 저장하기 위한 SharedPreferences
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //검색
        btn_join = findViewById(R.id.btn_join);
        btn_login = findViewById(R.id.btn_login);
        edit_email = findViewById(R.id.edit_email);
        edit_pwd = findViewById(R.id.edit_pwd);
        check_login = findViewById(R.id.check_login);

        btn_join.setOnClickListener(click);
        btn_login.setOnClickListener(click);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        if(pref.getString("access","no").equals("ok"))
        {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }//onCreate()

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_join:
                    Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                    startActivity(intent);
                    //finish();
                    break;
                case R.id.btn_login:
                    email = edit_email.getText().toString().trim();
                    pwd = edit_pwd.getText().toString().trim();

                    if(email.isEmpty()){
                        Toast.makeText(getApplicationContext(),"아이디를 입력하세요",Toast.LENGTH_SHORT).show();
                    }else if(pwd.isEmpty()){
                        Toast.makeText(getApplicationContext(),"비밀번호를 입력하세요",Toast.LENGTH_SHORT).show();
                    }else{
                        String result = "email="+email+"&pwd="+pwd;
                        new Task().execute(result);
                    }
                    break;
            }
        }
    };//OnClickListener


    class Task extends AsyncTask<String,Void,String> {

        String sendMsg, receiveMsg;
        String serverip = ServerInfo.SERVER_IP + "login";

        @Override
        protected String doInBackground(String... strings) {
            try{
                String str = "";
                URL url = new URL(serverip);

                //서버연결
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
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
                if(conn.getResponseCode() == conn.HTTP_OK){
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "utf-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();

                    while ((str = reader.readLine()) != null){
                        buffer.append(str);
                    }

                    //서버에서 넘겨준 JSON 형식의 결과값
                    receiveMsg = buffer.toString();
                    JSONArray jarray = new JSONObject(receiveMsg).getJSONArray("res");
                    JSONObject jObject = jarray.getJSONObject(0);
                    String result = jObject.optString("result");

                    if(result.equals("success")){
                        String last_login = jObject.optString("last_login");
                        nikname = jObject.optString("nikname");
                        user_idx = jObject.optString("user_idx");
                        receiveMsg = "최근접속일자 : "+last_login;
                    }else if(result.equals("none")){
                        receiveMsg = "등록되지 않은 사용자입니다";
                    }else{
                        receiveMsg = "아이디 혹은 비밀번호 오류입니다";
                    }
                    Log.i("돌아온 값", receiveMsg);
                }

            }catch (Exception e){

            }
            return receiveMsg;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            if (s.contains("최근")){

                if (check_login.isChecked()){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user_id", email);
                    editor.putString("user_nikname", nikname);
                    editor.putString("user_idx",user_idx);
                    editor.putString("access","ok");
                    editor.commit();
                }else{
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user_id", email);
                    editor.putString("user_nikname", nikname);
                    editor.putString("user_idx",user_idx);
                    editor.commit();
                }

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
