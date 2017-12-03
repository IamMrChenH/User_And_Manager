package user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.bean.TrafficItem;

/**
 * Created by chenhao on 17-3-22.
 */

public class TraTwoViewHolder extends BaseViewHolder {


    private ImageView mImager1;
    private TextView topText1;

    public TraTwoViewHolder(View itemView) {
        super(itemView);
        mImager1 = finView(R.id.item_line_imager);
        topText1 = finView(R.id.item_line_text);

    }

    @Override
    public void onBinView(TrafficItem data) {

        mImager1.setImageResource(data.mIcon1);
        topText1.setText(data.topText);
    }

}
