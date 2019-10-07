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
    int[] sales = new int[16];
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        //TextViewを宣言
        TextView[] textViews = {(TextView)findViewById(R.id.textView),(TextView)findViewById(R.id.textView2),(TextView)findViewById(R.id.textView3),(TextView)findViewById(R.id.textView4),
                (TextView)findViewById(R.id.textView5),(TextView)findViewById(R.id.textView6),(TextView)findViewById(R.id.textView7),(TextView)findViewById(R.id.textView8),(TextView)findViewById(R.id.textView9),
                (TextView)findViewById(R.id.textView10),(TextView)findViewById(R.id.textView11),
                (TextView)findViewById(R.id.customer),(TextView)findViewById(R.id.engineer),(TextView)findViewById(R.id.discount)};

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
                values.put("Hotdog", 0);
                values.put("Tuna", 0);
                values.put("Egg", 0);
                values.put("Peach", 0);
                values.put("Orange", 0);
                values.put("ChocoBanana", 0);
                values.put("HotCoffee", 0);
                values.put("IceCoffee", 0);
                values.put("Milktea", 0);
                values.put("Cola", 0);
                values.put("Calpis", 0);
                values.put("Registernum", 0);
                values.put("Engineerstunum", 0);
                values.put("DiscountTicket", 0);

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
