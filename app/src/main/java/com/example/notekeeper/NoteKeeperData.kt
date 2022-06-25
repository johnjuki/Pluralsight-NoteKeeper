package com.example.notekeeper

data class CourseInfo(val courseId: String, val courseTitle: String) {
    override fun toString(): String {
        return courseTitle
    }
}

data class NoteInfo(var courseInfo: CourseInfo, var title: String, var text: String)
