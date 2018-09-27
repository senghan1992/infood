package infofood.senghan1992.com.infofood.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import infofood.senghan1992.com.infofood.R;
import infofood.senghan1992.com.infofood.ServerInfo.ServerInfo;
import infofood.senghan1992.com.infofood.adapters.HomeFragmentAdapter1;
import infofood.senghan1992.com.infofood.vo.FoodVO;

public class HomeFragment extends Fragment {

    int cnt;

    ArrayList<FoodVO> list;

    RecyclerView recycler_view;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cnt = getArguments().getInt("cnt");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate
                (R.layout.fragment_home, container, false);

        list = new ArrayList<>();

        new Task().execute("");

        recycler_view = view.findViewById(R.id.recycler_view);
        recycler_view.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        recycler_view.setLayoutManager(mLayoutManager);

        return view;
    }

    public static HomeFragment create(int position) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cnt", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    class Task extends AsyncTask<String, Void, ArrayList<FoodVO>> {

        String sendMsg, receiveMsg;
        String serverip = ServerInfo.SERVER_IP + "home";

        @Override
        protected ArrayList<FoodVO> doInBackground(String... strings) {
            try {
                String str = "";
                URL url = new URL(serverip);

                //서버연결
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());

                //서버로 전달할 내용
                sendMsg = strings[0];

                //서버로 값 전송
                osw.write(sendMsg);
                osw.flush();

                if (conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "utf-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();

                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }

                    //서버에서 넘겨준 JSON 형식의 결과값
                    receiveMsg = buffer.toString();
                    JSONArray jarray = new JSONArray(receiveMsg);
                    for (int i=0;i<jarray.length();i++){
                        JSONObject object = jarray.getJSONObject(i);
                        FoodVO vo = new FoodVO();
                        vo.setImage(object.optString("image"));
                        vo.setSubway(object.optString("subway"));
                        vo.setUser_nikname(object.optString("user_nikname"));
                        vo.setRegidate(object.optString("regidate"));
                        vo.setUser_idx(Integer.parseInt(object.optString("user_idx")));
                        vo.setIdx(Integer.parseInt(object.optString("idx")));
                        vo.setFood(object.optString("food"));
                        vo.setContent(object.optString("content"));
                        list.add(vo);
                    }

                }

            } catch (Exception e) {

            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<FoodVO> list) {
            mAdapter = new HomeFragmentAdapter1(list, getContext());
            recycler_view.setAdapter(mAdapter);
        }
    }

}


