package com.himanshusingh.to_dolist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

private const val DB_NAME = "To_Do_Data"
private const val TABLE_NAME = "Data_Table"
private const val DB_VER =  1

private const val SR_NO =  "SrNo"
private const val TITLE = "Title"
private const val DESCRIPTION = "Description"
private const val DATE_TIME = "Date_Time"


class SQLiteDBRepository(private val context: Context) {

    val createDB = "CREATE TABLE $TABLE_NAME ($SR_NO INTEGER PRIMARY KEY AUTOINCREMENT, $TITLE TEXT, $DESCRIPTION TEXT, $DATE_TIME TEXT)"
    private val dbHelper = MyDBHelper(context)
    private val sqliteDB : SQLiteDatabase = dbHelper.writableDatabase


    fun createData(title : String, description: String, date_time : String){
        val contentValues = ContentValues()
        contentValues.put(TITLE, title)
        contentValues.put(DESCRIPTION, description)
        contentValues.put(DATE_TIME, date_time)

        val id: Long = sqliteDB.insert(TABLE_NAME, null, contentValues)
        if (id>0){
            Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show()
        }
    }

    fun showData() : List<TaskData>{
        var taskDataList : MutableList<TaskData> = ArrayList()
        val colList = arrayOf(SR_NO, TITLE, DESCRIPTION, DATE_TIME)
        val cursor:Cursor = sqliteDB.query(TABLE_NAME, colList, null, null, null, null, null)
        if (cursor.moveToFirst()){
            do {
                val srNo : String = cursor.getString(0)
                val title : String = cursor.getString(1)
                val description : String = cursor.getString(2)
                val dateTime : String = cursor.getString(3)
                val taskData = TaskData(srNo,title,description,dateTime)
                taskDataList.add(taskData)
            }while (cursor.moveToNext())
        }
        return taskDataList
    }

    fun updateData(srNo : String, title : String, description: String, date_time : String){
        val contentValues = ContentValues()
        contentValues.put(TITLE, title)
        contentValues.put(DESCRIPTION, description)
        contentValues.put(DATE_TIME, date_time)

        val id: Int = sqliteDB.update(TABLE_NAME, contentValues, "$SR_NO=$srNo", null)
        if (id>0){
            Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteAllData(){
        val total = sqliteDB.delete(TABLE_NAME,null,null)
        if (total>0){
            Toast.makeText(context, "All Data Deleted", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
        }
    }

    inner class MyDBHelper (private val context: Context) :SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {
        override fun onCreate(sqliteDatabase : SQLiteDatabase?) {
            sqliteDatabase?.execSQL(createDB)
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            TODO("Not yet implemented")
        }
    }
}