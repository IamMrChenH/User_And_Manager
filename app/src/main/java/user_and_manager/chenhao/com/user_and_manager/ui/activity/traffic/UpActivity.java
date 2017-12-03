package user_and_manager.chenhao.com.user_and_manager.ui.activity.traffic;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.config.Config;
import user_and_manager.chenhao.com.user_and_manager.http.HttpPost;
import user_and_manager.chenhao.com.user_and_manager.service.UpdateService;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.bean.RuleItem;
import user_and_manager.chenhao.com.user_and_manager.utils.ImageLoader;
import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;
import user_and_manager.chenhao.com.user_and_manager.utils.PhotoUtils;
import user_and_manager.chenhao.com.user_and_manager.widget.AutoEditText;

import static java.util.Map.Entry;
import static user_and_manager.chenhao.com.user_and_manager.R.id.vOffice;

public class UpActivity extends BaseActivity
{

    private List<String> mFilePath;
    private static String tempPath;
    private Intent mUpdateService;

    private ImageView[] mImageViews;
    private int mImaId[] = {R.id.imager1, R.id.imager2, R.id.imager3};


    private TextView vTimeText;

    private Spinner mCarID_text;
    private TextView vOfficeText;
    private AutoEditText mCarNumberText;

    private AutoEditText mAddressText;
    private Spinner mMsgText;
    private AutoEditText mGradeText;

    private AutoEditText mMoneyText;

    private TextView mChuFa_Info;
    private Button mBtn_ok;

    private List<String> countries;
    private ArrayAdapter<String> mIDTextAdapter;
    private ArrayAdapter<String> mAddressTextAdapter;
    private ArrayAdapter<String> mSpinerAdapter;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up);
        initTool((Toolbar) findView(R.id.toolbar));
        initData();
        initViews();
        initSetonClick();
        mFilePath.add(tempPath = PhotoUtils.startPhoto(this));
    }

    private void initData()
    {
        mUpdateService = new Intent(this, UpdateService.class);
        mUpdateService.putExtra("action", "up");
        mFilePath = new ArrayList<>();

        countries = new ArrayList<>();
        countries.add("请选择");
        countries.add("1");
        countries.add("2");
        countries.add("3");
        countries.add("4");
        countries.add("5");
        countries.add("6");
        countries.add("7");
        countries.add("8");
        countries.add("9");
        countries.add("10");


        if (BaseData.itemHashMap != null)
        {
            List<String> key = new ArrayList<>();
            Iterator iter = BaseData.itemHashMap.entrySet().iterator();
            while (iter.hasNext())
            {
                Entry entry = (Entry) iter.next();
                key.add(entry.getKey().toString());
            }
            key.add(0, "请选择");
            mSpinerAdapter = new ArrayAdapter<String>(this, android.R.layout
                    .simple_list_item_1, key);
        }


        mIDTextAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                countries);
        mAddressTextAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.address));


    }

    private void initViews()
    {
        mImageViews = new ImageView[3];
        for (int i = 0; i < mImaId.length; i++)
        {
            mImageViews[i] = findView(mImaId[i]);
        }


        mCarID_text = findView(R.id.id_carID_text);

        vTimeText = findView(R.id.vID);
//        vTimeText.setText(PhotoUtils.getRandomName());
        vTimeText.setText("记录违规时间：" + getTime(System.currentTimeMillis()));

        vOfficeText = findView(vOffice);
//        vOfficeText.setText("");

        mCarNumberText = findView(R.id.id_carNumber_text);
        mAddressText = findView(R.id.id_address_text);

        mMsgText = findView(R.id.id_Msg_text);
        mGradeText = findView(R.id.id_grade_text);
        mMoneyText = findView(R.id.id_money_text);
        mBtn_ok = findView(R.id.btn_ok);
//        mCarNumberText
        mCarID_text.setAdapter(mIDTextAdapter);
        mCarID_text.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String src = mCarID_text.getSelectedItem().toString();
                if (src.equals("请选择"))
                {
                    showToast("请选择小车ID");
                } else
                {
                    mCarNumberText.setText("MM0000" + src);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        mAddressText.setAdapter(mAddressTextAdapter);
        mMsgText.setAdapter(mSpinerAdapter);

        mChuFa_Info = findView(R.id.chufa_info);
        ((View) (mChuFa_Info.getParent())).setVisibility(View.GONE);


        mMsgText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                msg = mMsgText.getSelectedItem().toString();
                RuleItem item = BaseData.itemHashMap.get(msg);
                if (item != null)
                {
                    mMoneyText.setText(item.money);
                    mGradeText.setText(item.score);
                }
                showToast(msg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }


    private void initSetonClick()
    {
        mBtn_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Update();
            }
        });
    }

    private void Update()
    {
        String carId = mCarID_text.getSelectedItem().toString();
        if (TextUtils.isEmpty(carId) && carId.equals("请选择"))
        {
            showToast("请选择小车ID");
            mCarID_text.requestFocus();
            return;
        }


        String address = mAddressText.getText().toString();
        if (TextUtils.isEmpty(address))
        {
            mAddressText.setError("不能为空！");
            mAddressText.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(msg) || msg.equals("请选择"))
        {
            showToast("不能不选择！");
            mMsgText.requestFocus();
            return;
        }


        String office = vOfficeText.getText().toString();
        if (TextUtils.isEmpty(office))
        {
            vOfficeText.setError("不能为空！");
            vOfficeText.requestFocus();
            return;
        }

        String grade = mGradeText.getText().toString();
        if (TextUtils.isEmpty(grade))
        {
            grade = "0";
        }

        String money = mMoneyText.getText().toString();
        if (TextUtils.isEmpty(money))
        {
            money = "0";
        }
        String[] strings = {address, "自动生成", office, carId, msg, grade, money};

        new UpDataAsyncTask(strings).execute();


    }

    //    private String[] keys = {"vXY", "vID", "vOffice", "carID", "msg", "grade", "money"};
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.photo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.photo_btn)
        {
            mFilePath.add(tempPath = PhotoUtils.startPhoto(this));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // resultCode 0 取消 -1成功
        Toast.makeText(this, "(" + requestCode + "," + resultCode + ")", Toast.LENGTH_SHORT).show();
        if (resultCode == 0)
        {
            mFilePath.remove(tempPath);
            setImageView(mFilePath);

        } else if (resultCode == -1)
        {
            mUpdateService.putExtra("filePath", tempPath);
            startService(mUpdateService);
            mFilePath.add(tempPath = PhotoUtils.startPhoto(this));

        }

    }

    public void setImageView(List<String> photos)
    {
        int lenght = 3;
        if (photos.size() < 3)
        {
            lenght = photos.size();
        }

        for (int i = 0; i < lenght; i++)
        {
            mImageViews[i].setVisibility(View.VISIBLE);
            String temp = photos.get(i);
            ImageLoader.getInstance().loadImage(temp, mImageViews[i]);
            mImageViews[i].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String src = v.getTag().toString();
                    if (!TextUtils.isEmpty(src))
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(src)), ".jpg");
                        startActivity(intent);
                    }
                }
            });
        }

    }


    class UpDataAsyncTask extends AsyncTask<Void, Void, Integer>
    {

        private String[] mDatas;
        private String[] keys = {"vXY", "vID", "vOffice", "carID", "msg", "grade", "money"};
        private Integer mStatus = 0;

        public UpDataAsyncTask(String[] mDatas)
        {
            this.mDatas = mDatas;
        }

        @Override
        protected Integer doInBackground(Void... params)
        {

            JSONObject newJason = JasonUtils.NewJason();
            for (int i = 0; i < mDatas.length; i++)
            {
                JasonUtils.put(newJason, keys[i], mDatas[i]);
            }
            if (mFilePath.size() > 0)
            {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < mFilePath.size(); i++)
                {

                    stringBuffer.append(new File(mFilePath.get(i)).getName() + ",");
                }
                stringBuffer.substring(0, stringBuffer.length() - 1);
                JasonUtils.put(newJason, "photo", stringBuffer.toString());
            }

            HttpPost.post(Config.ACTION_ADD_VIOLATION, newJason.toString(), new HttpPost
                    .HttpPosttListenter()
            {
                @Override
                public void HttpPostResultListenter(int status, String result)
                {
                    if (status == 200)
                    {
                        if (JasonUtils.isResult(result))
                        {

                        } else
                        {

                            status = 401;
                        }
                    }


                    mStatus = status;

                }
            });

            return mStatus;
        }

        @Override
        protected void onPostExecute(Integer integer)
        {
            super.onPostExecute(integer);
            switch (integer)
            {
                case 200:
                    showToast("上传成功");
                    finish();
                    break;
                default:
                    showToast("上传失败，网络问题吧！");
                    break;
            }

        }
    }


}
