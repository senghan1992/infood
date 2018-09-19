package infofood.senghan1992.com.infofood.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import infofood.senghan1992.com.infofood.R;

public class SettingsFragment extends Fragment {

    int cnt;

    PackageInfo info = null;
    String versionName = "";

    TextView set_information, set_service, set_sos, set_version;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cnt = getArguments().getInt("cnt");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        set_information = view.findViewById(R.id.set_information);
        set_service = view.findViewById(R.id.set_service);
        set_sos = view.findViewById(R.id.set_sos);
        set_version = view.findViewById(R.id.set_version);

        set_information.setOnClickListener(click);
        set_service.setOnClickListener(click);
        set_sos.setOnClickListener(click);
        set_version.setOnClickListener(click);

        //버전정보 가져오기
        try{
            info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        versionName = info.versionName;

        return view;
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final EditText et = new EditText(getContext());
            switch (view.getId()){
                case R.id.set_information:
                    final AlertDialog.Builder dialog1 = new AlertDialog.Builder(getContext());
                    dialog1.setTitle("닉네임 변경").setMessage("변경할 닉네임을 입력하세요").setPositiveButton("변경", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog dialog = dialog1.create();
                            dialog.dismiss();
                        }
                    }).setView(et).show();
                    break;

                case R.id.set_service:
                    final AlertDialog.Builder dialog2 = new AlertDialog.Builder(getContext());
                    dialog2.setTitle("고객지원").setMessage("불편 사항이나 의견이 있으시면\n123@naver.com으로\n문의 주세요.\n감사합니다.^^").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog dialog = dialog2.create();
                            dialog.dismiss();
                        }
                    }).show();
                    break;

                case R.id.set_sos:
                    final AlertDialog.Builder dialog3 = new AlertDialog.Builder(getContext());
                    dialog3.setTitle("게시물 신고").setMessage("신고???").setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog dialog = dialog3.create();
                            dialog.dismiss();
                        }
                    }).show();
                    break;

                case R.id.set_version:
                    final AlertDialog.Builder dialog4 = new AlertDialog.Builder(getContext());
                    dialog4.setTitle("버전 정보").setMessage(versionName).setNegativeButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog dialog = dialog4.create();
                            dialog.dismiss();
                        }
                    }).show();
                    break;
            }
        }
    };

    public static SettingsFragment create(int position){
        SettingsFragment fragment = new SettingsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cnt",position);
        fragment.setArguments(bundle);
        return fragment;
    }

}
