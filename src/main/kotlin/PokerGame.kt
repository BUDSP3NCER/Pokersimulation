import Action.Action
import Action.BetAction
import Action.CallAction
import Action.CheckAction
import Deck.Card
import Deck.Deck
import Deck.Hand
import Player.HumanPlayer
import Player.KIPlayer
import Player.Player

class PokerGame(private val players: List<Player>) {
    private val deck = Deck()
    private var currentPlayerIndex: Int = 0
    private var currentBetAmount: Int = 0
    private var pot: Int = 0


    fun playRound(anteAmount: Int) {
        currentPlayerIndex = 0
        currentBetAmount = 0

        while (true) {

            println("\nDie Hand beginnt")

            val activePlayers = players.filter { it.chips > 0 }

            if (activePlayers.size == 1) {
                println("Spieler ${activePlayers[0].name} hat gewonnen!")
                activePlayers[0].chips += pot
                break
            }
            // Prüfen, ob Spieler ausscheiden
            val playersToRemove = activePlayers.filter { it.chips <= 0 }
            if (playersToRemove.isNotEmpty()) {
                println("Folgende Spieler scheiden aus:")
                for (player in playersToRemove) {
                    activePlayers.remove(player)
                    println("Spieler ${player.name}")
                }
                if (activePlayers.size == 1) {
                    println("Spieler ${activePlayers[0].name} hat gewonnen!")
                    activePlayers[0].chips += pot
                    break
                }
            }
            payAnte(anteAmount)
            deck.shuffle()
            dealCards()
            val humanPlayer = activePlayers.first { it is HumanPlayer } as HumanPlayer
            println("Spieler ${humanPlayer.name}, deine Hand: ${humanPlayer.hand}, Chips: ${humanPlayer.chips}")

            // Erste Setzrunde
            while (true) {
                val currentPlayer = activePlayers[currentPlayerIndex]
                if (currentPlayer !is HumanPlayer) {
                    println("Spieler ${currentPlayer.name}, Chips: ${currentPlayer.chips}")
                }

                val action = getPlayerAction(currentPlayer)

                if (action is CheckAction) {
                    currentPlayerIndex = (currentPlayerIndex + 1) % activePlayers.size

                    if (currentPlayerIndex == 0) {
                        break
                    }
                }

                if (action is BetAction) {
                    currentBetAmount = action.amount

                    // Alle KI-Spieler callen den Betrag
                    for (i in currentPlayerIndex + 1 until currentPlayerIndex + activePlayers.size) {
                        val index = i % activePlayers.size
                        val player = activePlayers[index]
                        if (player is KIPlayer) {
                            val callAmount = currentBetAmount - player.currentBetAmount
                            player.payBet(callAmount)
                            pot += callAmount
                            println("Spieler ${player.name} callt $callAmount")
                        }
                    }

                    break
                }
            }

            // Kartentausch
            if (activePlayers.any { it is HumanPlayer }) {
                val humanPlayer = activePlayers.first { it is HumanPlayer } as HumanPlayer
                val cardIndicesToDiscard = getPlayerCardIndicesToDiscard(humanPlayer)
                exchangeCards(humanPlayer, cardIndicesToDiscard)
                println("Deine finalen Karten: ${humanPlayer.hand} - Chipstand: ${humanPlayer.chips}")
            }

            // Zweite Setzrunde
            currentPlayerIndex = 0
            currentBetAmount = 0

            while (true) {
                val currentPlayer = activePlayers[currentPlayerIndex]
                if (currentPlayer !is HumanPlayer) {
                    println("Spieler ${currentPlayer.name}, Chips: ${currentPlayer.chips}")
                }

                val action = getPlayerAction(currentPlayer)

                if (action is CheckAction) {
                    currentPlayerIndex = (currentPlayerIndex + 1) % activePlayers.size

                    if (currentPlayerIndex == 0) {
                        break
                    }
                }

                if (action is BetAction) {
                    currentBetAmount = action.amount

                    // Alle KI-Spieler callen den Betrag
                    for (i in currentPlayerIndex + 1 until currentPlayerIndex + activePlayers.size) {
                        val index = i % activePlayers.size
                        val player = activePlayers[index]
                        if (player is KIPlayer) {
                            val callAmount = currentBetAmount - player.currentBetAmount
                            player.payBet(callAmount)
                            pot += callAmount
                            println("Spieler ${player.name} callt $callAmount")
                        }
                    }
                    break
                }
            }

            // Auswertung
            calculateBestHands()
            val winner = getWinner()
            if (winner != null) {
                println("Spieler ${winner.name} hat den Pot gewonnen! Pot: $pot")
                winner.chips += pot
                pot = 0
            } else {
                println("Kein Gewinner in dieser Runde. Der Pot bleibt unverändert: $pot")
            }

            // Neue Hand ausgeben
            for (player in activePlayers) {
                deck.reset()
                deck.shuffle()
                val playerCards = mutableListOf<Card>()
                for (i in 1..5) {
                    val card = deck.dealCard()
                    playerCards.add(card)
                }
                player.hand.clear()
                player.hand.addAll(playerCards)
            }
        }

    }
    private fun payAnte(anteAmount: Int) {
        for (player in players) {
            player.payAnte(anteAmount)
            pot += anteAmount

        }
    }
    private fun getWinner(): Player? {
        var bestHand: Hand? = null
        var bestPlayer: Player? = null

        for (player in players) {
            val playerHand = getBestHand(player.hand)
            if (bestHand == null || playerHand.rank.rankValue > bestHand.rank.rankValue) {
                bestHand = playerHand
                bestPlayer = player
            } else if (playerHand.rank.rankValue == bestHand.rank.rankValue) {
                val comparisonResult = compareHandsByCardValues(playerHand, bestHand)
                if (comparisonResult > 0) {
                    bestHand = playerHand
                    bestPlayer = player
                }
            }
        }

        return bestPlayer
    }

    private fun dealCards() {
        for (player in players) {
            val playerCards = mutableListOf<Card>()
            for (i in 1..5) {
                val card = deck.dealCard()
                playerCards.add(card)
            }
            player.hand.clear()
            player.hand.addAll(playerCards)
        }
    }

    private fun getPlayerAction(player: Player): Action {
        if (player is HumanPlayer) {
            println("Mögliche Aktionen: Check, Bet")
            while (true) {
                when (readlnOrNull()?.trim()?.toUpperCase()) {
                    "CHECK" -> return CheckAction()
                    "BET" -> {
                        val betAmount = getPlayerBetAmount(player)
                        currentBetAmount = betAmount
                        player.payBet(currentBetAmount)
                        pot += currentBetAmount
                        for (otherPlayer in players.filter { it != player }) {
                            if (otherPlayer.chips < currentBetAmount) {
                                val allInAmount = otherPlayer.chips
                                otherPlayer.payBet(allInAmount)
                                pot += allInAmount
                                println("Spieler ${otherPlayer.name} geht All-In mit $allInAmount")
                            }
                        }
                        return BetAction(betAmount)
                    }

                    else -> println("Ungültige Aktion. Bitte wähle Check oder Bet.")
                }
            }
        } else if (player is KIPlayer) {
            return if (currentBetAmount == 0) {
                CheckAction()
            } else {
                val callAmount = currentBetAmount - player.currentBetAmount
                player.payBet(callAmount)
                pot += callAmount
                for (otherPlayer in players.filter { it != player }) {
                    if (otherPlayer.chips < currentBetAmount) {
                        val allInAmount = otherPlayer.chips
                        otherPlayer.payBet(allInAmount)
                        pot += allInAmount
                        println("Spieler ${otherPlayer.name} geht All-In mit $allInAmount")
                    }
                }
                println("Spieler ${player.name} callt $callAmount")
                CallAction(callAmount)
            }
        } else {
            // Standardmäßig eine Action.CheckAction zurückgeben, falls keine der vorherigen Bedingungen erfüllt ist
            return CheckAction()
        }
    }

    private fun getPlayerBetAmount(player: HumanPlayer): Int {
        while (true) {
            println("Bitte gib deinen Einsatz ein:")
            val input = readLine()?.trim()
            if (input != null && input.matches(Regex("\\d+"))) {
                val betAmount = input.toInt()
                if (betAmount >= Player.MIN_BET_AMOUNT && betAmount <= player.chips) {
                    return betAmount
                } else {
                    println("Ungültiger Einsatz. Bitte wähle einen Betrag zwischen ${Player.MIN_BET_AMOUNT} und ${player.chips}.")
                }
            } else {
                println("Ungültige Eingabe. Bitte gib einen numerischen Betrag ein.")
            }
        }
    }

    private fun getPlayerCardIndicesToDiscard(player: HumanPlayer): List<Int> {
        val validIndices = listOf(0, 1, 2, 3, 4, "keine")
        val input = player.chooseCardsToDiscard()
        return input.filter { it in validIndices }
    }

    private fun exchangeCards(player: HumanPlayer, cardIndicesToDiscard: List<Int>) {
        if (cardIndicesToDiscard.contains(-1)) {
            return
        }

        val newCards = mutableListOf<Card>()
        for (index in cardIndicesToDiscard) {
            val card = deck.dealCard()
            newCards.add(card)
        }
        for ((index, cardIndex) in cardIndicesToDiscard.withIndex()) {
            player.hand[cardIndex] = newCards[index]
        }
    }

    private fun calculateBestHands() {
        var bestHand: Hand? = null
        var bestPlayer: Player? = null

        for (player in players) {
            val playerHand = getBestHand(player.hand)
            if (bestHand == null || playerHand.rank.rankValue > bestHand.rank.rankValue) {
                bestHand = playerHand
                bestPlayer = player
            } else if (playerHand.rank.rankValue == bestHand.rank.rankValue) {
                val comparisonResult = compareHandsByCardValues(playerHand, bestHand)
                if (comparisonResult > 0) {
                    bestHand = playerHand
                    bestPlayer = player
                }
            }
        }

        if (bestPlayer != null && bestHand != null) {
            println("Zusammenfassung des Showdown:")
            for (player in players) {
                val playerHand = getBestHand(player.hand)
                val chipCount = if (player == bestPlayer) player.chips + pot else player.chips
                println("Spieler ${player.name}: ${player.hand}, Chipstand: $chipCount")
            }
            bestPlayer.chips += pot
            println("Spieler ${bestPlayer.name} hat gewonnen mit: $bestHand")
            println("Pot: $pot")

            //for (player in players) {
            //   player.chips = initialChipCounts[player] ?: 0
            // }
        }
    }
    private fun <E> List<E>.remove(player: E) {

    }
    private fun getBestHand(hand: List<Card>): Hand {
        val possibleHands = generatePossibleHands(hand)
        val sortedHands = possibleHands.sortedByDescending { it.rank }
        return sortedHands.first()
    }

    private fun compareHandsByCardValues(hand1: Hand, hand2: Hand): Int {
        val cards1 = hand1.cards.sortedByDescending { it.value }
        val cards2 = hand2.cards.sortedByDescending { it.value }

        for (i in 0 until cards1.size) {
            val comparisonResult = cards1[i].value.compareTo(cards2[i].value)
            if (comparisonResult != 0) {
                return comparisonResult
            }
        }

        return 0
    }

    private fun generatePossibleHands(hand: List<Card>): List<Hand> {
        val possibleHands = mutableListOf<Hand>()

        for (i in 0 until hand.size - 4) {
            for (j in i + 1 until hand.size - 3) {
                for (k in j + 1 until hand.size - 2) {
                    for (m in k + 1 until hand.size - 1) {
                        for (n in m + 1 until hand.size) {
                            val possibleHand = Hand(listOf(hand[i], hand[j], hand[k], hand[m], hand[n]))
                            possibleHands.add(possibleHand)
                        }
                    }
                }
            }
        }

        return possibleHands
    }
}