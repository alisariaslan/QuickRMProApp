package com.pakachu.quickrmpremium;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class Database extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "totals";
    private static final String COL1 = "id";
    private static final String COL2 = "hareket";
    private static final String COL3 = "agirlik";
    private static final String COL4 = "tarih";

    public Database(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT," +
                COL3 + " INTEGER," +
                COL4 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String hareket,Integer agirlik,String tarih) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, hareket);
        contentValues.put(COL3, agirlik);
        contentValues.put(COL4, tarih);
        long result = db.insert(TABLE_NAME, null, contentValues);
        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +" ORDER BY id DESC";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public void deleteLast() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+TABLE_NAME+" WHERE id = (SELECT MAX(id) FROM "+TABLE_NAME+")";
        db.execSQL(query);
    }
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
    }


//    public void deleteTarih(String tarih) {
//        SQLiteDatabase db = this.getWritableDatabase();

//        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//                + COL1 + " = '" + id + "'" +
//                " AND " + COL2 + " = '" + name + "'";

//        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//                + COL1 + " = '" + id + "'";

//        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//                + COL4 + " = '" + tarih + "'";

//        Log.d(TAG, "deleteName: query: " + query);
//        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
//        db.execSQL(query);
//    }

    //
//    /**
//     * Returns only the ID that matches the name passed in
//     * @param name
//     * @return
//     */
//    public Cursor getItemID(String name){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
//                " WHERE " + COL2 + " = '" + name + "'";
//        Cursor data = db.rawQuery(query, null);
//        return data;
//    }
//
//    /**
//     * Updates the name field
//     * @param newName
//     * @param id
//     * @param oldName
//     */
//    public void updateName(String newName, int id, String oldName){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
//                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
//                " AND " + COL2 + " = '" + oldName + "'";
//        Log.d(TAG, "updateName: query: " + query);
//        Log.d(TAG, "updateName: Setting name to " + newName);
//        db.execSQL(query);
//    }
//
//    /**
//     * Delete from database
//     * @param id
//     * @param name
//     */
//    public void deleteBoth(int id, int gram) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//                + COL2 + " = '" + id + "'" +
//                " AND " + COL3 + " = '" + gram + "'";

//                String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//                + COL2 + " = '" + id + "'";

//        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//                + COL2 + " = '" + name + "'";

//        Log.d(TAG, "deleteName: query: " + query);
//        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
//        db.execSQL(query);
//    }



//    public void deleteURUNID(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
//                + COL2 + " = '" + id + "'";
//        db.execSQL(query);
//    }



}