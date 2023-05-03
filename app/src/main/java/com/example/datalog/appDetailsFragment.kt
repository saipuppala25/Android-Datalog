package com.example.datalog

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.datalog.databinding.FragmentAppDetailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class appDetailsFragment : Fragment() {
    private var _binding: FragmentAppDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by activityViewModels()
    lateinit var packageManager: PackageManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppDetailsBinding.inflate(inflater, container, false)
        val v = binding.root
        packageManager = binding.appIcon.context.packageManager
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var id = this.arguments?.getInt("id")

        CoroutineScope(Dispatchers.Main).launch {
            var app = viewModel.getApp(id!!)

            binding.appName.text = app.appName
            binding.appIcon.setImageDrawable(packageManager.getApplicationIcon
                (packageManager.getApplicationInfo(app.packageName, 0)))

            val trackers = view.findViewById<Button>(R.id.trackers)
            val permissions = view.findViewById<Button>(R.id.permissions)
            val notifications = view.findViewById<Button>(R.id.notifications)
            val like = view.findViewById<Button>(R.id.likeButton)

            if (app.liked) {
                like.text = "Unlike"
            }

            if (!app.liked) {
                like.text = "Like"
            }



            trackers.setOnClickListener {
                Log.d("DetailsFragment", "Track button was clicked")
                findNavController().navigate(R.id.action_appDetailsFragment_to_trackersFragment)
            }

            permissions.setOnClickListener {
                Log.d("DetailsFragment", "Permission button was clicked")
                findNavController().navigate(R.id.action_appDetailsFragment_to_permissionFragment)
                bundleOf("id" to id)
            }

            notifications.setOnClickListener {
                Log.d("DetailsFragment", "Notification button was clicked")
                findNavController().navigate(R.id.action_appDetailsFragment_to_notificationFragment
                        ,bundleOf("id" to id))
            }

            like.setOnClickListener {
                Log.d("DetailsFragment", "Like button was clicked")

                if (app.liked) {
                    app.liked = false
                    viewModel.insert(app)
                    like.text = "Like"
                }
                else {
                    app.liked = true
                    viewModel.insert(app)
                    like.text = "Unlike"
                }
            }
        }


    }


}