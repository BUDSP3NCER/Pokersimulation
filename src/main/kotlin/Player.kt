open class Player(var name: String, var chips: Int, var action: PlayerAction?) {
    val hand: MutableList<Card> = mutableListOf()

    override fun toString(): String {
        return "$name, Chips: $chips"
    }
}
