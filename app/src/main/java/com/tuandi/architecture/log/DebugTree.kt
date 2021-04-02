package com.tuandi.architecture.log

import timber.log.Timber

class DebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return "TuanTM/(${element.fileName}:${element.lineNumber})#${element.methodName}"
    }
}