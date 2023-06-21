package Deck

class Card(val value: Rank, val suit: Suit) {
    override fun toString(): String {
        return "${value.symbol}${suit.symbol}"
    }
}
