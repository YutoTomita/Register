package com.example.tjc.register;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private final static String DB_NAME = "sales.db";
    private final static String DB_TABLE = "sales";
    private final static int DB_VERSION = 1;
    private SQLiteDatabase db;
    private final String preName = "MAIN_SETTING";
    private final String dataIntPreTag = "dataIPT";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private int dataInt;
    private int position = 0;
    private int[] num = {0,0,0,0,0,0,0,0,0,0,0,0,0};
    private int[] value = {200,300,100,100,150,100,100,150,100,100,150,100,150};//各メニューの価格
    private int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        Context context = getApplicationContext();
        sharedPreferences = getSharedPreferences(preName, MODE_PRIVATE);
        ;
        dataInt = sharedPreferences.getInt(dataIntPreTag, 0);
        edit = sharedPreferences.edit();
        if (dataInt == 0) {
            CustomToast.makeText(context, "初回起動です", 500).show();
            CustomToast.makeText(context, "売り上げデータを初期化します", 500).show();
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
            if (colnum == 0) db.insert(DB_TABLE, String.valueOf(0), values);

            CustomToast.makeText(context, "初期化に成功しました", 500).show();
            dataInt++;
            edit.putInt(dataIntPreTag, dataInt).apply();
        } else {
        }
        // idがgroupのRadioGroupを取得
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        // radioGroupの選択値が変更された時の処理を設定
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId){
                // checkedIdには選択された項目のidがわたってくるので、そのidのRadioButtonを取得
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                switch (checkedId){
                    case R.id.menu1:
                        position = 0;
                        break;
                    case R.id.menu2:
                        position = 1;
                        break;
                    case R.id.menu3:
                        position = 2;
                        break;
                    case R.id.menu4:
                        position = 3;
                        break;
                    case R.id.menu5:
                        position = 4;
                        break;
                    case R.id.menu6:
                        position = 5;
                        break;
                    case R.id.menu7:
                        position = 6;
                        break;
                    case R.id.menu8:
                        position = 7;
                        break;
                    case R.id.menu9:
                        position = 8;
                        break;
                    case R.id.menu10:
                        position = 9;
                        break;
                    case R.id.menu11:
                        position = 10;
                        break;
                    case R.id.menu12:
                        position = 11;
                        break;
                    case R.id.menu13:
                        position = 12;
                        break;
                }
            }
        });
    }

    public void register(View view){
        // 確認ダイアログの作成
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegisterActivity.this);
        alertDialog.setTitle("確認");
        alertDialog.setMessage("数量は伝票と一致していますか？");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cursor cursor = db.query(DB_TABLE, null, null, null, null, null, null);
                cursor.moveToFirst();
                int[] database = new int[14];
                for(int i=0; i<database.length; i++)database[i] = cursor.getInt(i);
                ContentValues values = new ContentValues();
                values.put("HotCake", num[0] + database[0]);
                values.put("WHotCake", num[1] + database[1]);
                values.put("HotCoffee", num[2] + database[2]);
                values.put("IceCoffeeS", num[3] + database[3]);
                values.put("IceCoffeeL", num[4] + database[4]);
                values.put("HotTea", num[5] + database[5]);
                values.put("IceTeaS", num[6] + database[6]);
                values.put("IceTeaL", num[7] + database[7]);
                values.put("HotGreenTea", num[8] + database[8]);
                values.put("OrangeS", num[9] + database[9]);
                values.put("OrangeL", num[10] + database[10]);
                values.put("ColaS", num[11] + database[11]);
                values.put("ColaL", num[12] + database[12]);
                values.put("Registernum", database[13] + 1);

                int colnum = db.update(DB_TABLE, values, null, null);
                if(colnum == 0)db.insert(DB_TABLE, String.valueOf(0), values);
                Context context = getApplicationContext();
                CustomToast.makeText(context , "保存に成功しました", 500).show();
                Intent intent = new Intent(getApplication(), CaliculateActivity.class);
                intent.putExtra("total",total);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.create().show();
    }

    public void clear(View view){
        TextView[] textViews = {(TextView)findViewById(R.id.num1),(TextView)findViewById(R.id.num2),(TextView)findViewById(R.id.num3),(TextView)findViewById(R.id.num4),(TextView)findViewById(R.id.num5)
                ,(TextView)findViewById(R.id.num6),(TextView)findViewById(R.id.num7),(TextView)findViewById(R.id.num8),(TextView)findViewById(R.id.num9),(TextView)findViewById(R.id.num10)
                ,(TextView)findViewById(R.id.num11),(TextView)findViewById(R.id.num12),(TextView)findViewById(R.id.num13)};
        for(int i=0;i<textViews.length;i++){
            num[i] = 0;
            textViews[i].setText(Integer.toString(num[i]));
        }
        total = 0;
        for(int i=0; i<num.length; i++){
            total+=num[i]*value[i];
        }
        TextView show = (TextView)findViewById(R.id.Total);
        show.setText(Integer.toString(total));
    }

    public void sales(View view){
        Intent intent = new Intent(getApplication(), SalesActivity.class);
        startActivity(intent);
    }

    public void minus(View view){
        //TextViewの宣言
        TextView textView;
        switch (position){
            case 0:
                textView = (TextView)findViewById(R.id.num1);
                break;
            case 1:
                textView = (TextView)findViewById(R.id.num2);
                break;
            case 2:
                textView = (TextView)findViewById(R.id.num3);
                break;
            case 3:
                textView = (TextView)findViewById(R.id.num4);
                break;
            case 4:
                textView = (TextView)findViewById(R.id.num5);
                break;
            case 5:
                textView = (TextView)findViewById(R.id.num6);
                break;
            case 6:
                textView = (TextView)findViewById(R.id.num7);
                break;
            case 7:
                textView = (TextView)findViewById(R.id.num8);
                break;
            case 8:
                textView = (TextView)findViewById(R.id.num9);
                break;
            case 9:
                textView = (TextView)findViewById(R.id.num10);
                break;
            case 10:
                textView = (TextView)findViewById(R.id.num11);
                break;
            case 11:
                textView = (TextView)findViewById(R.id.num12);
                break;
            case 12:
                textView = (TextView)findViewById(R.id.num13);
                break;
            default:
                textView = (TextView)findViewById(R.id.num1);
                position = 0;
                break;
        }
        if(num[position] != 0) {
            num[position]--;
            textView.setText(Integer.toString(num[position]));
        }
        total = 0;
        for(int i=0; i<num.length; i++){
            total+=num[i]*value[i];
        }
        TextView show = (TextView)findViewById(R.id.Total);
        show.setText(Integer.toString(total));
    }

    public void plus(View view){
        //TextViewの宣言
        TextView textView;
        switch (position){
            case 0:
                textView = (TextView)findViewById(R.id.num1);
                break;
            case 1:
                textView = (TextView)findViewById(R.id.num2);
                break;
            case 2:
                textView = (TextView)findViewById(R.id.num3);
                break;
            case 3:
                textView = (TextView)findViewById(R.id.num4);
                break;
            case 4:
                textView = (TextView)findViewById(R.id.num5);
                break;
            case 5:
                textView = (TextView)findViewById(R.id.num6);
                break;
            case 6:
                textView = (TextView)findViewById(R.id.num7);
                break;
            case 7:
                textView = (TextView)findViewById(R.id.num8);
                break;
            case 8:
                textView = (TextView)findViewById(R.id.num9);
                break;
            case 9:
                textView = (TextView)findViewById(R.id.num10);
                break;
            case 10:
                textView = (TextView)findViewById(R.id.num11);
                break;
            case 11:
                textView = (TextView)findViewById(R.id.num12);
                break;
            case 12:
                textView = (TextView)findViewById(R.id.num13);
                break;
            default:
                textView = (TextView)findViewById(R.id.num1);
                position = 0;
                break;
        }
        num[position]++;
        textView.setText(Integer.toString(num[position]));
        total = 0;
        for(int i=0; i<num.length; i++){
            total+=num[i]*value[i];
        }
        TextView show = (TextView)findViewById(R.id.Total);
        show.setText(Integer.toString(total));
    }

    public static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        //データベースの生成
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table if not exists " + DB_TABLE + "(" +
                    "HotCake integer,WHotCake integer,HotCoffee integer,IceCoffeeS integer,IceCoffeeL integer," +
                    "HotTea integer,IceTeaS integer,IceTeaL integer,HotGreenTea integer,OrangeS integer," +
                    "OrangeL integer,ColaS integer,ColaL integer,Registernum integer,Engineerstunum integer)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}