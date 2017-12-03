package user_and_manager.chenhao.com.user_and_manager.ui.adapter.user;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.base.BaseListGridAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.ParkCatItem;

import static user_and_manager.chenhao.com.user_and_manager.base.BaseData.mParkadd;

/**
 * Created by chenhao on 17-3-20.
 */

public class ParkCarAdapter extends BaseListGridAdapter<ParkCatItem>
{

    public ParkCarAdapter(Context context, List<ParkCatItem> items)
    {
        super(context, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_line_5, null);
        }
        TextView t1 = findView(convertView, R.id.item_line_topText);
        TextView t2 = findView(convertView, R.id.item_line_belowText);

        ParkCatItem item = getItem(position);
        String carString;

        if (mParkadd.get(position) != null)
        {
            int i = BaseData.mParkadd.get(position);
            t1.setText("有车");

            convertView.setBackground(getDrawable(R.drawable.ic_add_money_item_back));

            t1.setTextColor(Color.parseColor("#438adc"));
            t2.setTextColor(Color.parseColor("#438adc"));
        } else
        {
            carString = "空闲车位";
            t1.setText(carString);
            t1.setTextColor(Color.RED);
            t2.setTextColor(Color.RED);
            convertView.setBackground(getDrawable(R.drawable.ic_red_back));
        }
        t2.setText((position + 1) + "号车位");

        return convertView;
    }
}
