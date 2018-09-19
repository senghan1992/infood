package infofood.senghan1992.com.infofood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import infofood.senghan1992.com.infofood.R;
import infofood.senghan1992.com.infofood.vo.StationVO;


public class SearchFragmentAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<StationVO> list;

    public SearchFragmentAdapter(Context mContext, ArrayList<StationVO> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.search_list_item, null);

            TextView search_list_item = view.findViewById(R.id.search_list_item);
            search_list_item.setText(list.get(i).getStation_name());
        }

        return view;
    }
}
