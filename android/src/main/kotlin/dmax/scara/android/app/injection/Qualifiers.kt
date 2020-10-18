package dmax.scara.android.app.injection

import org.koin.core.qualifier.StringQualifier

object Qualifiers {
    val dispatcherSimple = StringQualifier("simple dispatcher")
    val dispatcherProgressive = StringQualifier("progressive dispatcher")
    val connectorBluetooth = StringQualifier("bluetooth connector")
    val connectorMock = StringQualifier("mock connector")
}
