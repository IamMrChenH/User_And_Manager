package user_and_manager.chenhao.com.user_and_manager.ui.adapter.user;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.base.BaseListGridAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.VioLationItem;

/**
 * Created by chenhao on 17-3-19.
 */

public class DioLationListAdapter extends BaseListGridAdapter<VioLationItem>
{
    public DioLationListAdapter(Context context, List<VioLationItem> items)
    {
        super(context, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_line_4, null);
        }

        TextView money = findView(convertView, R.id.up_money);
        TextView lastfee = findView(convertView, R.id.up_LastFee);
        TextView time = findView(convertView, R.id.up_time);

        TextView id = findView(convertView, R.id.up_id);
        TextView score = findView(convertView, R.id.up_score);
        TextView carNumber = findView(convertView, R.id.up_catNumber);
        TextView address = findView(convertView, R.id.up_address);

        TextView action = findView(convertView, R.id.up_action);

        View view = findView(convertView, R.id.find_vio_layout);
        VioLationItem item = getItem(position);
        money.setText("罚款" + item.money);
        lastfee.setText("滞纳金0");
        time.setText(getTime(item.time));

        id.setText("违规编号: " + item.id);
        score.setText("扣分: " + item.score);
        carNumber.setText("车牌: " + BaseData.mCarNumber);
        address.setText("地点:(" + item.vXY + ")");
        action.setText("行为: " + item.msg);

        if (!item.flag.equals("0"))
        {
            view.setBackground(getDrawable(R.drawable.find_dio_back));
        } else
        {
            view.setBackground(getDrawable(R.drawable.ic_gray_back));
        }
        return convertView;
    }

}
