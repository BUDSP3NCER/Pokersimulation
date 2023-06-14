class Deck {
    private val cards: MutableList<Card> = mutableListOf()

    init {
        for (rank in Rank.values()) {
            for (suit in Suit.values()) {
                cards.add(Card(rank, suit))
            }
        }
    }

    fun shuffle() {
        cards.shuffle()
    }

    fun dealCard(): Card {
        return cards.removeAt(0)
    }

    override fun toString(): String {
        return cards.toString()
    }


}
