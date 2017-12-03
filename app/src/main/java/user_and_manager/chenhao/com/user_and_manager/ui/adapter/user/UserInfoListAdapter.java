package user_and_manager.chenhao.com.user_and_manager.ui.adapter.user;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseListGridAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.UserInfoItem;

/**
 * Created by chenhao on 17-3-19.
 */

public class UserInfoListAdapter extends BaseListGridAdapter<UserInfoItem>
{

    public UserInfoListAdapter(Context context, List<UserInfoItem> items)
    {
        super(context, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_line_3, null);
        }


        ImageView img = findView(convertView, R.id.item_line_imager);
        TextView t1 = findView(convertView, R.id.item_line_topText);
        TextView t2 = findView(convertView, R.id.item_line_belowText);

        UserInfoItem item = getItem(position);
        t1.setText(item.mTitle);
        if (item.mType == 0)
        {
            img.setVisibility(View.VISIBLE);
            img.setImageResource(item.mIconId);
            t2.setVisibility(View.GONE);
        } else if (item.mType == 1)
        {
            t2.setVisibility(View.VISIBLE);
            t2.setText(isEmpty(item.mContent));
            img.setVisibility(View.GONE);
        } else if (item.mType == 2)
        {
            t2.setVisibility(View.VISIBLE);
            t2.setText(isEmpty(item.mContent));
            t2.setTextColor(Color.RED);
            img.setVisibility(View.GONE);
        }

        return convertView;
    }



}
