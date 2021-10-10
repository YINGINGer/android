package com.example.system2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.system2.model.User;

//注册的Activity
public class regActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_save;
    private Button btn_reset;//重置按钮
    private Button btn_back;
    private EditText et_account;
    private EditText et_password;
    private EditText et_password_cfm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        btn_save=findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
        btn_reset=findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(this);
        btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        et_account=findViewById(R.id.et_account);
        et_password=findViewById(R.id.et_password);
        et_password_cfm=findViewById(R.id.et_password_cfm);
    }

    @Override
    public void onClick(View v){ //点击事件中
        switch (v.getId()){
            case R.id.btn_save:
                //获取输入框中的内容
                String account=et_account.getText().toString();
                System.out.println(account);//可获取
                String password=et_password.getText().toString();
                String password_cfm=et_password_cfm.getText().toString();
                if (account.trim().length()==0){
                    //.trim()去掉左右空格
                    Toast.makeText(regActivity.this,"账号不能为空",Toast.LENGTH_LONG).show();
                    System.out.println("账号空");//ok
                    return;//中断方法
                }
                if (password.length()==0){
                    Toast.makeText(regActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    System.out.println("密码空");
                    return;
                }
                if (!password.equals(password_cfm)){
                    Toast.makeText(regActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                    System.out.println("密码不一致");
                    return;
                }
//                判断账号是否重复
                if(isExistByAccountAndPhone(account)){
                    Toast.makeText(regActivity.this,"已有此账号",Toast.LENGTH_SHORT).show();
                    return;
                }
                User bean=new User(account,password);//将数据打包到User对象中
                loginActivity.list.add(bean);//添加到集合中
                finish();//返回到登录界面
                break;
            case R.id.btn_reset: //清空输入框内容
                et_account.setText("");
                et_password.setText("");
                et_password_cfm.setText("");
                break;
            case R.id.btn_back:
                finish();
                break;

        }
    }
//  根据账号密码查询是否存在用户
    public boolean isExistByAccountAndPhone(String account){
//        item 集合中的元素
        for (User item:loginActivity.list){
            if(item.getAccount().equals(account)){
                return true;
            }
        }
        return false;
    }

}