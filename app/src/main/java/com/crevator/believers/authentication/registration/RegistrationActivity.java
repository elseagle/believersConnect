package com.crevator.believers.authentication.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.crevator.believers.PresenterManager;
import com.crevator.believers.R;

import butterknife.ButterKnife;

import static com.crevator.believers.authentication.Authenticator.ARG_ACCOUNT_NAME;
import static com.crevator.believers.authentication.Authenticator.ARG_ACCOUNT_TYPE;
import static com.crevator.believers.authentication.Authenticator.ARG_AUTH_TYPE;

public class RegistrationActivity extends AppCompatActivity implements RegistrationContract.View {
    RegistrationContract.Presenter mPresenter;

    String mAccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter(savedInstanceState);
        initOthers();
    }

    void initPresenter(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mPresenter = new RegistrationPresenter();
        } else {
            mPresenter = PresenterManager.getInstance().restorePresnter(savedInstanceState);
        }
    }

    void initOthers() {
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        String accountName = getIntent().getStringExtra(ARG_ACCOUNT_NAME);
        String mAuthTokenType = getIntent().getStringExtra(ARG_AUTH_TYPE);
        mAccountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
        mPresenter.initializeAuth(accountName, mAccountType);
    }


    public void onCancelClick(View view) {
        mPresenter.cancelRegistration();
    }


    public void onSignUpClick(View view) {
        mPresenter.register(null, null);
    }

    @Override
    public void onCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onResultCompleted(Intent intent) {
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void showLoading(boolean show) {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(RegistrationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mPresenter, outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.bindView(this);
    }
}
