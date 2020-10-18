package dmax.scara.android.actions

interface Actor {
    suspend operator fun invoke()
}

interface Call<in T> {
    suspend operator fun invoke(data: T)
}

interface Request<out R> {
    suspend operator fun invoke(): R
}

interface Claim<in T, out R> {
    suspend operator fun invoke(data: T): R
}
