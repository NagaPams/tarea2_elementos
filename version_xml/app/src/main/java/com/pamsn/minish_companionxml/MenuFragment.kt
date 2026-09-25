package com.pamsn.minish_companionxml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.pamsn.minish_companionxml.databinding.FragmentMenuBinding
import androidx.appcompat.app.AlertDialog

class MenuFragment : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.btnPrimary.setOnClickListener {
            Snackbar.make(view, "Usa el ícono de Mapa en la barra inferior para entrar.", Snackbar.LENGTH_SHORT).show()
        }
        binding.btnOutlined.setOnClickListener {
            Snackbar.make(view, "Tus ítems están en la pestaña de Inventario.", Snackbar.LENGTH_SHORT).show()
        }
        binding.btnText.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Créditos")
                .setMessage("Zelda Map Companion\nDesarrollado en Android Nativo (XML).")
                .setPositiveButton("Cerrar", null)
                .show()
        }
        binding.btnIcon.setOnClickListener {
            Toast.makeText(context, "Rumor: Hay un muro misterioso en las Colinas del Este.", Toast.LENGTH_LONG).show()
        }
        binding.btnImage.setOnClickListener {
            Snackbar.make(view, "Abriendo menú de ajustes...", Snackbar.LENGTH_SHORT).show()
        }
        binding.fabNormal.setOnClickListener {
            Snackbar.make(view, "Agregando marcador de destino...", Snackbar.LENGTH_SHORT).show()
        }
        binding.fabExtended.setOnClickListener {
            Snackbar.make(view, "Sincronizando...", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
