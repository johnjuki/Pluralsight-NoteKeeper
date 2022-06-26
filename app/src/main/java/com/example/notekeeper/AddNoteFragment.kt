package com.example.notekeeper

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.core.content.res.ResourcesCompat.getDrawable
import com.example.notekeeper.databinding.FragmentAddNoteBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class AddNoteFragment : Fragment() {
    private var _binding: FragmentAddNoteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var noteTitle: EditText
    private lateinit var noteText: EditText
    private lateinit var spinnerCourses: Spinner

    private var notePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpNote()

        getNotePosition(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_next -> {
                moveToNextNote()
                true
            }
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (notePosition >= DataManager.notes.lastIndex) {
            val menuItem = menu.findItem(R.id.action_next)
            menuItem.icon = getDrawable(resources, R.drawable.ic_baseline_block_white_24, null)
            menuItem.isEnabled = false
        }
    }

    private fun updateOptionsMenu() {
        requireActivity().invalidateOptionsMenu()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    private fun setUpNote() {
        val coursesAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            DataManager.courses.values.toList()
        )
        coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCourses = binding.spinnerCourses
        spinnerCourses.adapter = coursesAdapter

        noteTitle = binding.textNoteTitle
        noteText = binding.textNoteText
    }

    private fun getNotePosition(savedInstanceState: Bundle?) {
        val bundle = arguments
        if (bundle == null) {
            Log.e("AddNote null information", "AddNoteFragment did not receive note information")
            return
        }
        val args = AddNoteFragmentArgs.fromBundle(bundle)

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?: args.noteIndexPosition
        if (notePosition != -1) {
            (requireActivity() as MainActivity).supportActionBar?.title =
                getString(R.string.change_title)
            displayNote()
        } else {
            DataManager.notes.add(NoteInfo())
            notePosition = DataManager.notes.lastIndex
        }
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        val coursePosition = DataManager.courses.values.indexOf(note.courseInfo)
        noteTitle.setText(note.title)
        noteText.setText(note.text)
        spinnerCourses.setSelection(coursePosition)
    }

    private fun moveToNextNote() {
        ++notePosition
        displayNote()
        updateOptionsMenu()
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = noteTitle.text.toString()
        note.text = noteText.text.toString()
        note.courseInfo = spinnerCourses.selectedItem as CourseInfo
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
