import kotlin.io.readLine

class Tournament(var name: String) {
    private val kiPlayers = listOf(
        KIPlayer("Phil Hellmuth", 50000),
        KIPlayer("Doyle Brunson", 50000),
        KIPlayer("Johnny Chan", 50000),
        KIPlayer("Phil Ivey", 50000),
        KIPlayer("Johnny Moss", 50000),
        KIPlayer("Erik Seidel", 50000),
        KIPlayer("Daniel Negreanu", 50000),
        KIPlayer("Stu Ungar", 50000),
        KIPlayer("Shaun Deeb", 50000),
        KIPlayer("Jason Mercier", 50000),
        KIPlayer("Men Nguyen", 50000),
        KIPlayer("Billy Baxter", 50000),
        KIPlayer("Brian Hastings", 50000)
    )

    fun startTournament() {
        println("Bitte gib deinen Namen ein:")
        val playerName = readLine() ?: "Unknown"
        val humanPlayer = HumanPlayer(playerName, 50000)

        //println("Spielername: ${humanPlayer.name}, Chips: ${humanPlayer.chips}")

        val selectedPlayers = (kiPlayers.shuffled().take(5) + humanPlayer).shuffled()

        println("Die heutigen Teilnehmen am Turnier:")

            val pokerGame = PokerGame(selectedPlayers)
            pokerGame.playRound()
        }
    }

