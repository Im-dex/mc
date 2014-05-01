package org.mc.mcc

import java.io.{InputStreamReader, InputStream}
import org.mc.parser.{Ast, Parser, McScanner}

object ModuleCompiler {
    def apply(moduleStream: InputStream) = {
        new ModuleCompiler(moduleStream)
    }
}

final class ModuleCompiler(val moduleStream: InputStream) extends Immutable {
    private val streamReader = new InputStreamReader(moduleStream)
    private val scanner = McScanner(streamReader)
    private val parser = new Parser(scanner)

    def parse(): Ast = {
        val symbol = parser.parse()
        symbol.value.asInstanceOf[Ast]
    }
}
