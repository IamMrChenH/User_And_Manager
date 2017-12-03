package user_and_manager.chenhao.com.user_and_manager.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.config.Config;
import user_and_manager.chenhao.com.user_and_manager.http.HttpPost;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.admin.AdminActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.traffic.TrafficActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.user.RegistActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.user.UserActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.dialog.IpDialog;
import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;

import static user_and_manager.chenhao.com.user_and_manager.R.id.email;
//import static user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils.getNewJason;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements OnClickListener
{

    private static final int REQUEST_READ_CONTACTS = 0;

    private UserLoginTask mAuthTask = null;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private Toolbar mToolbar;
    private String TAG = "LoginActivity";
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addAnimation(findView(R.id.layout));

        mToolbar = findView(R.id.toolbar);
        initTool(mToolbar);

        mEmailView = (AutoCompleteTextView) findViewById(email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent)
            {
                if (id == R.id.login || id == EditorInfo.IME_NULL)
                {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        initIp();
        initUserNumber();

    }

    private void initUserNumber()
    {
        findView(R.id.userText).setOnClickListener(this);
        findView(R.id.trafficText).setOnClickListener(this);
        findView(R.id.adminText).setOnClickListener(this);
    }

    private void initIp()
    {
        sp = getSharedPreferences("login", 0);
        Config.SERVER_IP = sp.getString("ip1", "") + "."
                + sp.getString("ip2", "") + "."
                + sp.getString("ip3", "") + "."
                + sp.getString("ip4", "");


        findView(R.id.ip).setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new IpDialog().show(getSupportFragmentManager(), "IpDialog");
            }
        });


    }


    private void attemptLogin()
    {
        if (mAuthTask != null)
        {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean isEmaiView = false;
        boolean isPassView = false;
        View focusView = null;

        if (TextUtils.isEmpty(email))
        {
            mEmailView.setError(getString(R.string.error_email_not_null));
            mEmailView.requestFocus();
            return;
        } else
        {
            focusView = mPasswordView;
            isEmaiView = true;
        }

        if (TextUtils.isEmpty(password))
        {
            mPasswordView.setError(getString(R.string.error_password_not_null));
            focusView.requestFocus();
            return;
        } else
        {
            focusView = mPasswordView;
            isPassView = true;
        }
        //用于管理员登录
        if (email.equals("admin"))
        {


        } else if (email.equals("traffic"))
        {
            startActivity(new Intent(this, TrafficActivity.class));
            finish();
            return;
        }


        showProgress(true);
        mAuthTask = new UserLoginTask(email, password);
        mAuthTask.execute((Void) null);


    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show)
    {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else
        {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.logint_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.register_icon)
        {
            startActivity(new Intent(this, RegistActivity.class));
        } else if (item.getItemId() == android.R.id.home)
        {
            finish();
            System.exit(0);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public String[] number = {"123", "1234", "12345", "police", "admin"};

    @Override
    public void onClick(View v)
    {
        String tempNumber = null;
        switch (v.getId())
        {
            case R.id.userText:
                tempNumber = number[(int) (Math.random() * 3)];
                break;
            case R.id.trafficText:
                tempNumber = number[3];
                break;
            case R.id.adminText:
                tempNumber = number[4];
                break;
        }
        mEmailView.setText(tempNumber);
        mPasswordView.setText(tempNumber);
    }


    /**
     * 登录
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Integer>
    {

        private final String mEmail;
        private final String mPassword;
        private int isOK = 101;

        UserLoginTask(String email, String password)
        {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Void... params)
        {
            // TODO: attempt authentication against a network service.

            try
            {
                // Simulate network access.
                Thread.sleep(500);
            } catch (InterruptedException e)
            {
                return isOK;
            }

            JSONObject newJason = JasonUtils.NewJason();
            JasonUtils.put(newJason, "username", mEmail);
            JasonUtils.put(newJason, "userpwd", mPassword);

            HttpPost.post(Config.ACTION_LOGIN, newJason.toString(), new HttpPost
                    .HttpPosttListenter()
            {
                @Override
                public void HttpPostResultListenter(int status, String result)
                {

                    Log.e(TAG, status + "----a" + result);
                    switch (status)
                    {
                        case 200:
                            if (JasonUtils.isResult(result))
                            {
                                BaseData.cur_carID = JasonUtils.getResultCarId(result);
                                String type = null;
                                try
                                {
                                    type = JasonUtils.NewJason(result).getString("type");
                                } catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                                isOK = Integer.valueOf(type);
                            } else
                            {
                                isOK = 10086;
                            }
                            break;
                        case 404:
                            isOK = status;
                            break;
                    }
                }
            });


            // TODO: register the new account here.
            return isOK;
        }

        @Override
        protected void onPostExecute(final Integer success)
        {
            mAuthTask = null;
            showProgress(false);

            switch (success)
            {
                case 404:
                    showToast("请检查您的网络是否正常运行！");
                    break;
                case 2:
                    showToast("登录成功 carID =" + BaseData.cur_carID);
                    startActivity(new Intent(LoginActivity.this, UserActivity.class));
                    finish();
                    break;
                case 1:
                    BaseData.cur_carID = "1";
                    showToast("登录成功 carID =" + BaseData.cur_carID);
                    startActivity(new Intent(LoginActivity.this, TrafficActivity.class));
                    finish();
                    break;
                case 0:
                    BaseData.cur_carID = "1";
                    showToast("登录成功 carID =" + BaseData.cur_carID);
                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    finish();
                    break;
                default:
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                    break;


            }


        }

        @Override
        protected void onCancelled()
        {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

