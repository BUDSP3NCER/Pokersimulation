
#                   SNGTurnier

# Spiele eine Runde Poker im Turnier gegen 1-5 KI´s

## Trainiere Deine Pokerskills gegen 1-5 Ki Pokerspieler in einem Sit´n´go

##### Features:

###### Wähle Deinen Spielernamen
###### Nimm Platz am Tisch gegen die Pokerelite
###### Gespielt wird 5 Card Poker
###### Jeder Spieler erhält 5 Karten
###### Nachdem die Spieler Platz genommen haben wird, vor jeder gespielten Hand, eine Ante (Zwangseinsatz) eingesammelt und dem Pot hinzugefügrt
###### Nun wo alle Spieler ihre Karten erhalten haben,
###### Ingame sehen wir nur unsere eigenen Karten
###### geht die erste Chack, Bet Runde los.
###### Du entscheidest Dich, ob Du checkst oder bettest // zum jetzigen Zeitpunkt Check/Call die KI all deine Einsätze.
###### Nach der ersten Setz Runde kannst du entscheiden oder du keine, 1,2,3,4 oder alle karten tauschen möchtest.
###### Danach beginnt die 2. Check, Bet Runde los.
###### Du entscheidest dich, ob du checkst oder bettest // zum jetzigen Zeitpunkt Check/Call die KI all deine Einsätze.
###### Nun kommt es zum großen Showdown. welcher Spieler hat die beste Hand und streicht den Pot ein?
###### dem Gewinner wird der Pot auf seinen Stack hinzuaddiert.
###### Spieler die keine Chips mehr haben müssen an die Rails und dürfen nur noch zuschauen.
###### Alle anderen spielen weiter.
###### Auf zur nächsten Hand. Gleicher Ablauf wie Zeile 13 bis 26.
###### das ganz wiederholt sich nun so lange, bis nur noch 1 Spieler übrig bleibt und das Sit´n´go gewonnen hat.

Einstellungsmöglichkeiten
+ Anzahl der KI Spieler 1-5 
+ Anzahl der Startchips 
+ Höhe der Ante

TODO´s
+ Fehler die ich nicht mehr abfangen konnte:
+ wenn KI Spieler weniger Chips hat als die Bet vom HumanPlayer ist, 
+ zeigt er zwar an das er Allin ist. 
+ Callt dann dennoch mit der vollen Bet.
+ Im späteren Spielverlauf hängt sich das Spiel in einer Dauerschleife 
  mit Bet / Call bei KIPlayer auf

### Welcher Abschnitt hat mich viel Energie gekostet.
+ Das ganze Projekt hat mich viel Energie gekostet! Wenn auch gleich lehrreich!

### Welcher Abschnitt war überraschend (einfach/schwierig)
+ Die Logik der Wertigkeit im Pokern zu implementieren

### Auf welchen Abschnitt bin ich besonders Stolz

### Den Spielablauf in die richtige reihenfolge zu bringen
+ siehe PokerGame.kt

### Was macht mein Programm
+ Pokersimulation Spiel gegen 1-5 KI Spieler

### Wie macht mein Programm das ganze
+ siehe Features

### Was ist mein Kernidee
+ Pokern
+ spielen gegen Freunde oder KI

Wie könnte ich das ganze ausbauen
+ Allin Situation mit verschiedenen Sidepot(s)
+ eine andere Pokervariante hinzufügen (Omaha, Holdem, Stud)
+ 2 reale Spieler gegeneinander mit KI Spielern oder ohne
+ KI Spieler die nicht nur reagieren ( nur Check wenn Human check oder nur bet wenn Human bet)
  zum Beispiel erhöht(raist) die KI meine Bet oder foldet
+ KI Spieler tauscht ebenfalls sinnvoll oder random Karten