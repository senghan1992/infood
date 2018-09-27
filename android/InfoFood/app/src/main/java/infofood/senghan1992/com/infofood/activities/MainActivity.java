package infofood.senghan1992.com.infofood.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import infofood.senghan1992.com.infofood.R;
import infofood.senghan1992.com.infofood.utils.NetRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView btn_join;
    Button btn_login;
    EditText edit_email, edit_pwd;
    CheckBox check_login;

    String email, pwd, nikname, user_idx;
    String loginResult = "";
    String loginMsg = "";

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
                        //String result = "email="+email+"&pwd="+pwd;
                        new Task().execute(email, pwd);
                    }
                    break;
            }
        }
    };//OnClickListener

    //retrofit을 사용하여 서버에 넘길 것들
    private void login(final String email, String pwd) {

        if (!email.isEmpty() && !pwd.isEmpty()) {
            Call<JsonObject> res = NetRetrofit.getInstance()
                    .getService().login(email, pwd);
            res.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonArray array = response.body().getAsJsonArray("res");
                    JsonObject object = (JsonObject) array.get(0);
                    loginResult = object.get("result").toString();

                    if (loginResult.contains("none")){
                        loginMsg = "등록되지 않은 사용자입니다";
                    }else if (loginResult.contains("fail")){
                        loginMsg = "아이디 혹은 비밀번호를 확인하세요";
                    }else{
                        loginMsg = "최근 접속일자 : "+object.get("last_login").toString();
                        nikname = object.get("nikname").toString();
                        user_idx = object.get("user_idx").toString();
                    }

                    Toast.makeText(getApplicationContext(),loginMsg,Toast.LENGTH_SHORT).show();

                    if (loginMsg.contains("최근")) {

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

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }
    }

    class Task extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... strings) {
            login(strings[0],strings[1]);
            return null;
        }
    }
}
