package com.pamsn.minish_companionxml

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.color.MaterialColors
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.pamsn.minish_companionxml.databinding.FragmentInventoryBinding

class InventoryFragment : Fragment() {
    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    // Lista de +15 elementos (8 técnicas + 20 objetos); se conserva al cambiar de pestaña
    private val items = inventoryItems.toMutableList()
    private val itemsAdapter = ItemsAdapter(items) { item -> showItemDetail(item) }
    private var currentTab = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Deslizar para marcar como dominado (solo en la lista de técnicas y objetos)
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
                if (currentTab == 0) super.getMovementFlags(recyclerView, viewHolder) else 0

            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val index = viewHolder.adapterPosition
                val item = items.removeAt(index)
                itemsAdapter.notifyItemRemoved(index)
                updateEmptyState()
                Snackbar.make(binding.root, "${item.name} dominado", Snackbar.LENGTH_SHORT)
                    .setAction("Deshacer") {
                        val position = index.coerceAtMost(items.size)
                        items.add(position, item)
                        itemsAdapter.notifyItemInserted(position)
                        updateEmptyState()
                    }
                    .show()
            }
        }).attachToRecyclerView(binding.recyclerView)

        // Arrastrar hacia abajo para recargar la lista completa
        binding.swipeRefresh.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                if (_binding == null) return@postDelayed
                items.clear()
                items.addAll(inventoryItems)
                itemsAdapter.notifyDataSetChanged()
                updateEmptyState()
                binding.swipeRefresh.isRefreshing = false
            }, 1000)
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) = showTab(tab.position)
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // Los corazones se actualizan al completar regiones en el Mapa
        AppState.completedRegions.observe(viewLifecycleOwner) { if (currentTab == 1) showTab(1) }

        showTab(0)
    }

    private fun showTab(tabIndex: Int) {
        currentTab = tabIndex
        binding.swipeRefresh.isEnabled = tabIndex == 0
        binding.tvHeader.visibility = if (tabIndex == 1) View.VISIBLE else View.GONE

        when (tabIndex) {
            0 -> {
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.recyclerView.adapter = itemsAdapter
            }
            1 -> {
                val completed = AppState.completedRegions.value ?: emptySet()
                val obtained = heartPieces.count { it.region.id in completed }
                binding.tvHeader.text = "Piezas obtenidas: $obtained/${heartPieces.size}"
                binding.recyclerView.layoutManager = GridLayoutManager(context, 4)
                binding.recyclerView.adapter = HeartsAdapter(heartPieces, completed) { heart -> showHeartDetail(heart, completed) }
            }
            2 -> {
                binding.recyclerView.layoutManager = LinearLayoutManager(context)
                binding.recyclerView.adapter = KinstonesAdapter()
            }
        }
        updateEmptyState()
    }

    private fun updateEmptyState() {
        binding.emptyView.visibility = if (currentTab == 0 && items.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun showDetail(title: String, body: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(title)
            .setMessage(body)
            .setPositiveButton("Cerrar", null)
            .show()
    }

    private fun showItemDetail(item: InventoryItem) =
        showDetail((if (item.category == "Técnica") "📜 " else "🎒 ") + item.name, item.howTo)

    private fun showHeartDetail(heart: HeartPiece, completed: Set<String>) {
        val status = if (heart.region.id in completed) " (región completada)" else ""
        showDetail("💖 Pieza de Corazón #${heart.number}", "${heart.region.name}$status\n\n${heart.poi.howTo}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// Pestaña 1: técnicas y objetos
class ItemsAdapter(
    private val items: List<InventoryItem>,
    private val onClick: (InventoryItem) -> Unit
) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val emoji: TextView = view.findViewById(R.id.tvEmoji)
        val title: TextView = view.findViewById(R.id.tvTitle)
        val subtitle: TextView = view.findViewById(R.id.tvSubtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_inventory, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.emoji.text = if (item.category == "Técnica") "📜" else "🎒"
        holder.title.text = item.name
        holder.subtitle.text = item.category
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = items.size
}

// Pestaña 2: cuadrícula con las 44 Piezas de Corazón
class HeartsAdapter(
    private val hearts: List<HeartPiece>,
    private val completed: Set<String>,
    private val onClick: (HeartPiece) -> Unit
) : RecyclerView.Adapter<HeartsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card: MaterialCardView = view.findViewById(R.id.cardHeart)
        val icon: TextView = view.findViewById(R.id.tvHeartIcon)
        val number: TextView = view.findViewById(R.id.tvHeartNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_heart, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val heart = hearts[position]
        val isObtained = heart.region.id in completed
        holder.icon.text = if (isObtained) "❤️" else "🤍"
        holder.number.text = "#${heart.number}"
        // Colores del tema para que se vea bien en modo claro y oscuro
        holder.card.setCardBackgroundColor(
            MaterialColors.getColor(
                holder.card,
                if (isObtained) com.google.android.material.R.attr.colorErrorContainer
                else com.google.android.material.R.attr.colorSurfaceVariant
            )
        )
        holder.card.setOnClickListener { onClick(heart) }
    }

    override fun getItemCount() = hearts.size
}

// Pestaña 3: lista con encabezados (dos tipos de vista)
class KinstonesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val groupColors = mapOf(
        "Verdes" to 0xFF4CAF50.toInt(),
        "Azules" to 0xFF2196F3.toInt(),
        "Rojas" to 0xFFF44336.toInt(),
        "Doradas" to 0xFFFFC107.toInt()
    )

    // Cada fila es un encabezado (String) o una forma de Kinstone con su grupo
    private val rows: List<Any> = kinstonesByColor.flatMap { (group, kinstones) ->
        listOf<Any>(group) + kinstones.map { group to it }
    }

    class HeaderHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.tvHeader)
    }

    class KinstoneHolder(view: View) : RecyclerView.ViewHolder(view) {
        val color: View = view.findViewById(R.id.viewColor)
        val shape: TextView = view.findViewById(R.id.tvShape)
        val fusions: TextView = view.findViewById(R.id.tvFusions)
    }

    override fun getItemViewType(position: Int) = if (rows[position] is String) TYPE_HEADER else TYPE_KINSTONE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_HEADER) {
            HeaderHolder(inflater.inflate(R.layout.item_section_header, parent, false))
        } else {
            KinstoneHolder(inflater.inflate(R.layout.item_kinstone, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderHolder -> {
                val group = rows[position] as String
                val total = kinstonesByColor.getValue(group).sumOf { it.fusions }
                holder.text.text = "$group ($total fusiones)"
            }
            is KinstoneHolder -> {
                @Suppress("UNCHECKED_CAST")
                val row = rows[position] as Pair<String, KinstoneType>
                val (group, kinstone) = row
                holder.color.background = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    setColor(groupColors.getValue(group))
                }
                holder.shape.text = kinstone.shape
                holder.fusions.text = "${kinstone.fusions} fusiones"
            }
        }
    }

    override fun getItemCount() = rows.size

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_KINSTONE = 1
    }
}
