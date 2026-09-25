package com.pamsn.minish_companionxml

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.pamsn.minish_companionxml.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    // Vista de cada zona táctil, para poder marcarla como completada
    private val regionViews = mutableMapOf<String, TextView>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.max = regionsList.size

        AppState.mapStyle.observe(viewLifecycleOwner) { style -> loadMap(style == "Pixel") }
        AppState.completedRegions.observe(viewLifecycleOwner) { updateCompleted(it) }
    }

    // Carga una imagen de assets (incluye /shared_assets); devuelve null si no existe
    private fun loadBitmap(path: String): Bitmap? =
        try {
            requireContext().assets.open(path).use { BitmapFactory.decodeStream(it) }
        } catch (e: Exception) {
            null
        }

    private fun dp(value: Float): Int = (value * resources.displayMetrics.density).toInt()

    private fun loadMap(isPixel: Boolean) {
        val bitmap = loadBitmap(if (isPixel) "map.jpg" else "map_satellite.webp") ?: return
        binding.imgMap.setImageBitmap(bitmap)
        binding.mapContainer.doOnLayout { layoutMap(bitmap, isPixel) }
    }

    // Ajusta el mapa al espacio disponible sin deformarlo y coloca las zonas táctiles encima
    private fun layoutMap(bitmap: Bitmap, isPixel: Boolean) {
        val container = binding.mapContainer
        val aspect = bitmap.width.toFloat() / bitmap.height
        var mapWidth = container.width.toFloat()
        var mapHeight = mapWidth / aspect
        if (mapHeight > container.height) {
            mapHeight = container.height.toFloat()
            mapWidth = mapHeight * aspect
        }
        binding.mapFrame.layoutParams = FrameLayout.LayoutParams(mapWidth.toInt(), mapHeight.toInt(), Gravity.CENTER)

        // Quitamos las zonas anteriores (el hijo 0 es la imagen)
        binding.mapFrame.removeViews(1, binding.mapFrame.childCount - 1)
        regionViews.clear()

        // Las coordenadas de las zonas solo corresponden al mapa Pixel
        if (!isPixel) return
        regionsList.forEach { reg ->
            val zone = TextView(requireContext()).apply {
                gravity = Gravity.CENTER
                textSize = 7f
                setOnClickListener {
                    Snackbar.make(binding.root, "Abriendo zona: ${reg.name}...", Snackbar.LENGTH_SHORT).show()
                    showBottomSheet(reg)
                }
            }
            val params = FrameLayout.LayoutParams((reg.w * mapWidth).toInt(), (reg.h * mapHeight).toInt()).apply {
                leftMargin = (reg.l * mapWidth).toInt()
                topMargin = (reg.t * mapHeight).toInt()
            }
            binding.mapFrame.addView(zone, params)
            regionViews[reg.id] = zone
        }
        updateCompleted(AppState.completedRegions.value ?: emptySet())
    }

    private fun updateCompleted(completed: Set<String>) {
        binding.progressBar.progress = completed.size
        val percent = completed.size * 100 / regionsList.size
        binding.tvProgress.text = "Zonas completadas: ${completed.size}/${regionsList.size} ($percent%)"

        regionsList.forEach { reg ->
            val zone = regionViews[reg.id] ?: return@forEach
            val isCompleted = reg.id in completed
            zone.text = (if (isCompleted) "✅ " else "") + reg.pois.joinToString(" ") { it.emoji }
            zone.background = GradientDrawable().apply {
                setColor(if (isCompleted) Color.argb(85, 0, 200, 83) else Color.TRANSPARENT)
                setStroke(dp(1f), Color.argb(51, 255, 255, 255))
            }
        }
    }

    private fun showPoi(poi: Poi) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("${poi.emoji} ${poi.title}")
            .setMessage(poi.howTo)
            .setPositiveButton("Cerrar", null)
            .show()
    }

    private fun showBottomSheet(region: Region) {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_region, null)

        view.findViewById<TextView>(R.id.tvRegionTitle).text = region.name
        view.findViewById<TextView>(R.id.tvPoiCount).text = "⭐ ${region.pois.size}"
        view.findViewById<TextView>(R.id.tvRegionSummary).text =
            "Explora esta zona para buscar corazones, hadas y secretos. En esta región hay:\n" +
                region.pois.joinToString(" ") { it.emoji }

        // Mapa detallado con los emojis encima
        val detailFrame = view.findViewById<PoiMapLayout>(R.id.detailFrame)
        val detailBitmap = region.img?.let { loadBitmap(it) }
        if (detailBitmap == null) {
            view.findViewById<TextView>(R.id.tvDetailHint).text = "Mapa detallado no disponible"
            detailFrame.visibility = View.GONE
        } else {
            view.findViewById<ImageView>(R.id.imgDetail).setImageBitmap(detailBitmap)
            detailFrame.imageRatio = region.imgHeight.toFloat() / region.imgWidth

            val markerSize = dp(26f)
            region.pois.forEach { poi ->
                val marker = TextView(requireContext()).apply {
                    text = poi.emoji
                    textSize = 14f
                    gravity = Gravity.CENTER
                    elevation = dp(3f).toFloat()
                    background = GradientDrawable().apply {
                        shape = GradientDrawable.OVAL
                        setColor(Color.argb(217, 255, 255, 255))
                    }
                    // PoiMapLayout centra el marcador en esta posición relativa del mapa
                    tag = PoiMapLayout.Position(poi.x, poi.y)
                    setOnClickListener { showPoi(poi) }
                }
                detailFrame.addView(marker, FrameLayout.LayoutParams(markerSize, markerSize))
            }
        }

        // Lista con cómo conseguir cada cosa
        val poiList = view.findViewById<LinearLayout>(R.id.poiList)
        region.pois.forEach { poi -> poiList.addView(buildPoiItem(poi)) }

        view.findViewById<Button>(R.id.btnCompleteRegion).setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Confirmar Exploración")
                .setMessage("¿Deseas marcar ${region.name} como explorado al 100%?")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Aceptar") { _, _ ->
                    // Se asigna un Set nuevo para que el LiveData avise a los observadores
                    AppState.completedRegions.value = (AppState.completedRegions.value ?: emptySet()) + region.id
                    dialog.dismiss()
                }
                .show()
        }

        dialog.setContentView(view)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        dialog.show()
    }

    private fun buildPoiItem(poi: Poi): View {
        val context = requireContext()
        return LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, dp(8f), 0, dp(8f))
            setOnClickListener { showPoi(poi) }

            addView(TextView(context).apply {
                text = poi.emoji
                textSize = 24f
                setPadding(0, 0, dp(12f), 0)
            })
            addView(LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(TextView(context).apply {
                    text = poi.title
                    setTypeface(typeface, Typeface.BOLD)
                })
                addView(TextView(context).apply { text = poi.howTo })
            }, LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        regionViews.clear()
        _binding = null
    }
}
