package com.example.salaoluciana.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.salaoluciana.fragments.CustomerListFragment
import com.example.salaoluciana.fragments.OrderListFragment
import com.example.salaoluciana.fragments.ProductListFragment

class MainAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> {
                CustomerListFragment()
            }
            1 -> {
                ProductListFragment()
            }
            else -> {
                return OrderListFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Clientes"
            1 -> "Produtos"
            else -> "Ordens"
        }
    }
}