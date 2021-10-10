package com.example.system2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.system2.model.User;

import java.util.ArrayList;
import java.util.List;

//添加点击事件，实现View.OnClickListener
public class loginActivity extends AppCompatActivity implements View.OnClickListener{
/*
Activity获取xml布局文件中的控件
    xml中给要获取的控件添加id
    在Activity中定义成员变量
    在onCreate方法中，获取控件代码必须在setContentView代码后面
 */
    private Button btn_login;
    private Button btn_reg;
    private EditText et_account;
    private EditText et_password;

    //保存注册的用户
    public static List<User> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        btn_login=findViewById(R.id.btn_login);
        //添加点击事件
        btn_login.setOnClickListener(this);

        btn_reg=findViewById(R.id.btn_reg);
        btn_reg.setOnClickListener(this);

        et_account=findViewById(R.id.et_account);
        et_password=findViewById(R.id.et_password);
    }

    protected void onRestart(){//测试
        super.onRestart();
        System.out.println(list);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        Intent intent=new Intent();//创建一个跳转
        switch (id){
            case R.id.btn_login:
                //获取输入框中的内容
                String account=et_account.getText().toString();
                String password=et_password.getText().toString();
                if (account.trim().length()==0){
                    //.trim()去掉左右空格
                    Toast.makeText(loginActivity.this,"账号不能为空",Toast.LENGTH_LONG).show();
                    System.out.println("账号空");//ok
                    return;//中断方法
                }
                if (password.length()==0){
                    Toast.makeText(loginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    System.out.println("密码空");
                    return;
                }
                User user=doLogin(account,password);//执行登录验证
                if (user==null){
                    Toast.makeText(loginActivity.this,"账号密码不匹配",Toast.LENGTH_SHORT).show();
                    System.out.println("账号密码不匹配");
                }else {//登录成功
                    System.out.println("登录成功");
                }
                break;
            case R.id.btn_reg:
                intent.setClass(loginActivity.this,regActivity.class);
                startActivity(intent);
                break;
        }
    }
    private User doLogin(String account,String password){//登录操作
        for (User item:list){
            if(item.getAccount().equals(account)&&item.getPassword().equals(password)){
                return item;
            }
        }
        return null;
    }
}