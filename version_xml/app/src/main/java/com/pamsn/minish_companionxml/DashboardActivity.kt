package com.pamsn.minish_companionxml

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pamsn.minish_companionxml.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_menu -> MenuFragment()
                R.id.nav_filters -> FiltersFragment()
                R.id.nav_map -> MapFragment()
                R.id.nav_inventory -> InventoryFragment()
                else -> MenuFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            true
        }
        
        // Cargar inicial
        if (savedInstanceState == null) {
            binding.bottomNav.selectedItemId = R.id.nav_menu
        }
    }
}
