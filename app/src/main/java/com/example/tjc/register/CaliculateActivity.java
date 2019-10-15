package com.example.tjc.register;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CaliculateActivity extends AppCompatActivity {

    private final static String DB_NAME = "sales.db";
    private final static  String DB_TABLE = "sales";
    private SQLiteDatabase db;
    private final static int DB_VERSION = 3;
    private int engineerstunum = 0, discountticket = 0;
    int total = 0;
    int engineer = 0;
    int discount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caliculate);

        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) total = extras.getInt("total");
        Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, null);
        cursor.moveToFirst();
        engineerstunum = cursor.getInt(13);
        discountticket = cursor.getInt(14);
        TextView showtotal = (TextView)findViewById(R.id.Total);
        showtotal.setText(Integer.toString(total));
    }

    public void register1(View view){
        // 確認ダイアログの作成
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CaliculateActivity.this);
        alertDialog.setTitle("確認");
        alertDialog.setMessage("お預かりした金額は入力と合っていますか？");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText = (EditText)findViewById(R.id.money);
                int money = 0;
                try{
                    String str = editText.getText().toString();
                    money = Integer.parseInt(str);
                }catch (Exception e){
                }
                TextView change = (TextView)findViewById(R.id.change);
                change.setText(Integer.toString(money - total));


                ContentValues values = new ContentValues();
                values.put("Engineerstunum", engineer + engineerstunum);
                values.put("DiscountTicket", discount + discountticket);

                int colnum = db.update(DB_TABLE, values, null, null);
                if (colnum == 0) db.insert(DB_TABLE, String.valueOf(0), values);

                Context context = getApplicationContext();
                CustomToast.makeText(context, "保存に成功しました", 500).show();
            }
        });
        alertDialog.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.create().show();
    }

    public void minus1(View view){
        boolean decrease = false;
        if(engineer != 0){
            engineer--;
            decrease = true;
        }
        TextView num = (TextView)findViewById(R.id.num);
        num.setText(Integer.toString(engineer));
        TextView showtotal = (TextView)findViewById(R.id.Total);
        if(decrease)total += 50;
        showtotal.setText(Integer.toString(total));
    }

    public void plus1(View view){
        boolean increase = false;
        if(total - 50 > 0){
            engineer++;
            increase = true;
        }
        TextView num = (TextView)findViewById(R.id.num);
        num.setText(Integer.toString(engineer));
        TextView showtotal = (TextView)findViewById(R.id.Total);
        if(increase)total -= 50;
        else {
            Context context = getApplicationContext();
            CustomToast.makeText(context, "会計額をマイナスにすることは出来ません", 1000).show();
        }
        showtotal.setText(Integer.toString(total));
    }

    public void minus2(View view){
        boolean decrease = false;
        if(discount != 0){
            discount--;
            decrease = true;
        }
        TextView num = (TextView)findViewById(R.id.num1);
        num.setText(Integer.toString(discount));
        TextView showtotal = (TextView)findViewById(R.id.Total);
        if(decrease)total += 50;
        showtotal.setText(Integer.toString(total));
    }

    public void plus2(View view){
        boolean increase = false;
        if(total - 50 > 0){
            discount++;
            increase = true;
        }
        TextView num = (TextView)findViewById(R.id.num1);
        num.setText(Integer.toString(discount));
        TextView showtotal = (TextView)findViewById(R.id.Total);
        if(increase)total -= 50;
        else {
            Context context = getApplicationContext();
            CustomToast.makeText(context, "会計額をマイナスにすることは出来ません", 1000).show();
        }
        showtotal.setText(Integer.toString(total));
    }

    public static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        //データベースの生成
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table if not exists " + DB_TABLE + "(" +
                    "Hotdog integer,Tuna integer,Egg integer,Peach integer,Orange integer,Mix integer," +
                    "ChocoBanana integer,HotCoffee integer,IceCoffee integer,Milktea integer,Cola integer," +
                    "Calpis integer,Registernum integer,Engineerstunum integer,DiscountTicket integer)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
