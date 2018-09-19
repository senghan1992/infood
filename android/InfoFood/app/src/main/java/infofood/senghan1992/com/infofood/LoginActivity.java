package infofood.senghan1992.com.infofood;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import infofood.senghan1992.com.infofood.ServerInfo.ServerInfo;

public class LoginActivity extends AppCompatActivity {

    EditText edit_email,edit_pwd;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //검색
        edit_email = findViewById(R.id.edit_email);
        edit_pwd = findViewById(R.id.edit_pwd);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edit_email.getText().toString();
                String pwd = edit_pwd.getText().toString();

                String result = "email="+email+"&pwd="+pwd;

                new Task().execute(result);
            }
        });//OnClickListener

    }//OnCreate()

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
                        receiveMsg = "로그인 성공";
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
        }
    }
}
