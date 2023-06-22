package Player

import Deck.Card

open class Player(val name: String, var chips: Int) {
    companion object {
        const val MIN_BET_AMOUNT = 1
    }

    var hand: MutableList<Card> = mutableListOf()

    open fun payAnte(anteAmount: Int) {

        if (chips >= anteAmount) {
            chips -= anteAmount
        } else {
            println("Not enough chips to pay the ante.")

        }
    }

    open fun payBet(betAmount: Int) {
        if (chips >= betAmount) {
            chips -= betAmount
        } else {

            println("Not enough chips to place the bet.")

        }
    }

}
