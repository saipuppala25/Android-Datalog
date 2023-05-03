package com.example.datalog

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.datalog.databinding.FragmentAppListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [AppListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AppListFragment : Fragment() {
    private var _binding: FragmentAppListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppViewModel by activityViewModels()
    lateinit var packageManager: PackageManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppListBinding.inflate(inflater, container, false)
        val v = binding.root



        var recyclerView: RecyclerView = binding.appList

        packageManager = recyclerView.context.packageManager

        recyclerView.layoutManager = LinearLayoutManager(context)
        val appAdapter = AppListAdapter()

        recyclerView.adapter = appAdapter

        viewModel.allApps.observe(
            viewLifecycleOwner,
            Observer<List<AppItemStorage>> {apps ->
                apps?.let{appAdapter.setApps(it)}
            }
        )

        binding.sync.setOnClickListener {
            viewModel.refreshApps()
        }
        var likedClicked = false
        binding.likedApps.setOnClickListener {
            if (likedClicked == true) {
                appAdapter.restore()
                likedClicked = false
                binding.likedApps.text = "Liked Apps"
            }
            else {
                appAdapter.sortByLiked()
                likedClicked = true
                binding.likedApps.text = "All apps"
            }
        }
        // Inflate the layout for this fragment
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.action_bar_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.search_app)

//        adapter = MovieListAdapter(viewModel)

        searchItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
//                adapter.restore()
                return true
            }
        })

        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//                adapter.search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                adapter.restore()
//                adapter.search(newText)
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    inner class AppListAdapter ():   RecyclerView.Adapter<AppListAdapter.AppViewHolder>() {

        var apps = emptyList<AppItemStorage>()
        var fullList = emptyList<AppItemStorage>()

        internal fun setApps(apps: List<AppItemStorage>) {
            this.apps = apps.sortedBy { it.appName }
            this.fullList = apps.sortedBy { it.appName }
            notifyDataSetChanged()
        }

        fun sortByLiked() {
            restore()
            apps = apps.filter { it.liked }
            notifyDataSetChanged()
        }

        fun restore() {
            apps = fullList
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
            return AppViewHolder(view)
        }

        override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
            val app = apps[position]
            holder.appName.text = app.appName
            holder.appIcon.setImageDrawable(packageManager.getApplicationIcon
                (packageManager.getApplicationInfo(app.packageName, 0)))
            holder.packageName.text = app.packageName
            holder.itemView.setOnClickListener{
                view?.findNavController()?.navigate(R.id.action_appListFragment_to_appDetailsFragment,
                bundleOf("id" to app.id))
            }
        }

        override fun getItemCount(): Int {
            return apps.size
        }

        inner class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val appName: TextView = view.findViewById(R.id.tracker_name)
            val appIcon: ImageView = view.findViewById(R.id.poster)
            val packageName: TextView = view.findViewById((R.id.packageName))

        }

    }

}