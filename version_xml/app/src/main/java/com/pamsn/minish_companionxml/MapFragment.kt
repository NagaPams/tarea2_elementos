package com.pamsn.minish_companionxml

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pamsn.minish_companionxml.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        AppState.mapStyle.observe(viewLifecycleOwner) { style ->
            val filename = if (style == "Pixel") "map.jpg" else "map_satellite.png"
            try {
                val bitmap = BitmapFactory.decodeStream(requireContext().assets.open(filename))
                binding.imgMap.setImageBitmap(bitmap)
            } catch (e: Exception) {}
        }

        // Dibujar las zonas táctiles encima
        val density = resources.displayMetrics.density
        val mapWidthPx = 1080 * density
        val mapHeightPx = 750 * density

        regionsList.forEach { reg ->
            val btn = Button(requireContext()).apply {
                alpha = 0.3f
                setBackgroundColor(0x5500FF00) // Verde semitransparente
                setOnClickListener {
                    showBottomSheet(reg)
                }
            }
            val params = FrameLayout.LayoutParams(
                (reg.w * mapWidthPx).toInt(),
                (reg.h * mapHeightPx).toInt()
            ).apply {
                leftMargin = (reg.l * mapWidthPx).toInt()
                topMargin = (reg.t * mapHeightPx).toInt()
            }
            binding.mapContainer.addView(btn, params)
        }
    }

    private fun showBottomSheet(region: Region) {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_region, null)
        
        view.findViewById<TextView>(R.id.tvRegionTitle).text = region.name
        view.findViewById<Button>(R.id.btnCompleteRegion).setOnClickListener {
            AppState.completedRegions.value?.add(region.id)
            dialog.dismiss()
        }
        
        dialog.setContentView(view)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
