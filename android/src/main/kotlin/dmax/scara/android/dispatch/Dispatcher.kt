package dmax.scara.android.dispatch

interface Dispatcher {
    suspend fun dispatch(event: Event)
}
