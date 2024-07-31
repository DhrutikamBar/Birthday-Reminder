package com.dhrutikambar.birthdayreminder.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dhrutikambar.birthdayreminder.databinding.ItemBirthdayDetailsBinding
import com.dhrutikambar.birthdayreminder.viewModel.MainViewModel

class BirthdayListAdapter(
    private val viewModel: MainViewModel,
    private val list: ArrayList<BirthdayDetail>
) :
    RecyclerView.Adapter<BirthdayListAdapter.BirthdayListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirthdayListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBirthdayDetailsBinding.inflate(layoutInflater, parent, false)
        return BirthdayListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BirthdayListViewHolder, position: Int) {
        holder.bind(list[position])
    }


    inner class BirthdayListViewHolder(val binding: ItemBirthdayDetailsBinding) :
        ViewHolder(binding.root) {

        fun bind(data: BirthdayDetail) {
            binding.tvName.setText(data.name)
            binding.tvDate.setText(data.date)
            binding.viewModel = viewModel

        }

    }
}