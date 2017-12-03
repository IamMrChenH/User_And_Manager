package user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.bean.TrafficItem;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.holder.BaseViewHolder;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.holder.TraOneViewHolder;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.holder.TraTwoViewHolder;

/**
 * Created by chenhao on 17-3-20.
 */

public class TrafficHomeAdapter extends RecyclerView.Adapter<BaseViewHolder>
{

    public interface OnItemClickListener
    {

        public void onItemClick(View view, int position);
    }


    public OnItemClickListener mItemClickListener;


    public void setOnItemClickListener(OnItemClickListener mItemClickListener)
    {
        this.mItemClickListener = mItemClickListener;
    }

    private LayoutInflater mInflater;
    private List<TrafficItem> mTrafficItems;


    public TrafficHomeAdapter(Context context, List<TrafficItem> TrafficItems)
    {
        mInflater = LayoutInflater.from(context);
        mTrafficItems = TrafficItems;
    }


    /**
     * 创建VIew
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
//        TrafficItem tempItem = mTrafficItems.get(0);
        switch (viewType)
        {
            case 0:
                return new TraOneViewHolder(mInflater.inflate(R.layout.item_line_6, parent, false));
            case 1:
                return new TraTwoViewHolder(mInflater.inflate(R.layout.item_line_7, parent, false));
            case 2:
                return new TraTwoViewHolder(mInflater.inflate(R.layout.item_line_8, parent, false));
        }
        return new TraOneViewHolder(mInflater.inflate(R.layout.item_line_6, parent, false));

    }

    @Override
    public int getItemViewType(int position)
    {
        return getItem(position).mType;
    }


    public TrafficItem getItem(int position)
    {
        return mTrafficItems.get(position);
    }

    /**
     * 绑定View
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position)
    {
        LayoutParams lp = null;
        switch (getItemViewType(position))
        {

            case 0:
                break;
            case 1:
                lp = holder.itemView.getLayoutParams();
                lp.height = 200;
                holder.itemView.setLayoutParams(lp);
                break;
            case 2:
                lp = holder.itemView.getLayoutParams();
                lp.height = 400;
                holder.itemView.setLayoutParams(lp);
                break;
        }


        holder.onBinView(getItem(position));
        if (mItemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mItemClickListener.onItemClick(v, holder.getLayoutPosition());
                }
            });
        }


    }

    /**
     * 设置item长度
     *
     * @return
     */
    @Override
    public int getItemCount()
    {
        return mTrafficItems.size();
    }


}
