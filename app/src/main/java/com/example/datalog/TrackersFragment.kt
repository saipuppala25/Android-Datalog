package com.example.datalog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class TrackersFragment : Fragment() {

    private val viewModel: AppViewModel by activityViewModels()

    private lateinit var adapter: TrackerListAdapter

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trackers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var packageName = viewModel.packageName

        Log.d("TrackersFragment", "App package: $packageName")

        val apiKey = viewModel.apiKey

        if (apiKey.isEmpty()) {
            view.findViewById<TextView>(R.id.tracker_amount).text =
                "Warning: API key missing!"
            Log.d("TrackersFragment", "API key is missing!")
        }

        val appReportUrl = "https://reports.exodus-privacy.eu.org/api/search/$packageName"

        val appReportRequest = Request.Builder()
            .url(appReportUrl)
            .header("Authorization", "Token $apiKey")
            .header("Accept", "application/json")
            .build()

        val client = OkHttpClient()

        GlobalScope.launch(Dispatchers.IO) {
            try {


                // Gets the report (data) for the package specified
                val appReportResponse = client.newCall(appReportRequest).execute()
                val appReportJsonResponse = appReportResponse.body?.string()
                val appReportJsonObject = JSONObject(appReportJsonResponse)

                if (!appReportJsonObject.has(packageName)) {
                    Log.d("TrackersFragment", "Exodus contains no data on this package")
                    withContext(Dispatchers.Main) {
                        view.findViewById<TextView>(R.id.tracker_amount).text =
                            "No data available on this app..."
                    }
                    return@launch
                }

                val appData = appReportJsonObject.getJSONObject(packageName)
                val reportsArray = appData.getJSONArray("reports")
                val firstReport = reportsArray.getJSONObject(0)
                val trackerIds = firstReport.getJSONArray("trackers")

                // Gets the list of trackers contained within the package
                val trackersUrl = "https://reports.exodus-privacy.eu.org/api/trackers"
                val trackersRequest = Request.Builder()
                    .url(trackersUrl)
                    .header("Authorization", "Token $apiKey")
                    .header("Accept", "application/json")
                    .build()

                val trackersResponse = client.newCall(trackersRequest).execute()
                val trackersJsonResponse = trackersResponse.body?.string()
                val trackersJsonObject = JSONObject(trackersJsonResponse)
                val allTrackers = trackersJsonObject.getJSONObject("trackers")

                // Gets the name of each tracker by matching its ID
                val trackerNames = mutableListOf<String>()
                for (i in 0 until trackerIds.length()) {
                    val trackerId = trackerIds.getInt(i).toString()
                    val trackerData = allTrackers.getJSONObject(trackerId)
                    val trackerName = trackerData.getString("name")
                    trackerNames.add(trackerName)
                }
                viewModel.trackerNamesList = trackerNames

                withContext(Dispatchers.Main) {

                    var trackerAmount = trackerNames.size

                    var trackerTV = view.findViewById<TextView>(R.id.tracker_amount)

                    when (trackerAmount) {
                        0 -> trackerTV.text = "No trackers are present!"
                        1 -> trackerTV.text = "There is 1 tracker present!"
                        else -> trackerTV.text = "There are $trackerAmount trackers present!"
                    }

                    recyclerView = view.findViewById(R.id.trackerList)
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    adapter = TrackerListAdapter()
                    recyclerView.adapter = adapter

                }

            } catch (e: IOException) {
                Log.d("TrackersFragment", "Request failed: ${e.message}")
            } catch (e: JSONException) {
                Log.d("TrackersFragment", "JSON parsing failed: ${e.message}")
            }
        }
    }

    inner class TrackerListAdapter() :
        RecyclerView.Adapter<TrackerListAdapter.TrackerViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackerViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.tracker_item, parent, false)
            return TrackerViewHolder(view)
        }

        override fun onBindViewHolder(holder: TrackerViewHolder, position: Int) {
            val tracker = viewModel.trackerNamesList[position]
            holder.trackerView.text = tracker
        }

        override fun getItemCount(): Int {
            return viewModel.trackerNamesList.size
        }

        inner class TrackerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val trackerView: TextView = view.findViewById(R.id.tracker_name)

        }
    }
}