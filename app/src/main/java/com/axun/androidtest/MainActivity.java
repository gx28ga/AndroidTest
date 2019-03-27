package com.axun.androidtest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_password;
    private CheckBox cb_save;
    private Button btn_submit;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String sdDir=null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if(sdCardExist){
            sdDir=Environment.getExternalStorageDirectory().getPath();
            Log.d(TAG, sdDir);

        }





        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        cb_save=findViewById(R.id.cb_save);
        btn_submit=findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new ClickListener());


        checkPermission();

        Map<String,String> map= UserInfoUtils.readInfo(this);
        if(map.containsKey("username")){
            String username=map.get("username");
            String password=map.get("password");
            et_username.setText(username);
            et_password.setText(password);
            cb_save.setChecked(true);
        }
    }

    class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_submit:

                    checkPermission();

                    String username=et_username.getText().toString();
                    String password=et_password.getText().toString();
                    boolean isSave=cb_save.isChecked();

                    if("".equals(username)){
                        Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }else if("".equals(password)){
                        Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(isSave){
                        Boolean isSaved=UserInfoUtils.saveInfo(getApplicationContext(),username,password);
                        Log.w(TAG, isSaved+"" );

                        Toast.makeText(getApplicationContext(),"账号已经保存到本地了",Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();



                    break;


                default:
                    break;
            }
        }
    }
     private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

        } else {
            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();

        }
    }

}
