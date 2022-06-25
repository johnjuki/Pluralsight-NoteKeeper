package com.example.notekeeper.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notekeeper.DataManager
import com.example.notekeeper.databinding.NotesListViewHolderBinding

class NotesListAdapter(private val dataManager: DataManager) :
    RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {
    inner class ViewHolder(binding: NotesListViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val noteListTextView: TextView = binding.noteTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NotesListViewHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteListTextView.text = dataManager.notes[position].toString()
    }

    override fun getItemCount() = dataManager.notes.size
}
