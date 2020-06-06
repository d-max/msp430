package dmax.scara.android.dispatch

import dmax.scara.android.domain.motion.Motion

interface Dispatcher {
    fun dispatch(motion: Motion)
}
