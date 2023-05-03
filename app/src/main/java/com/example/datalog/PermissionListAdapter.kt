package com.example.datalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.security.Permission

class PermissionListAdapter(private val permissions: List<PermissionItem>) :
    RecyclerView.Adapter<PermissionListAdapter.PermissionViewHolder>() {

    class PermissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val packageName: TextView = itemView.findViewById(R.id.package_name)
//        val permissionName: TextView = itemView.findViewById(R.id.permission)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PermissionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.permission_item, parent, false)
        return PermissionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PermissionViewHolder, position: Int) {
        val permission = permissions[position]
        notifyDataSetChanged()
//        holder.packageName.text = permission.packageName
//        holder.permissionName.text = permission.permissionName
    }

    override fun getItemCount(): Int {
        return permissions.size
    }
}