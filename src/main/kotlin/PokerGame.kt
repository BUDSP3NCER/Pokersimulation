class PokerGame(private val players: List<Player>) {
    private val deck = Deck()
    fun playRound() {
        deck.shuffle()

        var playAccount: Int = 1
        for (player in players) {
            println("$playAccount. Seat $player")
            val playerCards = mutableListOf<Card>()
            for (i in 1..5) {
                val card = deck.dealCard()
                playerCards.add(card)
            }
            player.hand.addAll(playerCards)
            if (player is HumanPlayer) {
                println("Deine Karten: ${player.hand}")
            }
        }
        playAccount++
    }
}