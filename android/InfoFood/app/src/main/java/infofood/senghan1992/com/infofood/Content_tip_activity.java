package infofood.senghan1992.com.infofood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Content_tip_activity extends AppCompatActivity {

    LinearLayout tip_layout2, tip_layout3, tip_layout4, tip_layout5;
    EditText tip_title, tip_content1, tip_content2, tip_content3, tip_content4, tip_content5;
    ImageView tip_img1, tip_img2, tip_img3, tip_img4, tip_img5;
    ImageView tip_nextbtn1, tip_nextbtn2, tip_nextbtn3, tip_nextbtn4;

    private static final int CAMERA_REQUEST_CODE  = 1;
    private static final int REQ_CODE_SELECT_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_tip);

        /*tip_title = findViewById(R.id.tip_title);

        tip_nextbtn1 = findViewById(R.id.tip_nextbtn1);
        tip_nextbtn2 = findViewById(R.id.tip_nextbtn2);
        tip_nextbtn3 = findViewById(R.id.tip_nextbtn3);
        tip_nextbtn4= findViewById(R.id.tip_nextbtn4);

        tip_img_layout1 = findViewById(R.id.tip_img_layout1);
        tip_img_layout2 = findViewById(R.id.tip_img_layout2);
        tip_img_layout3 = findViewById(R.id.tip_img_layout3);
        tip_img_layout4 = findViewById(R.id.tip_img_layout4);

        tip_txt_layout1 = findViewById(R.id.tip_txt_layout1);
        tip_txt_layout2 = findViewById(R.id.tip_txt_layout2);
        tip_txt_layout3 = findViewById(R.id.tip_txt_layout3);
        tip_txt_layout4 =findViewById(R.id.tip_txt_layout4);

        tip_img1 = findViewById(R.id.tip_img1);
        tip_img2 = findViewById(R.id.tip_img2);
        tip_img3 = findViewById(R.id.tip_img3);
        tip_img4 = findViewById(R.id.tip_img4);
        tip_img5 = findViewById(R.id.tip_img5);

        tip_img1.setOnClickListener(imgClick);
        tip_img2.setOnClickListener(imgClick);
        tip_img3.setOnClickListener(imgClick);
        tip_img4.setOnClickListener(imgClick);
        tip_img5.setOnClickListener(imgClick);

        tip_nextbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tip_img_layout1.setVisibility(View.VISIBLE);
                tip_txt_layout1.setVisibility(View.VISIBLE);
                tip_nextbtn2.setVisibility(View.VISIBLE);
                tip_nextbtn1.setVisibility(View.GONE);
            }
        });

        tip_nextbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tip_img_layout2.setVisibility(View.VISIBLE);
                tip_txt_layout2.setVisibility(View.VISIBLE);
                tip_nextbtn3.setVisibility(View.VISIBLE);
                tip_nextbtn2.setVisibility(View.GONE);
            }
        });

        tip_nextbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tip_img_layout3.setVisibility(View.VISIBLE);
                tip_txt_layout3.setVisibility(View.VISIBLE);
                tip_nextbtn4.setVisibility(View.VISIBLE);
                tip_nextbtn3.setVisibility(View.GONE);
            }
        });

        tip_nextbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tip_img_layout4.setVisibility(View.VISIBLE);
                tip_txt_layout4.setVisibility(View.VISIBLE);
                tip_nextbtn4.setVisibility(View.GONE);
            }
        });*/
    }//onCreate()

    /*View.OnClickListener imgClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final AlertDialog.Builder dialog1 = new AlertDialog.Builder(Content_tip_activity.this);
            dialog1.setTitle("사진 업로드").setPositiveButton("사진촬영", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    takePhoto();

                }
            }).setNegativeButton("앨범선택", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    selectAlbum();
                }
            }).setNeutralButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AlertDialog dialog = dialog1.create();
                    dialog.dismiss();
                }
            });
            dialog1.show();
        }
    };//imgClick

    public void takePhoto(){
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);

    }//takePhoto()

    private void selectAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }//selectAlbum()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {

                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    //배치해놓은 ImageView에 이미지를 넣는다.
                    tip_img1.setImageBitmap(bitmap);

                } catch (Exception e) {
                    Log.e("test", e.getMessage());
                }
            }
        }else if (requestCode == CAMERA_REQUEST_CODE){
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap)bundle.get("data");
            tip_img1.setImageBitmap(bitmap);
        }
    }*/
}
