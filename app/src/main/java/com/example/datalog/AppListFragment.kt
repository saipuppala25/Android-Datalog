package com.example.datalog

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

    private lateinit var adapter: AppListAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppListBinding.inflate(inflater, container, false)
        val v = binding.root

        var recyclerView: RecyclerView = binding.appList
        val packageManager: PackageManager = recyclerView.context.packageManager
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { !it.isSystemApp }

        recyclerView.layoutManager = LinearLayoutManager(context)
        val appAdapter = AppListAdapter(apps, packageManager, viewModel)
        recyclerView.adapter = appAdapter
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

    class AppListAdapter (val apps: List<ApplicationInfo>, val packageManager: PackageManager, val viewModel: AppViewModel):   RecyclerView.Adapter<AppListAdapter.AppViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
            return AppViewHolder(view)
        }

        override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
            val app = apps[position]
            holder.appName.text = app.loadLabel(packageManager)
            holder.appIcon.setImageDrawable(app.loadIcon(packageManager))
            val appItem: AppItem = AppItem(app.loadLabel(packageManager).toString(),
            "", app.loadIcon(packageManager))
            viewModel.apps.add(appItem)
            holder.itemView.setOnClickListener{

            }
        }

        override fun getItemCount(): Int {
            return apps.size
        }

        class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val appName: TextView = view.findViewById(R.id.title)
            val appIcon: ImageView = view.findViewById(R.id.poster)

        }
    }

    val ApplicationInfo.isSystemApp: Boolean
        get() = (flags and ApplicationInfo.FLAG_SYSTEM) != 0

}