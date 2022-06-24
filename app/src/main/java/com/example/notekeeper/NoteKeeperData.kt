package com.example.notekeeper

class CourseInfo(val courseId: String, val courseTitle: String) {
    override fun toString(): String {
        return courseTitle
    }
}

class NoteInfo(var courseInfo: CourseInfo, var title: String, var text: String)
