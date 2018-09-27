package infofood.senghan1992.com.infofood.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import infofood.senghan1992.com.infofood.R;
import infofood.senghan1992.com.infofood.utils.NetRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edit_email,edit_pwd;
    Button btn_login;
    String email, pwd;

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
                email = edit_email.getText().toString();
                pwd = edit_pwd.getText().toString();

                new Task().execute(email, pwd);
            }
        });//OnClickListener

    }//OnCreate()

    //retrofit을 사용하여 서버에 넘길 것들
    private void login(String email, String pwd){
        if (!email.isEmpty() && !pwd.isEmpty())
        {
            Call<JsonObject> res = NetRetrofit.getInstance()
                    .getService().login(email, pwd);
            res.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_SHORT).show();
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
            login(strings[0], strings[1]);
            return null;
        }
    }
}
