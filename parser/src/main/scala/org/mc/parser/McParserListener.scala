package org.mc.parser

/**
 * Created by dex on 01.05.2014.
 */
trait McParserListener {
    def onSyntaxError(line: Int, column: Int, text: String): Unit

    def onFatalSyntaxError(line: Int, column: Int, text: String): Unit
}
