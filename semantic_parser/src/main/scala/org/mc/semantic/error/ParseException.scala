package org.mc.semantic.error

object ParseException {
    def apply(message: String, exception: Exception) = {
        new ParseException(message, exception)
    }

    def apply(message: String) = {
        new ParseException(message, null)
    }

    def apply(exception: Exception) = {
        new ParseException("", exception)
    }
}

class ParseException(message: String, exception: Exception) extends Exception(message, exception)