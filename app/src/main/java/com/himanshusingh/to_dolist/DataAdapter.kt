package com.himanshusingh.to_dolist

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnCreateContextMenuListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.himanshusingh.to_dolist.databinding.AddTaskDailogBinding
import com.himanshusingh.to_dolist.databinding.LayoutItemBinding

class DataAdapter(val list: List<TaskData>) : RecyclerView.Adapter<DataAdapter.MyViewHolder>() {

    class MyViewHolder( var binding : LayoutItemBinding) : RecyclerView.ViewHolder(binding.root), OnCreateContextMenuListener{

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.setHeaderTitle("Select Any One")
            menu?.add(adapterPosition,101,1,"Edit")
            menu?.add(adapterPosition,102,2,"Delete")
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : LayoutItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val taskData: TaskData = list[position]
        holder.binding.displayTitle.text = taskData.title
        holder.binding.displayDescription.text = taskData.description
        holder.binding.displayDate.text = taskData.date_time
        holder.binding.cardListItem.setOnCreateContextMenuListener(holder)
    }

    override fun getItemCount(): Int {
        return list.size
    }


}