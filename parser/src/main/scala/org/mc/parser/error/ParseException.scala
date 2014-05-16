package org.mc.parser.error

object ParseException {
    def apply(exception: Exception) = {
        new ParseException(exception)
    }
}

/**
 * Created by dex on 01.05.2014.
 */
class ParseException(val exception: Exception) extends Exception(exception)  {
}
