package dmax.scara.android.domain.mechanics

data class Arm(
    val femur: Bone,
    val tibia: Bone,
    val fibula: Bone,
    val base: Joint,
    val elbow: Joint,
    val wrist: Joint
)
