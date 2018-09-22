package infofood.senghan1992.com.infofood.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitService {
    @POST("login")
    Call<JsonObject> login(
            @Query("email")String email, @Query("pwd")String pwd);

    @POST("home")
    Call<JsonArray> home(@Part("photo") MultipartBody.Part photo, @Part("description")String test);
}
