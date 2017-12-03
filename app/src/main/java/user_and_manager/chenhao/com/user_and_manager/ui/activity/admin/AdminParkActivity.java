package user_and_manager.chenhao.com.user_and_manager.ui.activity.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.adapter
        .BaseRecycleViewHolder;

import static user_and_manager.chenhao.com.user_and_manager.base.BaseData.mParkadd;

public class AdminParkActivity extends BaseActivity
{
    private RecyclerView mRecyclerView;
    private AdminParkRecycleVeiwAdapter mAdminParkRecycleVeiwAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_park);
        Toolbar mToolBarView = findView(R.id.toolbar);
        mToolBarView.setTitle("停车场车位监控");
        initTool(mToolBarView);

        initDatas();
        initViews();


    }

    private void initViews()
    {
        mRecyclerView = findView(R.id.RecycleView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(mAdminParkRecycleVeiwAdapter);

    }

    private void initDatas()
    {

        mAdminParkRecycleVeiwAdapter = new AdminParkRecycleVeiwAdapter();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.admin_park_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                break;
            case R.id.heng:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                        StaggeredGridLayoutManager.HORIZONTAL));
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    public class AdminParkRecycleVeiwAdapter extends RecyclerView.Adapter<BaseRecycleViewHolder>
    {

        @Override
        public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {

            View inflate = getLayoutInflater().inflate(R.layout.item_line_5, parent, false);

            return new AdminViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(BaseRecycleViewHolder holder, int position)
        {

            if (mParkadd.get(position) != null)
            {
                holder.onBinView(mParkadd.get(position));
            } else
            {
                holder.onBinView(-1);
            }


        }

        @Override
        public int getItemCount()
        {
            return 10;
        }

        public class AdminViewHolder extends BaseRecycleViewHolder<Integer>
        {

            private TextView mTopText;
            private TextView mBelowText;
//            item_line_topText item_line_belowText

            public AdminViewHolder(View itemView)
            {
                super(itemView);
                mTopText = (TextView) itemView.findViewById(R.id.item_line_topText);
                mBelowText = (TextView) itemView.findViewById(R.id.item_line_belowText);
            }

            @Override
            public void onBinView(Integer data)
            {
                if (data == -1)
                {
                    mTopText.setText("空闲车位");
                    itemView.setBackground(AdminParkActivity.this.getResources().getDrawable(R
                            .drawable.ic_red_back, null));
                    mTopText.setTextColor(Color.RED);
                    mBelowText.setTextColor(Color.RED);

                } else
                {
                    mTopText.setText(data + "号小车");
                }
                mBelowText.setText((getLayoutPosition() + 1) + "号车位");

            }
        }


    }

}
