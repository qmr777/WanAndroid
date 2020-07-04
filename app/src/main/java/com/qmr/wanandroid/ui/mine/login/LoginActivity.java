package com.qmr.wanandroid.ui.mine.login;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qmr.base.activity.BaseActivity;
import com.qmr.wanandroid.R;
import com.qmr.wanandroid.model.entity.LoginBean;
import com.qmr.wanandroid.network.base.CheckMapFunction;
import com.qmr.wanandroid.network.base.RequestManager;
import com.qmr.wanandroid.network.base.WanNetworkException;
import com.qmr.wanandroid.util.LoginUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.tv_username)
    EditText tvUsername;
    @BindView(R.id.tv_password)
    EditText tvPassword;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void initView() {
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    private void login() {

        String username = tvUsername.getText().toString();
        String password = tvPassword.getText().toString();
        Log.i(TAG, "login2: " + username + "    " + password);

        RequestManager.getInstance().loginOrRegister().login(username, password)
                .map(new CheckMapFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull LoginBean loginBeanBaseResponse) {
                        //LoginUtil.username = loginBeanBaseResponse.getUsername();
                        LoginUtil.success(loginBeanBaseResponse.getUsername());
                        setResult(1);
                        finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                        shortToast(e.getMessage());
                        tvPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void register() {

        String username = tvUsername.getText().toString();
        String password = tvPassword.getText().toString();
        Log.i(TAG, "login2: " + username + "    " + password);

        RequestManager.getInstance().loginOrRegister().register(username, password, password)
                .map(new CheckMapFunction<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull LoginBean loginBean) {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("注册成功")
                                .setMessage("账号是" + username + "\n" + "密码是：" + password)
                                .setPositiveButton("确定", null)
                                .show();
                        LoginUtil.success(loginBean.getUsername());
                        setResult(1);
                        //finish();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof WanNetworkException) {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("出错了...")
                                    .setMessage(e.getMessage())
                                    .setPositiveButton("确定", null)
                                    .show();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick: " + v.getId());
        switch (v.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.register:
                register();
                break;
            default:
                break;
        }
    }
}