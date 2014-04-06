package org.mc.mcc

import java.io.{InputStreamReader, InputStream}
import org.mc.parser.{Ast, Parser, CupScanner}

object ModuleCompiler {
    def apply(moduleStream: InputStream) = {
        new ModuleCompiler(moduleStream)
    }
}

final class ModuleCompiler(val moduleStream: InputStream) extends Immutable {
    private val streamReader = new InputStreamReader(moduleStream)
    private val scanner = CupScanner(streamReader)
    private val parser = new Parser(scanner)

    def parse(): Ast = {
        val symbol = parser.parse()
        symbol.value.asInstanceOf[Ast]
    }
}
