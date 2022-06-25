package com.example.notekeeper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notekeeper.adapters.NotesListAdapter
import com.example.notekeeper.databinding.FragmentNotesListBinding

class NotesListFragment : Fragment() {
    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private lateinit var notesListAdapter: NotesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)

        setUpRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_NotesListFragment_to_AddNoteFragment)
        }
    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.notesListRecyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(binding.notesListRecyclerView.context, layoutManager.orientation)
        binding.notesListRecyclerView.addItemDecoration(dividerItemDecoration)
        notesListAdapter = NotesListAdapter(DataManager)
        binding.notesListRecyclerView.adapter = notesListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
