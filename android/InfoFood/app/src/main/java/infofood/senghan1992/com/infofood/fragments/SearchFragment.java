package infofood.senghan1992.com.infofood.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import infofood.senghan1992.com.infofood.R;

public class SearchFragment extends Fragment {

    int cnt;

    Button search_btn;
    EditText search_edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cnt = getArguments().getInt("cnt");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        search_btn = view.findViewById(R.id.search_btn);
        search_edit = view.findViewById(R.id.search_edit);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                String search = search_edit.getText().toString();
                bundle.putString("search_term",search);
                /*Intent i = new Intent(getContext(),Search_resActivity.class);
                i.putExtras(bundle);
                startActivity(i);*/
            }
        });

        return view;
    }

    public static SearchFragment create(int position) {
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cnt", position);
        fragment.setArguments(bundle);
        return fragment;
    }


}
