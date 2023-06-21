import Deck.Deck
import Player.HumanPlayer
import Player.KIPlayer

class Tournament(var name: String) {
    private val kiPlayers = listOf(
        KIPlayer("Phil Hellmuth", 1000),
        KIPlayer("Doyle Brunson", 1000),
        KIPlayer("Johnny Chan", 1000),
        KIPlayer("Phil Ivey", 1000),
        KIPlayer("Johnny Moss", 1000),
        KIPlayer("Erik Seidel", 1000),
        KIPlayer("Daniel Negreanu", 1000),
        KIPlayer("Stu Ungar", 1000),
        KIPlayer("Shaun Deeb", 1000),
        KIPlayer("Jason Mercier", 1000),
        KIPlayer("Men Nguyen", 1000),
        KIPlayer("Billy Baxter", 1000),
        KIPlayer("Brian Hastings", 1000)
    )

    fun startTournament() {
        val deck = Deck()
        deck.reset()
        deck.shuffle()
        println("Bitte gib deinen Namen ein:")
        val playerName = readln()
        val humanPlayer = HumanPlayer(playerName, 1000)
        val selectedPlayers = (kiPlayers.shuffled().take(5) + humanPlayer)

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

