package dmax.scara.android.dispatch

import dmax.scara.android.domain.mechanics.Joint

data class Event(
    val base: Joint? = null,
    val elbow: Joint? = null,
    val wrist: Joint? = null
)
