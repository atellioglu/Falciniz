package org.tll.falciniz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abdullahtellioglu on 28/04/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FALCINIZ.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table if not exists  Magic (id integer primary key, uniqueId text, context text , read integer, author text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean insert(String text,String uniqueId,String author){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("uniqueId",uniqueId);
        cv.put("context",text);
        cv.put("read",false);
        cv.put("author",author);
        db.insert("Magic", null, cv);
        return true;
    }
    public boolean contains(String uniqueId){
        SQLiteDatabase db = getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db,"Magic","uniqueId = ?",new String[]{uniqueId});
        if(count==0)
            return false;
        return true;
    }
    public boolean update(String text,String uniqueId,boolean read,String author){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("context",text);
        cv.put("read",read ? 1:0);
        cv.put("author",author);
        db.update("Magic", cv, "uniqueId = ?", new String[]{uniqueId});
        return true;
    }
    public Magic get(String uniqueId){
        Magic magic = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Magic where uniqueId = '" + uniqueId + "'", null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(0);
            String uId = cursor.getString(1);
            String context = cursor.getString(2);
            boolean read = cursor.getInt(3)==1 ? true : false;
            String author = cursor.getString(4);
            magic = new Magic();
            magic.setId(id);
            magic.setRead(read);
            magic.setUniqueId(uId);
            magic.setContext(context);
            magic.setAuthor(author);
        }
        return magic;
    }
    public class Magic{
        int id;
        String uniqueId;
        String context;
        boolean read;
        String author;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public boolean isRead() {
            return read;
        }

        public void setRead(boolean read) {
            this.read = read;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}