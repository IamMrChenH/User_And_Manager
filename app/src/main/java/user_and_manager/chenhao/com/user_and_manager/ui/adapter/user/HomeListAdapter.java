package user_and_manager.chenhao.com.user_and_manager.ui.adapter.user;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseListGridAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.HomeMenuItem;

/**
 * Created by chenhao on 17-3-18.
 */

public class HomeListAdapter extends BaseListGridAdapter<HomeMenuItem>
{

    public HomeListAdapter(Context context, List<HomeMenuItem> items)
    {
        super(context, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_line_1, null);
        }

        ImageView itemIcon = findView(convertView, R.id.item_line_imager);
        TextView itemText = findView(convertView, R.id.item_line_topText);

        HomeMenuItem item = getItem(position);

//        itemIcon.setImageResource(item.mIcon);
        itemText.setText(item.mString);

        return convertView;
    }


}
