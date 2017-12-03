package user_and_manager.chenhao.com.user_and_manager.ui.adapter.user;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseListGridAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.AddMoneyItem;

/**
 * Created by chenhao on 17-3-19.
 */

public class AddMoneyGridViewAdapter extends BaseListGridAdapter<AddMoneyItem>
{
    public AddMoneyGridViewAdapter(Context context, List<AddMoneyItem> items)
    {
        super(context, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_line_2, null);
        }
        TextView t1 = findView(convertView, R.id.item_line_topText);
        TextView t2 = findView(convertView, R.id.item_line_belowText);

        AddMoneyItem item = getItem(position);

        t1.setText(String.valueOf(item.mMoney) + "元");
        t2.setText("当前售价" + String.valueOf(item.mCureentMoney) + "元");
        if (item.mMoney == -1)
        {
            t1.setText("其他");
            t2.setVisibility(View.GONE);
        }

        return convertView;
    }


}
