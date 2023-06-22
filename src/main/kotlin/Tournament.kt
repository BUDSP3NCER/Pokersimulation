import Deck.Deck
import Player.HumanPlayer
import Player.KIPlayer

class Tournament(var name: String) {
    private val kiPlayers = listOf(
        KIPlayer("Phil Hellmuth", 10000),
        KIPlayer("Doyle Brunson", 10000),
        KIPlayer("Johnny Chan", 10000),
        KIPlayer("Phil Ivey", 10000),
        KIPlayer("Johnny Moss", 10000),
        KIPlayer("Erik Seidel", 10000),
        KIPlayer("Daniel Negreanu", 10000),
        KIPlayer("Stu Ungar", 10000),
        KIPlayer("Shaun Deeb", 10000),
        KIPlayer("Jason Mercier", 10000),
        KIPlayer("Men Nguyen", 10000),
        KIPlayer("Billy Baxter", 10000),
        KIPlayer("Brian Hastings", 10000)
    )

    fun startTournament() {
        val deck = Deck()
        deck.reset()
        deck.shuffle()
        println("Bitte gib deinen Namen ein:")
        val playerName = readln()
        val humanPlayer = HumanPlayer(playerName, 10000)
        val selectedPlayers = mutableListOf(humanPlayer) + kiPlayers.shuffled().take(1)

        println(
            """Die heutigen Teilnehmen am Turnier:
        ⎛¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯⎞
        |  ⎛¯⎞ ⎛¯¯⎞ |/ |̅ ̅̅̅  ⎛¯⎞  |\/| ⎛̅ ⎞ ̅ ||̅  ⎛̅ ̅̅̅  |  |    |
        |  |/  |  | |\ |-- |\   |  | |-|  ||  |   |--|    |
        |  |   ⎝__⎠ | \|__ | \  |  | | |  ||  ⎩__ |  |    |
        ⎝_________________________________________________⎠
        """
        )

        val pokerGame = PokerGame(selectedPlayers)

        val anteAmount = 50
        pokerGame.playRound(anteAmount)
    }
}

