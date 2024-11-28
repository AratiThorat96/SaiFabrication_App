package com.example.saifabrication.Fragment

// HomeFragment.kt
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.saifabrication.ImageSliderAdapter
import com.example.saifabrication.R
import com.example.saifabrication.TopDesignsAdapter

class HomeFragment : Fragment() {
    private lateinit var viewPager: ViewPager
    private lateinit var recyclerViewTopDesigns: RecyclerView
    private lateinit var recyclerViewNewDesigns: RecyclerView
    private lateinit var progressBarViewPager: ProgressBar
    private lateinit var progressBarTopDesigns: ProgressBar
    private lateinit var progressBarNewDesigns: ProgressBar
    private val sliderHandler = Handler()

    private val viewPagerImages = listOf(R.drawable.banner1, R.drawable.banner3, R.drawable.winall,R.drawable.banner2) // Replace with actual images
    private val topDesignImages = listOf(R.drawable.winall, R.drawable.banner2, R.drawable.banner3,R.drawable.d3, R.drawable.d7, R.drawable.banner1,R.drawable.d7,R.drawable.d3)
    private val newDesignImages = listOf(R.drawable.d3, R.drawable.d7, R.drawable.d7,R.drawable.d3, R.drawable.d7, R.drawable.d3,R.drawable.d7,R.drawable.d3,R.drawable.d7)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewPager = view.findViewById(R.id.viewPager)
        recyclerViewTopDesigns = view.findViewById(R.id.recyclerViewTopDesigns)
        recyclerViewNewDesigns = view.findViewById(R.id.recyclerViewNewDesigns)
        progressBarViewPager = view.findViewById(R.id.progressBarViewPager)
        progressBarTopDesigns = view.findViewById(R.id.progressBarTopDesigns)
        progressBarNewDesigns = view.findViewById(R.id.progressBarNewDesigns)

        setupViewPager()
        setupRecyclerView(recyclerViewTopDesigns, TopDesignsAdapter(requireContext(), topDesignImages), progressBarTopDesigns)
        setupRecyclerView(recyclerViewNewDesigns, TopDesignsAdapter(requireContext(), newDesignImages), progressBarNewDesigns)

        return view
    }

    private fun setupViewPager() {
        val adapter = ImageSliderAdapter(requireContext(), viewPagerImages)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = viewPagerImages.size
        progressBarViewPager.visibility = View.GONE

        // Auto-scroll ViewPager every 2 seconds
        sliderHandler.postDelayed(object : Runnable {
            override fun run() {
                val nextItem = (viewPager.currentItem + 1) % viewPagerImages.size
                viewPager.setCurrentItem(nextItem, true)
                sliderHandler.postDelayed(this, 2000)
            }
        }, 2000)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>, progressBar: ProgressBar) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sliderHandler.removeCallbacksAndMessages(null) // Stop the auto-scroll when the fragment is destroyed
    }
}
