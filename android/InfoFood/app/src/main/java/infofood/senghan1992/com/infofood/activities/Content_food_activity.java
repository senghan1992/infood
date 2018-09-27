package infofood.senghan1992.com.infofood.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import infofood.senghan1992.com.infofood.R;
import infofood.senghan1992.com.infofood.utils.NetRetrofit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Content_food_activity extends AppCompatActivity {

    ImageView camera_icon, gallery_icon, upload_img;
    Button upload_btn;
    EditText upload_subway, upload_content, upload_food;

    //사진 전송에 필요한 것들
    private Uri photoURI;
    private String imageFilePath;

    String subway, content, food, user_idx, user_nikname, user_id;

    //사진으로 전송시 되돌려 받을 번호
    static int REQUEST_PICTURE = 1;

    //앨범으로 전송시 돌려받을 번호
    static int REQUEST_PHOTO_ALBUM = 2;

    //첫번째 이미지 아이콘 샘플 이다.
    static String SAMPLEIMG = "ic_launcher.png";

    //SharedPreferences 에서 사용할 아이디와 닉네임 가져오기
    SharedPreferences pref;

    //사진 업로드시 보여줄 다이얼로드
    ProgressDialog asyncDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_food);

        PermissionListener pemissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(Content_food_activity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(Content_food_activity.this, "Permission Denied\n" +
                        deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(pemissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

        camera_icon = findViewById(R.id.camera_icon);
        gallery_icon = findViewById(R.id.gallery_icon);
        upload_img = findViewById(R.id.upload_img);
        upload_btn = findViewById(R.id.upload_btn);
        upload_subway = findViewById(R.id.upload_subway);
        upload_content = findViewById(R.id.upload_content);
        upload_food = findViewById(R.id.upload_food);

        camera_icon.setOnClickListener(click);
        gallery_icon.setOnClickListener(click);
        upload_btn.setOnClickListener(click);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        user_id = pref.getString("user_id","user_id");
        user_idx = pref.getString("user_idx","user_idx");
        user_nikname = pref.getString("user_nikname","user_nikname");

    }//onCreate()

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.camera_icon:
                    doTakePhotoAction();
                    break;
                case R.id.gallery_icon:
                    doTakeAlbumAction();
                    break;
                case R.id.upload_btn:
                    getUploadInfo();
                    break;
            }
        }
    };

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

    private void doTakePhotoAction() {
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
                startActivityForResult(intent, REQUEST_PICTURE);
            }
        }
    }//doTakePhotoAction()

    private void doTakeAlbumAction() {
        //저장된 사진을 불러오는 함수이다. 즉앨범에있는것인데 인텐트는 ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        //갤러리의 기본설정 해주도록하자! 아래는 이미지와 그 경로를 표준타입으로 설정한다.
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        //사진이 저장된위치(sdcard)에 데이터가 잇다고 지정
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_PHOTO_ALBUM);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICTURE) {
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

                upload_img.setImageBitmap(rotate(resized, exifDegree));
            }

            if (requestCode == REQUEST_PHOTO_ALBUM) {
                //앨범에서 호출한경우 data는 이전인텐트(사진갤러리)에서 선택한 영역을 가져오게된다.
                Bitmap resized = null;
                ExifInterface exif = null;
                int exifOrientation = 0;
                int exifDegree = 0;
                if (data != null) {
                    photoURI = data.getData();
                    try {
                        Cursor c = getContentResolver().query(Uri.parse(photoURI.toString()), null, null, null, null);
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
                    upload_img.setImageBitmap(rotate(resized, exifDegree));
                }
            }
        }

    }//onActivityResult()


    //업로드 버튼 클릭시 정보들을 가져온다
    private void getUploadInfo() {
        subway = upload_subway.getText().toString();
        content = upload_content.getText().toString();
        food = upload_food.getText().toString();

        if (subway.length() <= 0) {
            Toast.makeText(getApplicationContext(), "지하철역을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else if (content.length() <= 0) {
            Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else if (imageFilePath == null) {
            Toast.makeText(getApplicationContext(), "사진을 골라주세요", Toast.LENGTH_SHORT).show();
        } else if (food == null) {
            Toast.makeText(getApplicationContext(), "음식 이름을 입력해주세요", Toast.LENGTH_SHORT).show();
        } else {
            new DoFileUpload().execute(imageFilePath);
        }
    }

    //retrofit2로 업로드 구현
    private String upload_food(String imageFilePath){
        File file = new File(imageFilePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(photoURI)),file);
        MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("file",file.getName(),requestFile);

        Call<ResponseBody> res = NetRetrofit.getInstance()
                                .getService()
                                .upload_food(subway,content,food,user_idx+"",user_nikname, multipartBody);

        res.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                HomeActivity homeActivity = (HomeActivity)HomeActivity.HomeActivity;
                Intent intent = new Intent(Content_food_activity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                homeActivity.finish();

                Log.d("파일업로드시 돌아오는 값",response.body().toString());

                asyncDialog.dismiss();
                Toast.makeText(getApplicationContext(),"업로드 성공"
                        ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"업로드하는 도중 오류가 발생하였습니다"
                        ,Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        return "";
    }

    class DoFileUpload extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            asyncDialog = new ProgressDialog(Content_food_activity.this);
            asyncDialog.setMessage("loading");
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            //String result = HttpFileUpload(serverip, "", strings[0]);
            upload_food(strings[0]);
            return "";
        }

    }

    //retrofit2를 사용하면 필요없는 것들이다
    ///////////////////////////////////////////////////////////////////////
    /*private String HttpFileUpload(String urlString, String params, String fileName) {
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String receiveMsg = "";
        try {

            File sourceFile = new File(fileName);
            DataOutputStream dos;
            if (!sourceFile.isFile()) {
                Log.e("uploadFile", "Source File not exist :" + fileName);
            } else {

                FileInputStream mFileInputStream = new FileInputStream(sourceFile);
                URL connectUrl = new URL(urlString);

                // open connection
                HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                // write data
                dos = new DataOutputStream(conn.getOutputStream());

                // write data
                dos.writeBytes("\r\n--" + boundary + "\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"subway\"" + "\r\n\r\n" + subway);
                dos.writeBytes("\r\n--" + boundary + "\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"food\"" + "\r\n\r\n" + food);
                dos.writeBytes("\r\n--" + boundary + "\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"content\"" + "\r\n\r\n" + content);
                dos.writeBytes("\r\n--" + boundary + "\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"user_idx\"" + "\r\n\r\n" + user_idx);
                dos.writeBytes("\r\n--" + boundary + "\r\n");
                dos.writeBytes("Content-Disposition: form-data; name=\"user_nikname\"" + "\r\n\r\n" + user_nikname);
                dos.writeBytes("\r\n--" + boundary + "\r\n");


                //write upload file
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                int bytesAvailable = mFileInputStream.available();
                int maxBufferSize = 1024 * 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);

                byte[] buffer = new byte[bufferSize];
                int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

                // read image
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = mFileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                dos.flush(); // finish upload...

                if (conn.getResponseCode() == 200) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                    receiveMsg = stringBuffer.toString();
                    JSONArray jarray = new JSONObject(receiveMsg).getJSONArray("res");
                    JSONObject jObject = jarray.getJSONObject(0);
                    String result = jObject.optString("result");
                    if (result.equals("success")) {
                        receiveMsg = "yes";
                    } else {
                        receiveMsg = "no";
                    }
                }
                mFileInputStream.close();
                dos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return receiveMsg;
    }*/

}
