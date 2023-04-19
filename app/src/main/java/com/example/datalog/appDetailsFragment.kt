package com.example.datalog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.datalog.databinding.FragmentAppDetailsBinding



class appDetailsFragment : Fragment() {
    private var _binding: FragmentAppDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AppViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppDetailsBinding.inflate(inflater, container, false)
        val v = binding.root
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var id = this.arguments?.getInt("id")

        binding.appName.text = viewModel.apps.get(id!!).title
        binding.appIcon.setImageDrawable(viewModel.apps.get(id!!).icon)

        val trackers = view.findViewById<Button>(R.id.trackers)

        trackers.setOnClickListener {
            Log.d("DetailsFragment", "Track button was clicked")
            findNavController().navigate(R.id.action_appDetailsFragment_to_trackersFragment)
        }
    }


}