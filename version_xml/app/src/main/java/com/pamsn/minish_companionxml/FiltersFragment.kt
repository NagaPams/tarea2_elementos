package com.pamsn.minish_companionxml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pamsn.minish_companionxml.databinding.FragmentFiltersBinding

class FiltersFragment : Fragment() {
    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.rgMapStyle.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rbPixel) AppState.mapStyle.value = "Pixel"
            else AppState.mapStyle.value = "Satelite"
        }

        binding.sliderZoom.addOnChangeListener { _, value, _ ->
            binding.tvZoom.text = "Progreso del juego (Días transcurridos): ${value.toInt()}"
        }
        
        AppState.mapStyle.observe(viewLifecycleOwner) { style ->
            if (style == "Pixel") binding.rgMapStyle.check(R.id.rbPixel)
            else binding.rgMapStyle.check(R.id.rbSatellite)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
