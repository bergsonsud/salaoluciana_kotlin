package com.example.salaoluciana.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.salaoluciana.R
import com.example.salaoluciana.adapters.MainAdapter
import kotlinx.android.synthetic.main.home_activity.*

class HomeFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.home_activity, container, false)

    }

    override fun onStart() {
        super.onStart()
        val fragmentAdapter = MainAdapter(childFragmentManager)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)

    }
}