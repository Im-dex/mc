package org.mc.semantic.error

object FatalParseException {
    def apply(message: String, exception: Exception) = {
        new FatalParseException(message, exception)
    }

    def apply(message: String) = {
        new FatalParseException(message, null)
    }

    def apply(exception: Exception) = {
        new FatalParseException("", exception)
    }
}

class FatalParseException(message: String, exception: Exception) extends Exception(message, exception)
