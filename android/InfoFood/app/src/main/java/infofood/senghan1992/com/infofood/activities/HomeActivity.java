package infofood.senghan1992.com.infofood.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import infofood.senghan1992.com.infofood.R;
import infofood.senghan1992.com.infofood.adapters.HomeActivityAdapter;

public class HomeActivity extends FragmentActivity {

    int MAX_PAGE = 4;
    Fragment cur_fragment = new Fragment();

    ViewPager pager;
    ImageButton btn_home, btn_recipe, btn_write, btn_search, btn_settings,menu_content_tip,menu_content_food;
    LinearLayout view_home, view_recipe, view_search, view_settings;

    //Animation
    Animation menu_in, menu_out;

    //menu
    LinearLayout menu_layout;

    //다른 곳에서 종료 시키기 위한 변수
    public static Activity HomeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        HomeActivity = infofood.senghan1992.com.infofood.activities.HomeActivity.this;

        //검색
        btn_home = findViewById(R.id.btn_home);
        btn_home.setTag(0);
        btn_recipe = findViewById(R.id.btn_recipe);
        btn_recipe.setTag(1);
        btn_search = findViewById(R.id.btn_search);
        btn_search.setTag(2);
        btn_settings = findViewById(R.id.btn_settings);
        btn_settings.setTag(3);

        view_home = findViewById(R.id.view_home);
        view_home.setTag(0);
        view_recipe = findViewById(R.id.view_recipe);
        view_recipe.setTag(1);
        view_search = findViewById(R.id.view_search);
        view_search.setTag(2);
        view_settings = findViewById(R.id.view_settings);
        view_settings.setTag(3);

        //메뉴
        menu_layout = findViewById(R.id.menu_layout);
        menu_content_tip = findViewById(R.id.menu_content_tip);
        menu_content_food = findViewById(R.id.menu_content_food);

        menu_content_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,Content_tip_activity.class);
                startActivity(intent);
            }
        });

        menu_content_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,Content_food_activity.class);
                startActivity(intent);
            }
        });

        btn_write = findViewById(R.id.btn_write);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu_in = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade_in);
                menu_out = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade_out);
                if (menu_layout.getVisibility() == View.INVISIBLE) {
                    menu_layout.startAnimation(menu_in);
                    menu_layout.setVisibility(View.VISIBLE);
                    btn_write.setBackgroundResource(R.mipmap.home_x);
                } else {
                    menu_layout.startAnimation(menu_out);
                    menu_layout.setVisibility(View.INVISIBLE);
                    btn_write.setBackgroundResource(R.mipmap.home_write);
                }
            }
        });

        btn_home.setOnClickListener(click);
        btn_recipe.setOnClickListener(click);
        btn_search.setOnClickListener(click);
        btn_settings.setOnClickListener(click);
        view_home.setOnClickListener(click);
        view_recipe.setOnClickListener(click);
        view_search.setOnClickListener(click);
        view_settings.setOnClickListener(click);

        pager = findViewById(R.id.pager);
        pager.setAdapter(new HomeActivityAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(0);

    }//onCreate()

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pager.setCurrentItem((int) view.getTag());
            switch ((int)view.getTag())
            {
                case 0:
                    //태그번호에 따라서, 버튼의 모양을 바꿔줄 수 있다
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }

    };//OnClickListener

}
