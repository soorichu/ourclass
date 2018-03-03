package co.soori.ourclass;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InsertEasyActivity extends AppCompatActivity {

    private SQLiteDatabase sqliteDB;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_easy);

        isThisDatabase("Student.db");
        isThisQuery();


    }

    public void isThisDatabase(String name) {

        try {
            sqliteDB = SQLiteDatabase.openOrCreateDatabase(name, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public void isThisQuery(){
        sqliteDB.execSQL("CREATE TABLE IF NOT EXISTS ORDER_T (ID INTEGER, NAME TEXT, PHONE TEXT)");
    }

    public void insertData(int number, String name, String phone){
        sqliteDB.execSQL("INSERT OR REPLACE INTO ORDER_T (ID, NAME, PHONE) VALUES ("+number+", '"+name+"', '"+phone+"')");

    }

    public void updateName(int number, String name){
        sqliteDB.execSQL("UPDATE ORDER_T SET NAME='"+name+"' WHERE NUMBER="+number);
    }


}
