package com.example.datalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AppListAdapter (private val viewModel: AppViewModel) : RecyclerView.Adapter<AppListAdapter.AppViewHolder>() {

    inner class AppViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return viewModel.apps.size
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}