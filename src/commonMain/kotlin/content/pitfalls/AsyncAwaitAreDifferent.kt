package content.pitfalls

import dsl.SlidesBuilder
import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.seconds

fun SlidesBuilder.asyncAwaitAreDifferent() {
    //TODO: Show
}



private class UselessAsync {

    fun doMagic(): Deferred<Money> = viewModelScope.async {
        val rabbit = rotateTheHat().await()
        rabbit.exchangeForMoney(Currency.Chf).await()
    }

    private fun rotateTheHat() = viewModelScope.async {
        delay(1.seconds)
        Rabbit()
    }


    private fun Rabbit.exchangeForMoney(currency: Currency) = viewModelScope.async {
        Money(currency)
    }

    private val viewModelScope = CoroutineScope(Dispatchers.Default)
}

private class TheWayItShouldBe {

    suspend fun doMagic() {
        val rabbit = rotateTheHat()
        rabbit.exchangeForACat()
    }

    suspend fun rotateTheHat(): Rabbit {
        delay(1.seconds)
        return Rabbit()
    }


    suspend fun Rabbit.exchangeForACat(): Cat {
        delay(1.seconds)
        return Cat()
    }
}

private class Rabbit()

private enum class Currency {
    Eur, Chf
}

private class Money(currency: Currency) {

}

private class Cat {

}