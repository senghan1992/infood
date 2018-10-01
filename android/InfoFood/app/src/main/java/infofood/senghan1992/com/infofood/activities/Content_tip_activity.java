package infofood.senghan1992.com.infofood.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import infofood.senghan1992.com.infofood.R;
import infofood.senghan1992.com.infofood.vo.TipVO;

public class Content_tip_activity extends AppCompatActivity {

    LinearLayout[] layouts;
    ImageView[] tip_nextbtns;
    ImageView[] tip_cancelbtns;
    ImageView[] tip_images;
    Button content_tip_upload_btn;

    //ImageView[] tip_images;
    ImageView tip_img1, tip_img2, tip_img3, tip_img4, tip_img5, tip_test;
    EditText[] contents;

    AlertDialog.Builder dialog;

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int GALLERY_REQUEST_CODE = 2;

    //***클릭할때마다 변화를 주는 제일 중요한 변수****
    int count;

    //사진 다루는 변수
    String imageFilePath;
    Uri photoURI;
    ArrayList<TipVO> contentInfo;
    TipVO vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_tip);

        //사진들 및 팁에 대한 내용들 담을 리스트 초기화
        count = 0;
        contentInfo = new ArrayList<>();

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

        //content별 업로드 할 이미지들
        tip_images = new ImageView[5];
        tip_images[0] = findViewById(R.id.tip_img1);
        tip_images[1] = findViewById(R.id.tip_img2);
        tip_images[2] = findViewById(R.id.tip_img3);
        tip_images[3] = findViewById(R.id.tip_img4);
        tip_images[4] = findViewById(R.id.tip_img5);

        tip_images[0].setOnClickListener(clickImage);
        tip_images[1].setOnClickListener(clickImage);
        tip_images[2].setOnClickListener(clickImage);
        tip_images[3].setOnClickListener(clickImage);
        tip_images[4].setOnClickListener(clickImage);

        //content별 업로드 할 이미지
        /*tip_img1 = findViewById(R.id.tip_img1);
        tip_img2 = findViewById(R.id.tip_img2);
        tip_img3 = findViewById(R.id.tip_img3);
        tip_img4 = findViewById(R.id.tip_img4);
        tip_img5 = findViewById(R.id.tip_img5);

        tip_img1.setOnClickListener(clickImage);
        tip_img2.setOnClickListener(clickImage);
        tip_img3.setOnClickListener(clickImage);
        tip_img4.setOnClickListener(clickImage);
        tip_img5.setOnClickListener(clickImage);*/

        //content 별 edittext
        contents = new EditText[5];
        contents[0] = findViewById(R.id.tip_content1);
        contents[1] = findViewById(R.id.tip_content2);
        contents[2] = findViewById(R.id.tip_content3);
        contents[3] = findViewById(R.id.tip_content4);
        contents[4] = findViewById(R.id.tip_content5);

        content_tip_upload_btn = findViewById(R.id.content_tip_upload_btn);
        content_tip_upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isTrue = false;
                for (int i = 0; i < 5; i++) {
                    if (layouts[i].getVisibility() == View.VISIBLE) {
                        Bitmap bitmap = ((BitmapDrawable) tip_images[i].getDrawable()).getBitmap();
                        String content = contents[i].getText().toString().trim();
                        if (bitmap == null || content.isEmpty()) {
                            Toast.makeText(getApplicationContext(),
                                    "이미지 혹은 내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                            isTrue = false;
                            return;
                        } else {
                            isTrue = true;
                        }
                    }//if
                }//for
                if (isTrue) {
                    for (int i=0;i<5;i++){
                        if(layouts[i].getVisibility() == View.VISIBLE){
                            Bitmap bitmap = ((BitmapDrawable) tip_images[i].getDrawable()).getBitmap();
                            String content = contents[i].getText().toString().trim();
                            vo = new TipVO();
                            vo.setPhotoUri(getImageUri(Content_tip_activity.this,bitmap));
                            vo.setContent(content);
                            contentInfo.add(vo);
                        }
                    }//for



                }//if(isTrue)
            }
        });


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
            switch (v.getId()) {
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

    //가져온 사진을 정비해서 넣는다
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",         /* suffix */
                storageDir          /* directory */
        );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    /////////////////////////////////////////////////////////

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        }
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
            if (requestCode == CAMERA_REQUEST_CODE) {
                Bitmap resized = null;
                ExifInterface exif = null;
                int exifOrientation = 0;
                int exifDegree = 0;
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
                    exif = new ExifInterface(imageFilePath);
                    if (exif != null) {
                        exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        exifDegree = exifOrientationToDegrees(exifOrientation);
                    } else {
                        exifDegree = 0;
                    }

                    resized = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tip_test.setImageBitmap(rotate(resized, exifDegree));
            } else if (requestCode == GALLERY_REQUEST_CODE) {
                //앨범에서 호출한경우 data는 이전인텐트(사진갤러리)에서 선택한 영역을 가져오게된다.
                Bitmap resized = null;
                ExifInterface exif = null;
                int exifOrientation = 0;
                int exifDegree = 0;
                if (data != null) {
                    photoURI = data.getData();
                    try {
                        Cursor c = getContentResolver().query(photoURI, null, null, null, null);
                        c.moveToNext();
                        imageFilePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                        Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
                        exif = new ExifInterface(imageFilePath);
                        if (exif != null) {
                            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                            exifDegree = exifOrientationToDegrees(exifOrientation);
                        } else {
                            exifDegree = 0;
                        }

                        resized = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                    } catch (Exception e) {

                    }
                    tip_test.setImageBitmap(rotate(resized, exifDegree));
                }
            }
        }
    }//onActivityResult()

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }//getImageUri

}
