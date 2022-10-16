package com.himanshusingh.to_dolist

import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View.OnCreateContextMenuListener
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.himanshusingh.to_dolist.databinding.ActivityTodoBinding
import com.himanshusingh.to_dolist.databinding.AddTaskDailogBinding
import com.himanshusingh.to_dolist.databinding.EditTaskDailogBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class TodoActivity : AppCompatActivity(){
    private lateinit var binding: ActivityTodoBinding
    private lateinit var factory: TodoVMFactory
    private lateinit var viewModel: TodoViewModel


    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("EEE, d MMM yyyy, hh:mm aaa ")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_todo)
        factory = TodoVMFactory(this)
        viewModel = ViewModelProvider(this, factory)[TodoViewModel::class.java]
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        /*val taskList = viewModel.showData()
        val adapter = DataAdapter(taskList)
        binding.recyclerView.adapter = adapter*/
        recyclerViewShow()

        binding.btnAdd.setOnClickListener {
            val dialog = Dialog(this)
            val addBinding: AddTaskDailogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.add_task_dailog, null, false)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(addBinding.root)
            dialog.setCancelable(false)
            dialog.show()

            val window = dialog.window
            val back = ColorDrawable(Color.TRANSPARENT)
            val margin = 16
            val insert = InsetDrawable(back, margin)
            window!!.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            window.setBackgroundDrawable(insert)

            addBinding.dateTime.text =  dateFormat.format(calendar.time)
            addBinding.btnSave.setOnClickListener {

                viewModel.createData(addBinding.etTitle.text.toString(), addBinding.etNote.text.toString(), addBinding.dateTime.text.toString())
                /*val taskList1 = viewModel.showData()
                val adapter1 = DataAdapter(taskList1)
                binding.recyclerView.adapter = adapter1*/
                recyclerViewShow()
                dialog.dismiss()
            }
            addBinding.btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }
    }
        private fun recyclerViewShow(){
            val taskList = viewModel.showData()
            val adapter = DataAdapter(taskList)
            binding.recyclerView.adapter = adapter
        }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.todo_option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all) {
            viewModel.deleteAllData()
            recyclerViewShow()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onContextItemSelected(item: MenuItem, ): Boolean {
        when(item.itemId) {
            101 -> {
//                val adapter = DataAdapter(taskList)
//                val updateTask = viewModel.showData().get()

                val dialog = Dialog(this)
                val upBinding : EditTaskDailogBinding =  DataBindingUtil.inflate(LayoutInflater.from(this),R.layout.edit_task_dailog,null,false)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(upBinding.root)
                dialog.setCancelable(false)
                dialog.show()
                val window = dialog.window
                val back = ColorDrawable(Color.TRANSPARENT)
                val margin = 16
                val insert = InsetDrawable(back, margin)
                window!!.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                window.setBackgroundDrawable(insert)

                /*upBinding.etTitle.setText(updateTask.title)
                upBinding.etNote.setText(updateTask.description)
                upBinding.dateTime.text = dateFormat.format(calendar.time)*/
                upBinding.btnUpdate.setOnClickListener {
//                    viewModel.updateData(updateTask.srNo,upBinding.etTitle.text.toString(),upBinding.etNote.text.toString(), upBinding.dateTime.text.toString())
                    viewModel.displayContextMenuItem("Successfully Updated")
                    dialog.dismiss()
                }
                upBinding.btnCancel.setOnClickListener {
                    dialog.dismiss()
                }

            }
            102 -> {
                viewModel.displayContextMenuItem("Successfully Deleted")
            }
        }
        return super.onContextItemSelected(item)
    }

}