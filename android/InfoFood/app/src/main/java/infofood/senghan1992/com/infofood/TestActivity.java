package infofood.senghan1992.com.infofood;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import infofood.senghan1992.com.infofood.utils.NetRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    EditText test_edit, test_edit2;
    Button test_btn;
    TextView test_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        test_edit = findViewById(R.id.test_edit);
        test_edit2 = findViewById(R.id.test_edit2);
        test_btn = findViewById(R.id.test_btn);
        test_txt = findViewById(R.id.test_txt);

        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new testTask().execute();
            }
        });

    }//onCreate()

    public void onSearch() {
        String id = test_edit.getText().toString();
        String pwd = test_edit2.getText().toString();

        if (!id.isEmpty()) {
            Call<JsonObject> res = NetRetrofit.getInstance()
                    .getService().login(id,pwd);
            res.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.d("Retrofit", response.toString());
                    test_txt.setText(response.body().toString());
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e("Err", t.getMessage());
                }
            });
        } else
            Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
    }//onSearch()

    class testTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            onSearch();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
