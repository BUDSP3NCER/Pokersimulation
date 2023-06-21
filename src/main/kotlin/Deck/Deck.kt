package Deck

class Deck {
    private val cards: MutableList<Card> = mutableListOf()

    init {
        reset()
    }

    fun reset() {
        cards.clear()
        for (suit in Suit.values()) {
            for (rank in Rank.values()) {
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

}
