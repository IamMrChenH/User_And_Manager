package user_and_manager.chenhao.com.user_and_manager.ui.dialog;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.config.Config;

public class IpDialog extends DialogFragment implements OnClickListener {
    /**
     * 设置ip dialog
     */
    TextView ip1;
    TextView ip2;
    TextView ip3;
    TextView ip4;
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.set_ip_dialog, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        sp = getActivity().getSharedPreferences("login", 0);
        ip1 = (TextView) getView().findViewById(R.id.ip1_edit_text);
        ip2 = (TextView) getView().findViewById(R.id.ip2_edit_text);
        ip3 = (TextView) getView().findViewById(R.id.ip3_edit_text);
        ip4 = (TextView) getView().findViewById(R.id.ip4_edit_text);
        ip1.setText(sp.getString("ip1", ""));
        ip2.setText(sp.getString("ip2", ""));
        ip3.setText(sp.getString("ip3", ""));
        ip4.setText(sp.getString("ip4", ""));
        try {
            if (TextUtils.isEmpty(ip1.getText())) {
                String[] split = Config.SERVER_IP.split("\\.");
                ip1.setText(split[0]);
                ip2.setText(split[1]);
                ip3.setText(split[2]);
                ip4.setText(split[3]);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getView().findViewById(R.id.ok_button).setOnClickListener(this);
        getView().findViewById(R.id.close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dismiss();
            }
        });
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View arg0) {


        int[] arr = new int[5];
        try {

            arr[1] = Integer.valueOf(ip1.getText().toString());
            arr[2] = Integer.valueOf(ip2.getText().toString());
            arr[3] = Integer.valueOf(ip3.getText().toString());
            arr[4] = Integer.valueOf(ip4.getText().toString());


        } catch (Exception e) {
            Toast.makeText(getActivity(), "输入的IP不规范！", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Config.SERVER_IP = arr[1] + "." + arr[2] + "." + arr[3] + "." + arr[4];
        sp.edit().putString("ip", Config.SERVER_IP).commit();
        sp.edit().putString("ip1", arr[1] + "").commit();
        sp.edit().putString("ip2", arr[2] + "").commit();
        sp.edit().putString("ip3", arr[3] + "").commit();
        sp.edit().putString("ip4", arr[4] + "").commit();
        dismiss();
    }


}
