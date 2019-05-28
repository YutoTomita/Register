package com.example.tjc.register;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by tomi on 2018/10/14.
 */

public class SalesActivity extends AppCompatActivity {

    private final static String DB_NAME = "sales.db";
    private final static  String DB_TABLE = "sales";
    private SQLiteDatabase db;
    private final static int DB_VERSION = 1;
    int[] sales = new int[15];
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        //TextViewを宣言
        TextView[] textViews = {(TextView)findViewById(R.id.textView),(TextView)findViewById(R.id.textView2),(TextView)findViewById(R.id.textView3),(TextView)findViewById(R.id.textView4),
                (TextView)findViewById(R.id.textView5),(TextView)findViewById(R.id.textView6),(TextView)findViewById(R.id.textView7),(TextView)findViewById(R.id.textView8),(TextView)findViewById(R.id.textView9),
                (TextView)findViewById(R.id.textView10),(TextView)findViewById(R.id.textView11),(TextView)findViewById(R.id.textView12),(TextView)findViewById(R.id.textView13),
                (TextView)findViewById(R.id.customer),(TextView)findViewById(R.id.engineer)};

        //売り上げデータベースを読み込み
        RegisterActivity.DBHelper dbHelper = new RegisterActivity.DBHelper(this);
        db = dbHelper.getWritableDatabase();
        Cursor cursor;
        try{
            cursor = db.query(DB_TABLE, null, null, null, null, null, null);
            cursor.moveToFirst();
            for(int i=0; i<sales.length; i++){
                sales[i] = cursor.getInt(i);
                textViews[i].setText(Integer.toString(sales[i]));
            }
            cursor.close();
        }catch (Exception e){}
    }

    public void onClick(View view){
        //アクティビティ遷移を行う
        Intent intent = new Intent(getApplication(), RegisterActivity.class);
        startActivity(intent);
    }

    public void clearSales(View view){
        // 確認ダイアログの作成
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SalesActivity.this);
        alertDialog.setTitle("確認");
        alertDialog.setMessage("本当に売り上げデータを消去しますか？");
        alertDialog.setPositiveButton("消去する", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ContentValues values = new ContentValues();
                values.put("HotCake", 0);
                values.put("WHotCake", 0);
                values.put("HotCoffee", 0);
                values.put("IceCoffeeS", 0);
                values.put("IceCoffeeL", 0);
                values.put("HotTea", 0);
                values.put("IceTeaS", 0);
                values.put("IceTeaL", 0);
                values.put("HotGreenTea", 0);
                values.put("OrangeS", 0);
                values.put("OrangeL", 0);
                values.put("ColaS", 0);
                values.put("ColaL", 0);
                values.put("Registernum", 0);
                values.put("Engineerstunum", 0);

                int colnum = db.update(DB_TABLE, values, null, null);
                if(colnum == 0)db.insert(DB_TABLE, String.valueOf(0), values);
                Context context = getApplicationContext();
                CustomToast.makeText(context , "初期化に成功しました", 500).show();
            }
        });
        alertDialog.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.create().show();
    }
}
