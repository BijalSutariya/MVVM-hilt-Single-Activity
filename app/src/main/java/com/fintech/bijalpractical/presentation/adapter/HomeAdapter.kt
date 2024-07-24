package com.fintech.bijalpractical.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fintech.bijalpractical.data.DataModel
import com.fintech.bijalpractical.databinding.ItemDataBinding

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var mList: List<DataModel> = ArrayList()


    inner class ViewHolder(val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val binding =
            ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.binding.data = mList[position]
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun updateData(updatedDataList: List<DataModel>?) {
        if (updatedDataList != null) {
            mList = updatedDataList
        }
        notifyDataSetChanged()
    }
}