package com.tuandi.architecture.util.initializer.log

import timber.log.Timber

class DebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return "TuanTM/(${element.fileName}:${element.lineNumber})#${element.methodName}"
    }
}