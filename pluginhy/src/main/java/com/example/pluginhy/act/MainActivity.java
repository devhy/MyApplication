package com.example.pluginhy.act;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pluginhy.R;
import com.example.pluginhy.test.IShowToast;
import com.example.pluginhy.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.security.Permission;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    private IShowToast iShowToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        requestPer();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDexClass();
            }
        });
    }

    /**
     * 加载Dex文件中的class，并调用其中的showToast()方法
     */
    private void loadDexClass() {
        //test_jar的解压路径（为了安全性，这个目录一般是apk的安装目录下面的子目录）
        File cacheFile = FileUtils.getCacheDir(getApplicationContext());

        String internalPath = cacheFile.getAbsolutePath() + File.separator + "test_dex.jar";

        File desFile = new File(internalPath);

        try {
            if (!desFile.exists()) {
                desFile.createNewFile();

                FileUtils.copyFiles(this, "test_dex.jar", desFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //下面开始加载Dex
        DexClassLoader dexClassLoader = new DexClassLoader(internalPath, cacheFile.getAbsolutePath(), null,
                getClassLoader());

        //加载Dex 里面的class，打包是的完整包名
        try {
            Class clazz = dexClassLoader.loadClass("com.example.pluginhy.test.ShowToastImpl");

            iShowToast = (IShowToast) clazz.newInstance();

            iShowToast.showToast(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void requestPer() {

        int grantResult = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (grantResult != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    110);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 110 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "没有授予权限", Toast.LENGTH_SHORT).show();
            finish();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
