package org.mc.semantic

trait Listener {
    def onError(): Unit

    def onWarning(): Unit
}
