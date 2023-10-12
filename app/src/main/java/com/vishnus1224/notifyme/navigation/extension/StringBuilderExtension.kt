package com.vishnus1224.notifyme.navigation.extension

import java.io.File

fun StringBuilder.appendSeparatorChar(): StringBuilder = append(File.separatorChar)

fun StringBuilder.appendOptionalArgument(
  argName: String,
  argValue: Any?,
): StringBuilder {
  return if (argValue != null) {
    append('?').append(argName).append('=').append(argValue)
  } else this
}