package org.mc.mcc

import java.io.{ByteArrayInputStream, InputStream}

object Main extends App {

    private val test =
        """
            val a = 1 + 4 * 15;
            val b = "Str";
            val c = (1 + 4) * 15;
        """

    override def main(args: Array[String]) = {
        super.main(args)

        val stream = new ByteArrayInputStream(test.getBytes("UTF-8"))
        val compiler = ModuleCompiler(stream)

        val ast = compiler.parse()
    }
}