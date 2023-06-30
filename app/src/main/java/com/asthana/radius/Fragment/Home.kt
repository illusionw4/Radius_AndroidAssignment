package com.asthana.radius.Fragment

import Adapter
import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asthana.radius.ApiService
import com.asthana.radius.R
import com.asthana.radius.databinding.FragmentHomeBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Home : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter
        val recyclerView: RecyclerView = binding.recyclerview
        // Example of using LinearLayoutManager
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        progressBar = binding.progressBar

        progressBar.visibility = View.VISIBLE

        // Implementing API using Retrofit and RxJava
        val retrofit = Retrofit.Builder()
            .baseUrl("https://my-json-server.typicode.com/iranjith4/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getApiData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                // Handle the API response here
                val facilities = response.facilities
                val exclusions = response.exclusions

//                Log.d("API", "Facilities: $facilities")
//                Log.d("API", "Exclusions: $exclusions")

                // Create an instance of the adapter
                val adapter = Adapter(requireContext(), facilities, exclusions)

                // Set the adapter to the RecyclerView
                recyclerView.adapter = adapter

                // Update the adapter's data
                adapter.setData(facilities)

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged()

                // Hide the progressBar
                progressBar.visibility = View.GONE

            }, { error ->
                // Handle the API error here
                error.printStackTrace()
                Toast.makeText(requireContext(),"Error Fetching Data Values", Toast.LENGTH_SHORT).show()
                // Hide the progressBar
                progressBar.visibility = View.GONE
            })



    }

}