package Player

class HumanPlayer(name: String, chips: Int) : Player(name, chips) {
    // ...

    val currentBetAmount: Int = 0

    fun chooseCardsToDiscard(): List<Int> {
        println("Welche Karten möchtest du ablegen? Gib die Indizes der Karten getrennt durch Kommas ein:")
        val input = readln()
        if (input == "keine")
            return listOf(-1)
        val indices = input.split(",").map { it.trim().toInt() }

        return indices }
}
