package com.himanshusingh.to_dolist

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel


class TodoViewModel(private val context: Context) : ViewModel() {
    private val dBRepository = SQLiteDBRepository(context)

    fun createData(title: String, description: String, date_time: String) {
        dBRepository.createData(title, description, date_time)
    }

    fun showData(): List<TaskData> {
        return dBRepository.showData()
    }

    fun updateData(srNo: String, title: String, description: String, date_time: String) {
        dBRepository.updateData(srNo, title, description, date_time)
    }

    fun deleteAllData(){
        dBRepository.deleteAllData()
    }

    fun displayContextMenuItem(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}