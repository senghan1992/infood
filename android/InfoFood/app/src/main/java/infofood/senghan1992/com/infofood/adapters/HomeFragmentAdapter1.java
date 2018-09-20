package infofood.senghan1992.com.infofood.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import infofood.senghan1992.com.infofood.activities.DetailActivity;
import infofood.senghan1992.com.infofood.R;
import infofood.senghan1992.com.infofood.ServerInfo.ServerInfo;
import infofood.senghan1992.com.infofood.vo.FoodVO;

public class HomeFragmentAdapter1 extends RecyclerView.Adapter<HomeFragmentAdapter1.MyViewHolder> {

    ArrayList<FoodVO> list;
    Context mContext;

    public HomeFragmentAdapter1(ArrayList<FoodVO> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView recycler_list_item1;
        TextView recycler_list_item2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recycler_list_item1 = itemView.findViewById(R.id.recycler_list_item1);
            recycler_list_item2 = itemView.findViewById(R.id.recycler_list_item2);
        }

    }

    @NonNull
    @Override
    public HomeFragmentAdapter1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recycler_list_item,
                parent, false);

        MyViewHolder vh = new MyViewHolder(itemView);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFragmentAdapter1.MyViewHolder holder, final int position) {

        Glide.with(mContext).load(ServerInfo.SERVER_IP_PHOTO + list.get(position).getImage()).into(holder.recycler_list_item1);
        holder.recycler_list_item2.setText(list.get(position).getFood());
        holder.recycler_list_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("image",list.get(position).getImage());
                bundle.putString("subway",list.get(position).getSubway());
                bundle.putString("user_nikname",list.get(position).getUser_nikname());
                bundle.putString("regidate",list.get(position).getRegidate());
                bundle.putInt("user_idx",list.get(position).getUser_idx());
                bundle.putString("food",list.get(position).getFood());
                bundle.putString("content",list.get(position).getContent());
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
