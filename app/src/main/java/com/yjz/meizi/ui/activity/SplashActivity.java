package com.yjz.meizi.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yjz.meizi.BuildConfig;
import com.yjz.meizi.utils.BaseSafeHandler;

import io.reactivex.functions.Consumer;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onRestart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkPermission();
    }

    private void goMain() {
        new DelayHandler(this).sendEmptyMessageDelayed(0, 1000);
    }

    /**
     * 请求权限
     */
    public void checkPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            //同意
                            goMain();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //拒绝,没有选中[不在询问]
                            checkPermission();
                        } else {
                            //拒绝,选中[不在询问]
                            showMissingPermissionDialog("手机存储");
                        }
                    }
                });
    }

    public void showMissingPermissionDialog(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
        final AlertDialog alertDialog = builder.create();
        builder.setMessage("当前应用缺少-" + s + "-权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。\n\n最后点击两次后退按钮，即可返回。");
        // 拒绝, 退出应用
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                finish();
            }
        });
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                startActivity(intent);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }


    private void autoLogin() {
        MainActivity.go(this);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }


    private static class DelayHandler extends BaseSafeHandler<SplashActivity> {

        DelayHandler(SplashActivity instance) {
            super(instance);
        }

        @Override
        protected void handleMessage(SplashActivity splashActivity, Message msg) {
            splashActivity.autoLogin();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
