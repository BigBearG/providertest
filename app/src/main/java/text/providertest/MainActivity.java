package text.providertest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String newId="";
    private EditText show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button add= (Button) findViewById(R.id.add_data);
        Button delete= (Button) findViewById(R.id.delete_data);
        Button query= (Button) findViewById(R.id.query_data);
        Button update= (Button) findViewById(R.id.update_data);
        show = (EditText) findViewById(R.id.et_show);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uriadd=Uri.parse("content://text.sqlitedatabase.provider/Book");
                ContentValues values = new ContentValues();
                values.put("author","；刘营海");
                values.put("price",22);
                values.put("pages",33);
                values.put("name","android");
                Uri newUri=getContentResolver().insert(uriadd,values);
                newId=newUri.getPathSegments().get(1);
                Log.d("插入",newId);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("删除",newId);
                Uri uridel=Uri.parse("content://text.sqlitedatabase.provider/Book/"+newId);
                int del=getContentResolver().delete(uridel,null,null);
                if (del==1){
                    newId= String.valueOf(Integer.parseInt(newId)-1);
                }
                Log.d("删除", String.valueOf(newId));
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse("content://text.sqlitedatabase.provider/Book");
                Cursor cursor=getContentResolver().query(uri,null,null,null,null);
                ArrayList<String>arrayList=new ArrayList<String>();
                if (cursor!=null){
                    while (cursor.moveToNext()){
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int price = cursor.getInt(cursor.getColumnIndex("price"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String shows = "author:"+author+",price:"+price+",pages:"+pages+",name:"+name;
                        arrayList.add(shows+"\n");
                    }
                }else show.setText("没有数据");
                String a=null;
                for (String s:arrayList){
                    a+=s;
                }
                show.setText(a);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri updateuri=Uri.parse("content://text.sqlitedatabase.provider/Book/"+newId);
                ContentValues values1 = new ContentValues();
                values1.put("author","；刘营海");
                values1.put("price",5435);
                values1.put("pages",44);
                values1.put("name","android");
                getContentResolver().update(updateuri,values1,null,null);
            }
        });

    }
}
