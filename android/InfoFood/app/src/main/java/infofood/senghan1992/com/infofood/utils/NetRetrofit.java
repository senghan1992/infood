package infofood.senghan1992.com.infofood.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import infofood.senghan1992.com.infofood.ServerInfo.ServerInfo;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetRetrofit {
    private static NetRetrofit ourInstance = new NetRetrofit();
    public static NetRetrofit getInstance(){
        return ourInstance;
    }
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ServerInfo.SERVER_IP)
            .addConverterFactory(GsonConverterFactory.create(gson)) //파싱 등록
            .build();

    RetrofitService service = retrofit.create(RetrofitService.class);

    public RetrofitService getService() {
        return service;
    }
}
