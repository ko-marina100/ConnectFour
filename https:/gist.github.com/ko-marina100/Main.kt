package connectfour

fun boardDraw(x: Int, y: Int, gameBoard: MutableList<MutableList<String>>) {
    for (i in 1..x) print(" $i")
    println()
    for (i in y - 1 downTo  0) {
        for (j in 0 until x) {
            print("\u2551${gameBoard[j][i]}")
        }
        println("\u2551")
    }
    print('\u255A')
    repeat(x - 1) {
        print("\u2550\u2569")
    }
    print("\u2550\u255D\n")
}

fun main() {
    println("Connect Four\n" +
            "First player's name:")
    val player1 = readln()
    println("Second player's name:")
    val player2 = readln()
    val textMessage = "Set the board dimensions (Rows x Columns)\n" +
            "Press Enter for default (6 x 7)"
    println(textMessage)
    var board = readln()
    board = board.replace(" ", "")
    board = board.replace("\t", "")
    while (!board.matches(Regex("[5-9][x, X][5-9]"))) {
        if (board == "") {
            board = "6x7"
            break
        } else if (board.matches(Regex("[1-4][x, X][5-9]")) ||
            board.matches(Regex("\\d+[x, X][5-9]"))) {
            println("Board rows should be from 5 to 9\n" + textMessage)
            board = readln()
        } else if (board.matches(Regex("[5-9][x, X][1-4]")) ||
            board.matches(Regex("[5-9][x, X]\\d+"))) {
            println("Board columns should be from 5 to 9\n" + textMessage)
            board = readln()
        } else {
            println("Invalid input\n" + textMessage)
            board = readln()
        }
    }

    println("Do you want to play single or multiple games? ")
    println("For a single game, input 1 or press Enter")
    println("Input a number of games:")

    var games = readln()
    while (games != "") {
        if (games.matches(Regex("[0-9]+"))) {
            if (games.toInt() <= 0) {
                println("Invalid input")
                println("Do you want to play single or multiple games? ")
                println("For a single game, input 1 or press Enter")
                println("Input a number of games:")
            } else break
        } else {
            println("Invalid input")
            println("Do you want to play single or multiple games? ")
            println("For a single game, input 1 or press Enter")
            println("Input a number of games:")
        }
        games = readln()
    }

    board = board.replace(Regex("[x, X]"), " X ")
    println("$player1 VS $player2\n$board board")

    val rows = board.first().digitToInt()
    val columns = board.last().digitToInt()

    if (games == "") games = "1"
    if (games == "1") {
        println("Single game")
    } else {
        println("Total $games games")
        println("Game #1")
    }

    var sc1 = 0
    var sc2 = 0
    for (iGame in 1..games.toInt()) {

        var gameBoard = MutableList(columns) { MutableList(rows) { " " } }

        if (iGame > 1) println("Game #$iGame")
        boardDraw(columns, rows, gameBoard)

        var turn = ""
        var step = 1
        var player = 0
        loop@ do {
            if (iGame % 2 == 1) {
                if (step % 2 == 1) {
                    println("$player1's turn: ") // o
                    player = 1
                } else {
                    println("$player2's turn: ") // *
                    player = 2
                }
            } else {
                if (step % 2 == 0) {
                    println("$player1's turn: ") // o
                    player = 1
                } else {
                    println("$player2's turn: ") // *
                    player = 2
                }
            }
            turn = readln()
            if (turn.matches(Regex("[0-9]+"))) {
                if (turn.toInt() <= 0 || turn.toInt() > columns) {
                    println("The column number is out of range (1 - $columns)")
                } else if (gameBoard[turn.toInt() - 1][rows - 1] != " ") { //
                    println("Column $turn is full")
                } else {
                    val emptyrow = gameBoard[turn.toInt() - 1].count { it != " " }
                    if (player == 1) {
                        gameBoard[turn.toInt() - 1][emptyrow] = "o"
                    } else {
                        gameBoard[turn.toInt() - 1][emptyrow] = "*"
                    }
                    boardDraw(columns, rows, gameBoard)
                    //проверка на выигрыш, если нет, то на заполненность, тогда ничья
                    for (i in 0 until rows) {
                        for (j in 0 until columns) {
                            //горизонтально
                            if (j + 3 <= columns - 1) {
                                if (gameBoard[j][i] == gameBoard[j + 1][i] &&
                                    gameBoard[j][i] == gameBoard[j + 2][i] &&
                                    gameBoard[j][i] == gameBoard[j + 3][i] &&
                                    gameBoard[j][i] != " "
                                ) {
                                    if (gameBoard[j][i] == "o") {
                                        println("Player $player1 won")
                                        println("Score")
                                        sc1 = sc1 + 2
                                        println("$player1: $sc1 $player2: $sc2")
                                    } else {
                                        println("Player $player2 won")
                                        println("Score")
                                        sc2 = sc2 + 2
                                        println("$player1: $sc1 $player2: $sc2")
                                    }
                                    if (iGame == games.toInt()) {
                                        println("Game over!")
                                        return
                                    } else {
                                        break@loop
                                    }
                                }
                            }
                            //вертикально
                            if (i + 3 <= rows - 1) {
                                if (gameBoard[j][i] == gameBoard[j][i + 1] &&
                                    gameBoard[j][i] == gameBoard[j][i + 2] &&
                                    gameBoard[j][i] == gameBoard[j][i + 3] &&
                                    gameBoard[j][i] != " "
                                ) {
                                    if (gameBoard[j][i] == "o") {
                                        println("Player $player1 won")
                                        println("Score")
                                        sc1 = sc1 + 2
                                        println("$player1: $sc1 $player2: $sc2")
                                    } else {
                                        println("Player $player2 won")
                                        println("Score")
                                        sc2 = sc2 + 2
                                        println("$player1: $sc1 $player2: $sc2")
                                    }
                                    if (iGame == games.toInt()) {
                                        println("Game over!")
                                        return
                                    } else {
                                        break@loop
                                    }
                                }
                            }
                            //диагонально 1
                            if (i + 3 <= rows - 1 && j + 3 <= columns - 1) {
                                if (gameBoard[j][i] == gameBoard[j + 1][i + 1] &&
                                    gameBoard[j][i] == gameBoard[j + 2][i + 2] &&
                                    gameBoard[j][i] == gameBoard[j + 3][i + 3] &&
                                    gameBoard[j][i] != " "
                                ) {
                                    if (gameBoard[j][i] == "o") {
                                        println("Player $player1 won")
                                        println("Score")
                                        sc1 = sc1 + 2
                                        println("$player1: $sc1 $player2: $sc2")
                                    } else {
                                        println("Player $player2 won")
                                        println("Score")
                                        sc2 = sc2 + 2
                                        println("$player1: $sc1 $player2: $sc2")
                                    }
                                    if (iGame == games.toInt()) {
                                        println("Game over!")
                                        return
                                    } else {
                                        break@loop
                                    }
                                }
                            }
                            //диагонально 2
                            if (i + 3 <= rows - 1 && j - 3 >= 0) {
                                if (gameBoard[j][i] == gameBoard[j - 1][i + 1] &&
                                    gameBoard[j][i] == gameBoard[j - 2][i + 2] &&
                                    gameBoard[j][i] == gameBoard[j - 3][i + 3] &&
                                    gameBoard[j][i] != " "
                                ) {
                                    if (gameBoard[j][i] == "o") {
                                        println("Player $player1 won")
                                        println("Score")
                                        sc1 = sc1 + 2
                                        println("$player1: $sc1 $player2: $sc2")
                                    } else {
                                        println("Player $player2 won")
                                        println("Score")
                                        sc2 = sc2 + 2
                                        println("$player1: $sc1 $player2: $sc2")
                                    }
                                    if (iGame == games.toInt()) {
                                        println("Game over!")
                                        return
                                    } else {
                                        break@loop
                                    }
                                }
                            }

                        }
                    }
                    var dr = true
                    for (j in 0 until columns) {
                        if (gameBoard[j][rows - 1] == " ") {
                            dr = false
                            break
                        }
                    }
                    if (dr) {
                        println("It is a draw")
                        println("Score")
                        sc1++
                        sc2++
                        println("$player1: $sc1 $player2: $sc2")
                        if (iGame == games.toInt()) {
                            println("Game over!")
                            return
                        } else {
                            break@loop
                        }
                    }
                    step++
                }
            } else if (turn == "end") {
                println("Game over!")
            } else {
                println("Incorrect column number")
            }
        } while (turn != "end")
    }
 }
