package com.example.datalog

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.datalog.databinding.FragmentAppListBinding
import com.example.datalog.databinding.FragmentPermissionBinding


/**
 * A simple [Fragment] subclass.
 * Use the [PermissionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PermissionFragment : Fragment() {

    private var _binding: FragmentPermissionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppViewModel by activityViewModels()

    private lateinit var adapter: AppListAdapter

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

        var id = this.arguments?.getInt("id")

        val recyclerView: RecyclerView = binding.permissionList

        val packageManager: PackageManager = recyclerView.context.packageManager

//        val packageInfo = packageManager.getPackageInfo(viewModel.apps[id!!].packageName, PackageManager.GET_PERMISSIONS)

//        val permissions = packageInfo.requestedPermissions

//        if (permissions != null) {
//            for (permission in permissions) {
//
//            }
//        }


        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}