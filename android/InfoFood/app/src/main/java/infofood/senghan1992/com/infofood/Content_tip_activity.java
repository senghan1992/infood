package infofood.senghan1992.com.infofood;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Content_tip_activity extends AppCompatActivity {

    LinearLayout[] layouts;
    ImageView[] tip_nextbtns;
    ImageView[] tip_cancelbtns;
    //ImageView[] tip_images;
    ImageView tip_img1, tip_img2, tip_img3, tip_img4, tip_img5, tip_test;
    EditText[] contents;

    int image_num;

    AlertDialog.Builder dialog;

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;

    //***클릭할때마다 변화를 주는 제일 중요한 변수****
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_tip);

        count = 0;

        //검색
        //content 하나당 레이아웃
        layouts = new LinearLayout[5];
        layouts[0] = findViewById(R.id.tip_layout1);
        layouts[1] = findViewById(R.id.tip_layout2);
        layouts[2] = findViewById(R.id.tip_layout3);
        layouts[3] = findViewById(R.id.tip_layout4);
        layouts[4] = findViewById(R.id.tip_layout5);

        //content 하나당 다음 content 보이게 하는 버튼
        tip_nextbtns = new ImageView[4];
        tip_nextbtns[0] = findViewById(R.id.tip_nextbtn1);
        tip_nextbtns[1] = findViewById(R.id.tip_nextbtn2);
        tip_nextbtns[2] = findViewById(R.id.tip_nextbtn3);
        tip_nextbtns[3] = findViewById(R.id.tip_nextbtn4);
        for (ImageView tmp : tip_nextbtns) {
            tmp.setOnClickListener(clickNext);
        }

        //content 감추기 버튼들
        tip_cancelbtns = new ImageView[4];
        tip_cancelbtns[0] = findViewById(R.id.tip_cancel1);
        tip_cancelbtns[1] = findViewById(R.id.tip_cancel2);
        tip_cancelbtns[2] = findViewById(R.id.tip_cancel3);
        tip_cancelbtns[3] = findViewById(R.id.tip_cancel4);
        for (ImageView tmp : tip_cancelbtns) {
            tmp.setOnClickListener(clickCancel);
        }

        //content별 업로드 할 이미지
        tip_img1 = findViewById(R.id.tip_img1);
        tip_img2 = findViewById(R.id.tip_img2);
        tip_img3 = findViewById(R.id.tip_img3);
        tip_img4 = findViewById(R.id.tip_img4);
        tip_img5 = findViewById(R.id.tip_img5);

        tip_img1.setOnClickListener(clickImage);
        tip_img2.setOnClickListener(clickImage);
        tip_img3.setOnClickListener(clickImage);
        tip_img4.setOnClickListener(clickImage);
        tip_img5.setOnClickListener(clickImage);

        //content별 업로드 할 이미지
        /*tip_images = new ImageView[5];
        tip_images[0] = findViewById(R.id.tip_img1);
        tip_images[1] = findViewById(R.id.tip_img2);
        tip_images[2] = findViewById(R.id.tip_img3);
        tip_images[3] = findViewById(R.id.tip_img4);
        tip_images[4] = findViewById(R.id.tip_img5);
        for(ImageView tmp : tip_images){
            tmp.setOnClickListener(clickImage);
        }*/

        //content 별 edittext
        contents = new EditText[5];
        contents[0] = findViewById(R.id.tip_content1);
        contents[1] = findViewById(R.id.tip_content2);
        contents[2] = findViewById(R.id.tip_content3);
        contents[3] = findViewById(R.id.tip_content4);
        contents[4] = findViewById(R.id.tip_content5);

    }//onCreate()

    //+버튼 클릭되는 경우
    View.OnClickListener clickNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tip_nextbtns[count].setVisibility(View.GONE);
            count += 1;
            layouts[count].setVisibility(View.VISIBLE);
        }
    };

    //-버튼 클릭되는 경우
    View.OnClickListener clickCancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            layouts[count].setVisibility(View.GONE);
            count -= 1;
            tip_nextbtns[count].setVisibility(View.VISIBLE);
        }
    };

    //이미지 클릭되는 경우
    View.OnClickListener clickImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tip_img1:
                    makeDialog();
                    tip_test = findViewById(R.id.tip_img1);
                    break;
                case R.id.tip_img2:
                    makeDialog();
                    tip_test = findViewById(R.id.tip_img2);
                    break;
                case R.id.tip_img3:
                    makeDialog();
                    tip_test = findViewById(R.id.tip_img3);
                    break;
                case R.id.tip_img4:
                    makeDialog();
                    tip_test = findViewById(R.id.tip_img4);
                    break;
                case R.id.tip_img5:
                    tip_test = findViewById(R.id.tip_img5);
                    makeDialog();
                    break;
            }
        }
    };

    //make dialog
    void makeDialog() {
        dialog = new AlertDialog.Builder(Content_tip_activity.this);
        dialog.setTitle("사진 업로드").setPositiveButton("사진촬영",
                new DialogInterface.OnClickListener() {
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
                AlertDialog dialog1 = dialog.create();
                dialog1.dismiss();
            }
        });
        dialog.show();
    }

    public void takePhoto() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }//takePhoto()

    private void selectAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }//selectAlbum()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST_CODE) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    //배치해놓은 ImageView에 이미지를 넣는다.
                    tip_test.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Log.e("test", e.getMessage());
                }
            } else if (requestCode == CAMERA_REQUEST_CODE) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                tip_test.setImageBitmap(bitmap);
            }
        }
    }

}
