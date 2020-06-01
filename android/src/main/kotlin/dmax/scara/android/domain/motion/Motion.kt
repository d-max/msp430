package dmax.scara.android.domain.motion

import dmax.scara.android.domain.mechanics.Joint

data class Motion(
    val base: Joint? = null,
    val elbow: Joint? = null,
    val wrist: Joint? = null
)
