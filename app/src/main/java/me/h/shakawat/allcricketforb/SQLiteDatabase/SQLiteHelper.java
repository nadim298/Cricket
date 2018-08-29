package me.h.shakawat.allcricketforb.SQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context,
                        String name,
                        SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }




    public void queryData(String sql)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }







    public void insertData(String name, String post, byte[] image)
    {
        SQLiteDatabase database = getWritableDatabase();
        ////query to insert record in database table
        String sql = "INSERT INTO RECORD VALUES (NULL, ?, ?, ?)";///where "RECORD" is table name in database we will create in MainActivity

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,name);
        statement.bindString(2,post);
        statement.bindBlob(3,image);

        statement.executeInsert();
    }






    ////upDate Data
    public void upDateData(String name, String post, byte[] image, int id)
    {

        SQLiteDatabase database = getWritableDatabase();

        ////query to update record.....
        String sql = "UPDATE RECORD SET name=?, post=?, image=? WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);

        statement.bindString(1,name);
        statement.bindString(2,post);
        statement.bindBlob(3,image);
        statement.bindDouble(4,(double)id);

        statement.execute();
        database.close();

    }







    ////delete...data from database
    public void deleteData(int id)
    {
        SQLiteDatabase database = getWritableDatabase();

        ////query to delete record using id
        String sql = "DELETE  FROM RECORD WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1,(double)id);

        statement.execute();
        database.close();
    }





    public Cursor getData(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }






    @Override
    public void onCreate(SQLiteDatabase db) {

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
