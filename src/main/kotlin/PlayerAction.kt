enum class PlayerAction {
    CHECK, FOLD, CALL, RAISE, ALL_IN, BET;

    fun random() {

    }
}

fun getPlayerAction(): PlayerAction {
    // Hier kannst du die Logik für die Eingabe der Aktion eines Spielers implementieren
    // Dieser Code dient als Platzhalter und gibt eine zufällige Aktion zurück
    val randomAction = PlayerAction.values().toList().shuffled().first()
    return randomAction
}