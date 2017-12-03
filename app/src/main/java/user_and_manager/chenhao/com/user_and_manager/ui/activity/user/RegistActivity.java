package user_and_manager.chenhao.com.user_and_manager.ui.activity.user;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;
import user_and_manager.chenhao.com.user_and_manager.config.Config;
import user_and_manager.chenhao.com.user_and_manager.http.HttpPost;
import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;

/**
 * A login screen that offers login via email/password.
 */
public class RegistActivity extends BaseActivity implements LoaderCallbacks<Cursor> {

    private String TAG = "RegistActivity";
    private RegistLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mRePasswordView;
    private EditText mPhoneNumberView;
    private View mProgressView;
    private View mLoginFormView;

    private Toolbar mmToolbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        // Set up the login form.
        mmToolbarView = findView(R.id.toolbar);
        initTool(mmToolbarView);
        initViews();


    }

    private void initViews() {
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mRePasswordView = findView(R.id.re_password);
        mPhoneNumberView = findView(R.id.phone);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mRePasswordView.setError(null);
        mPhoneNumberView.setError(null);


        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        String rePassword = mRePasswordView.getText().toString();
        String phoneNumber = mPhoneNumberView.getText().toString();


        if (TextUtils.isEmpty(email)) {
            mEmailView.requestFocus();
            mEmailView.setError("账号不能为空");
            return;
        }

        if (!isEmailValid(email)) {
            mEmailView.requestFocus();
            mEmailView.setError("账号不能小于4为数");
            return;
        }


        if (TextUtils.isEmpty(password)) {
            mPasswordView.requestFocus();
            mPasswordView.setError("密码不能为空");
            return;
        }

        if (TextUtils.isEmpty(rePassword)) {
            mRePasswordView.requestFocus();
            mRePasswordView.setError("再次输入密码不能为空");
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberView.requestFocus();
            mPhoneNumberView.setError("电话不能空");
            return;
        }

        if (!password.equals(rePassword)) {
            mRePasswordView.requestFocus();
            mRePasswordView.setError("两次密码不同,请重新输入。");
            return;
        }


        showProgress(true);
        mAuthTask = new RegistLoginTask(email, password, phoneNumber);
        mAuthTask.execute((Void) null);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        if (email.length() < 4) {
//            showToast("账号不能小于4为数");
            return false;
        }
        return true;

//        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegistActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class RegistLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mEmail;
        private final String mPassword;
        private final String mPhoneNumber;

        private int isOK = 0;

        RegistLoginTask(String email, String password, String phoneNumber) {
            mEmail = email;
            mPassword = password;
            mPhoneNumber = phoneNumber;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            try {
                // Simulate network access.
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return isOK;
            }

            try {
                JSONObject tempObj = JasonUtils.NewJason()
                        .put("username", mEmail)
                        .put("userpwd", mPassword)
                        .put("phone", mPhoneNumber);
                Log.e("233", "doInBackground: " + tempObj.toString());


                HttpPost.post(Config.ACTION_REGISTER, tempObj.toString(), new HttpPost.HttpPosttListenter() {
                    @Override
                    public void HttpPostResultListenter(int status, String result) {
                        Log.e(TAG, status + "----a" + result);
                        switch (status) {
                            case 200:
                                if (JasonUtils.isResult(result)) {
                                    isOK = status;
                                } else {
                                    isOK = 201;
                                }
                                break;
                            case 404:
                                isOK = status;
                                break;
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return isOK;
        }

        @Override
        protected void onPostExecute(final Integer success) {
            mAuthTask = null;
            showProgress(false);

            switch (success) {
                case 404:
                    showToast("请检查您的网络是否正常运行！");
                    break;
                case 200:
                    showToast("注册成功");
                    finish();
                    break;
                case 201:
                    mEmailView.setError("账号或电话已绑定.\n 请重新输入！");
                    mEmailView.requestFocus();
                    break;
                default:
                    showToast("注册失败");
                    break;


            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

