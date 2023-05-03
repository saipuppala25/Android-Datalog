package com.example.datalog

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datalog.databinding.FragmentPermissionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [PermissionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PermissionFragment : Fragment() {

    private var _binding: FragmentPermissionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppViewModel by activityViewModels()

    lateinit var packageManager: PackageManager

    lateinit var permissionAdapter: PermissionListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentPermissionBinding.inflate(inflater, container, false)
        val v = binding.root

        var recyclerView = binding.permissionList
        recyclerView.layoutManager = LinearLayoutManager(context)
        packageManager = recyclerView.context.packageManager
        permissionAdapter = PermissionListAdapter()
        recyclerView.adapter = permissionAdapter




        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id = this.arguments?.getInt("id")

        CoroutineScope(Dispatchers.Main).launch {
            var app = viewModel.getApp(id!!)
            val packageInfo = packageManager.getPackageInfo(app.packageName, PackageManager.GET_PERMISSIONS)
            val permissions = packageInfo.requestedPermissions

            var permissionList = emptyList<String>()

            permissionList = permissions.toList()
            if (permissions != null && permissions.isNotEmpty()) {
                permissionList = permissions.toList()
            }
            permissionAdapter.setList(permissionList)

        }
    }

    inner class PermissionListAdapter() :
        RecyclerView.Adapter<PermissionListAdapter.PermissionViewHolder>() {

        var permissions = emptyList<String>()
        inner class PermissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val permissionName: TextView = itemView.findViewById(R.id.permission)
        }

        fun setList(permissions: List<String>) {
            this.permissions = permissions
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PermissionViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.permission_item, parent, false)
            return PermissionViewHolder(view)
        }

        override fun onBindViewHolder(holder: PermissionViewHolder, position: Int) {
            val permission = permissions[position]
            holder.permissionName.text = permission
        }

        override fun getItemCount(): Int {
            return permissions.size
        }
    }

}