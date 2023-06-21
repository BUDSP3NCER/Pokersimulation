package Deck

class Hand(val cards: List<Card>) {
    val rank: HandRank = calculateRank()

    private fun calculateRank(): HandRank {
        val sortedCards = cards.sortedByDescending { it.value }

        if (isRoyalFlush(sortedCards)) {
            return HandRank.ROYAL_FLUSH
        }

        if (isStraightFlush(sortedCards)) {
            return HandRank.STRAIGHT_FLUSH
        }

        if (isFourOfAKind(sortedCards)) {
            return HandRank.FOUR_OF_A_KIND
        }

        if (isFullHouse(sortedCards)) {
            return HandRank.FULL_HOUSE
        }

        if (isFlush(sortedCards)) {
            return HandRank.FLUSH
        }

        if (isStraight(sortedCards)) {
            return HandRank.STRAIGHT
        }

        if (isThreeOfAKind(sortedCards)) {
            return HandRank.THREE_OF_A_KIND
        }

        if (isTwoPair(sortedCards)) {
            return HandRank.TWO_PAIR
        }

        if (isOnePair(sortedCards)) {
            return HandRank.ONE_PAIR
        }

        return HandRank.HIGH_CARD
    }


    override fun toString(): String {
        return cards.joinToString(", ")
    }

    private fun isRoyalFlush(sortedCards: List<Card>): Boolean {
        val suits = sortedCards.map { it.suit }.toSet()
        val royalFlushValues = setOf(CardValue.TEN, CardValue.JACK, CardValue.QUEEN, CardValue.KING, CardValue.ACE)
        return suits.size == 1 && sortedCards.map { it.value } == royalFlushValues
    }

    private fun isStraightFlush(sortedCards: List<Card>): Boolean {
        val suits = sortedCards.map { it.suit }.toSet()
        val values = sortedCards.map { it.value }
        return suits.size == 1 && isConsecutive(values)
    }

    private fun isFourOfAKind(sortedCards: List<Card>): Boolean {
        val cardCounts = sortedCards.groupingBy { it.value }.eachCount()
        return cardCounts.containsValue(4)
    }

    private fun isFullHouse(sortedCards: List<Card>): Boolean {
        val cardCounts = sortedCards.groupingBy { it.value }.eachCount()
        return cardCounts.containsValue(3) && cardCounts.containsValue(2)
    }

    private fun isFlush(sortedCards: List<Card>): Boolean {
        val suits = sortedCards.map { it.value }.toSet()
        return suits.size == 1
    }

    private fun isStraight(sortedCards: List<Card>): Boolean {
        val values = sortedCards.map { it.value }
        return isConsecutive(values)
    }

    private fun isThreeOfAKind(sortedCards: List<Card>): Boolean {
        val cardCounts = sortedCards.groupingBy { it.value }.eachCount()
        return cardCounts.containsValue(3)
    }

    private fun isTwoPair(sortedCards: List<Card>): Boolean {
        val cardCounts = sortedCards.groupingBy { it.value }.eachCount()
        val pairCount = cardCounts.count { it.value == 2 }
        return pairCount == 2
    }

    private fun isOnePair(sortedCards: List<Card>): Boolean {
        val cardCounts = sortedCards.groupingBy { it.value }.eachCount()
        return cardCounts.containsValue(2)
    }

    private fun isConsecutive(values: List<Rank>): Boolean {
        for (i in 0 until values.size - 1) {
            if (values[i].ordinal != values[i + 1].ordinal - 1) {
                return false
            }
        }
        return true

    }
}
