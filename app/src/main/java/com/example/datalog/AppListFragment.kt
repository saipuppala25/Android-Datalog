package com.example.datalog

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 * Use the [AppListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AppListFragment : Fragment() {

    private val viewModel: AppViewModel by activityViewModels()

    private lateinit var adapter: AppListAdapter

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.appList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AppListAdapter(viewModel)
        recyclerView.adapter = adapter
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

}