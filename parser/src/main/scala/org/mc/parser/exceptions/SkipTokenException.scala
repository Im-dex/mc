package org.mc.parser.exceptions

object SkipTokenException {
    def apply(message: String) = {
        new SkipTokenException(message, null)
    }

    def apply(message: String, exception: Throwable) = {
        new SkipTokenException(message, exception)
    }
}

final class SkipTokenException(val message: String, val exception: Throwable) extends Exception(message, exception) {
}