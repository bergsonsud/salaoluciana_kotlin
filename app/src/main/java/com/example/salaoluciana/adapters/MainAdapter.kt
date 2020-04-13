package com.example.salaoluciana.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.salaoluciana.fragments.CustomerListFragment
import com.example.salaoluciana.fragments.ProductListFragment

class MainAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> {
                CustomerListFragment()
            }
            else -> {
                return ProductListFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Clientes"
            else -> "Produtos"
        }
    }
}