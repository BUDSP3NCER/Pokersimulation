class Card(val rank: Rank, val suit: Suit) {
    override fun toString(): String {
        return "${rank.symbol}${suit.symbol}"
    }
}
