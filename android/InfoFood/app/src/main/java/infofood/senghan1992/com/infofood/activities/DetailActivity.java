package infofood.senghan1992.com.infofood.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import infofood.senghan1992.com.infofood.R;
import infofood.senghan1992.com.infofood.ServerInfo.ServerInfo;

public class DetailActivity extends AppCompatActivity {

    TextView detail_nikname, detail_regidate, detail_subway, detail_content;
    ImageView detail_image;
    String nikname, regidate, subway, image, content;
    int user_idx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        nikname = bundle.getString("user_nikname");
        regidate = bundle.getString("regidate");
        subway = bundle.getString("subway");
        image = bundle.getString("image");
        content = bundle.getString("content");
        user_idx = bundle.getInt("user_idx");

        //검색
        detail_nikname = findViewById(R.id.detail_nikname);
        detail_regidate = findViewById(R.id.detail_regidate);
        detail_subway = findViewById(R.id.detail_subway);
        detail_image = findViewById(R.id.detail_image);
        detail_content = findViewById(R.id.detail_content);

        detail_nikname.setText(nikname);
        detail_regidate.setText(regidate.substring(0,10));
        detail_subway.setText(subway);
        detail_content.setText(content);
        Glide.with(this).load(ServerInfo.SERVER_IP_PHOTO+image).into(detail_image);

    }
}
