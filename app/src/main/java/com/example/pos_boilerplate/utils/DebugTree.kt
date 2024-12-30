package com.example.pos_boilerplate.utils

import timber.log.Timber

class DebugTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        return "${element.fileName}:${element.lineNumber} on ${element.methodName}"
    }
}