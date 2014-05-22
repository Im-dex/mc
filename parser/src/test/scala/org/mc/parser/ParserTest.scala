package org.mc.parser

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.io.InputStreamReader

@RunWith(classOf[JUnitRunner])
final class ParserTest extends FlatSpec {
    private val resourceStream = this.getClass.getResourceAsStream("/sample.mc")
    private val reader = new InputStreamReader(resourceStream)
    private val scanner = McScanner(reader)
    private val parser = new McParser(scanner)

    val result = parser.parse()
    val r = result
}