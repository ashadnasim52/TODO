package ashad.app.torch.flashlight.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class dbhelper extends SQLiteOpenHelper {


    private static final String DB_name="Tester";
    private static  final int DB_veresion=1;
    private static  final String DB_table="Task";
    private static  final String DB_colum="TaskName";

    public dbhelper(Context context) {
        super(context, DB_name, null, DB_veresion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query=String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL);",DB_table,DB_colum);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query=String.format("DELETE TABLE IF EXITS %S",DB_table);
        db.execSQL(query);
        onCreate(db);
    }



    public void insertNewTask(String task){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DB_colum,task);
        db.insertWithOnConflict(DB_table,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTask(String Task)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(DB_table,DB_colum+"=?",new String[]{Task});
        db.close();
    }

    public ArrayList<String>  getTasskLit()
    {
        ArrayList<String> tasklist=new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.query(DB_table,new String[]{DB_colum},null,null,null,null,null);
        while(cursor.moveToNext())
        {
            int index=cursor.getColumnIndex(DB_colum);
            tasklist.add(cursor.getString(index));
        }
        return tasklist;
    }



}
