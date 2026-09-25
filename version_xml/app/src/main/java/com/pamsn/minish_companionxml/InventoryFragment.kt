package com.pamsn.minish_companionxml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.pamsn.minish_companionxml.databinding.FragmentInventoryBinding

class InventoryFragment : Fragment() {
    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupList(0)
        
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) { setupList(tab.position) }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setupList(tabIndex: Int) {
        when (tabIndex) {
            0 -> {
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.recyclerView.adapter = SimpleAdapter(listOf("Ataque giratorio", "Rompe-rocas", "Estocada descendente", "Poción azul", "Escudo espejo"))
            }
            1 -> {
                binding.recyclerView.layoutManager = GridLayoutManager(context, 4)
                val hearts = List(44) { "Corazón #${it + 1}" }
                binding.recyclerView.adapter = SimpleAdapter(hearts)
            }
            2 -> {
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.recyclerView.adapter = SimpleAdapter(listOf("ENCABEZADO: Verdes", "Triangular", "Cuadrada", "ENCABEZADO: Azules", "Gota", "L"))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class SimpleAdapter(private val items: List<String>) : RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.tvItem)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_generic, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position]
        if (items[position].startsWith("ENCABEZADO:")) {
            holder.textView.setBackgroundColor(0xFFFFF8E1.toInt())
            holder.textView.setTextColor(0xFFFF8F00.toInt())
        } else {
            holder.textView.setBackgroundColor(0x00000000.toInt())
            holder.textView.setTextColor(0xFF000000.toInt())
        }
    }
    override fun getItemCount() = items.size
}
