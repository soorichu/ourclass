package co.soori.ourclass.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.List;

/**
 * Created by Soo-Young on 2018-02-25.
 */

public class StudentDB {

    SQLiteDatabase sqliteDB;
    String dbname = "student.db";
    String tablename = "MY_STUDENT_TABLE";

    public StudentDB(){
        createDatabase(this.dbname);
    }

    public StudentDB(String dbname){
        createDatabase(dbname);
    }

    public void createDatabase(String dbname) {

        try {
            sqliteDB = SQLiteDatabase.openOrCreateDatabase(dbname, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public void createTable(){
        createTable(this.tablename);
    }

    public void createTable(String tablename){

        String query = "CREATE TABLE IF NOT EXISTS" + tablename +" (";

        query += " _ID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        query += " NAME TEXT, ";
        query += " PHONE TEXT, ";
        query += " IMAGE TEXT, ";
        query += " ETC1 TEXT, ";
        query += " ETC2 TEXT) ";

        sqliteDB.execSQL(query);

    }

    public void insertData(Student student){
        insertData(this.tablename, student);
    }

    public void insertData(String tablename, Student student){

        String query = "INSERT OR REPLACE INTO "+ tablename;

        query += " ( _ID, NAME, PHONE, IMAGE, ETC1, ETC2 ) VALUES ";
        query += " ( "+student.get_id()+", ";
        query += " ( '"+student.getName()+"', ";
        query += " ( '"+student.getPhone()+"', ";
        query += " ( '"+student.getImage()+"', ";
        query += " ( '"+student.getEtc1()+"', ";
        query += " ( '"+student.getEtc2()+"', ";

        sqliteDB.execSQL(query);

    }

    public void updateItem(int _id, String key, String value){
        updateItem(this.tablename, _id, key, value);
    }

    public void updateItem(String tablename, int _id, String key, String value){
        String query = "UPDATE " + tablename;
        query += " SET "+key+"='"+value+"' ";
        query += " WHERE _ID="+_id;

        sqliteDB.execSQL(query);
    }



    public void deleteStudent(Student student){

    }

}
