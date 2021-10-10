package com.example.system2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainSqliteActivity extends AppCompatActivity {

    SQLiteDatabase db;//SQLiteDatabase对象
    public String db_name="gallery.sqlite";//数据库名
    public String table_name="pic";//表名
    final DbHelper helper=new DbHelper(this,db_name,null,1);//新建一个数据库
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite_main);

        Button btn_insert=(Button) findViewById(R.id.btn_insert);
        Button btn_delete=(Button) findViewById(R.id.btn_delete);
        Button btn_update=(Button) findViewById(R.id.btn_update);
        Button btn_select=(Button) findViewById(R.id.btn_select);

        db=helper.getWritableDatabase();//从辅助类获得数据库对象
        initDatabase(db);//初始化数据
        updateSpinner();//更新下拉列表中的数据
        View.OnClickListener oc1=new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ContentValues cv=new ContentValues();//ContentValues对象
                switch (v.getId()){
                    case R.id.btn_insert:
                        cv.put("fileName","pic5.jpg");
                        cv.put("description","t5");
                        long long1=db.insert("pic","",cv);//添加数据
                        if(long1==-1){
                            Toast.makeText(MainSqliteActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                            System.out.println("添加失败");
                        }else{
                            Toast.makeText(MainSqliteActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            System.out.println("添加成功");
                        }
                        updateSpinner();//更新下拉列表
                        break;
                    case R.id.btn_delete:
                        long long2=db.delete("pic","description='t5'",null);
                        System.out.println(long2);
                        updateSpinner();
                        break;
                    case R.id.btn_update:
                        cv.put("fileName","pic0.jpg");
                        cv.put("description","t0");
                        long long3=db.update("pic",cv,"fileName='pic5.jpg'",null);
                        System.out.println(long3);
                        updateSpinner();
                        break;
                    case R.id.btn_select:
                        Cursor c=db.query("pic",null,null,null,null,null,null);
                        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){//循环显示
                            System.out.println("no"+c.getInt(0)+":"+c.getString(1)+"::"+c.getString(2));
                        }
                        updateSpinner();
                        break;
                }
            }
        };
        btn_insert.setOnClickListener(oc1);//给按钮绑定监听器
        btn_delete.setOnClickListener(oc1);
        btn_update.setOnClickListener(oc1);
        btn_select.setOnClickListener(oc1);
    }
    public void initDatabase(SQLiteDatabase db){ //初始化表
        ContentValues cv=new ContentValues();
        cv.put("fileName","piv1.jpg");
        cv.put("description","t1");
        db.insert(table_name,"",cv);

        cv.put("fileName","piv2.jpg");
        cv.put("description","t2");
        db.insert(table_name,"",cv);
        cv.put("fileName","piv3.jpg");
        cv.put("description","t3");
        db.insert(table_name,"",cv);
        cv.put("fileName","piv4.jpg");
        cv.put("description","t4");
        db.insert(table_name,"",cv);
    }
    public void updateSpinner(){//更新下拉列表
        final TextView tv=(TextView) findViewById(R.id.textView2);
        Spinner s=(Spinner) findViewById(R.id.spinner1);
        //从数据库中获取数据放入游标Cursor对象
        final Cursor cursor=db.query("pic",null,null,null,null,null,null);
        //创建简单游标适配器
        SimpleCursorAdapter adapter=new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item,cursor,new String[]{
        "fileName","description"},new int[]{android.R.id.text1,android.R.id.text2});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);//给下拉列表设置适配器

        //定义子元素选择监听器
        AdapterView.OnItemSelectedListener oisl=new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                cursor.moveToPosition(position);
                tv.setText("当前pic的描述为:"+cursor.getString(2));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        s.setOnItemSelectedListener(oisl);//给下拉列表绑定子元素选择监听器
    }

    @Override
    public void onDestroy(){ //窗口销毁时删除表中数据
        super.onDestroy();
        db.delete(table_name,null,null);
        updateSpinner();
    }
}