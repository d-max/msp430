package dmax.scara.android.domain.mechanics

data class Arm(

    val base: Joint,
    val elbow: Joint,
    val wrist: Joint,

    val femur: Bone,
    val tibia: Bone,
)
