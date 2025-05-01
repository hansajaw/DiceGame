import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GameViewModel : ViewModel() {
    var humanScore = mutableStateOf(0)
    var computerScore = mutableStateOf(0)
    var rollsLeft = mutableStateOf(3)
    var humanRolls = mutableStateOf<List<Int>>(emptyList())
    var computerRolls = mutableStateOf<List<Int>>(emptyList())
    var gameOver = mutableStateOf(false)
    var targetScore = mutableStateOf(101)
    var humanAttempts = mutableStateOf(0)
    var computerAttempts = mutableStateOf(0)

    fun rollDice() {
        if (!gameOver.value && rollsLeft.value > 0) {
            humanRolls.value = rollFiveDice()
            computerRolls.value = rollFiveDice()
            rollsLeft.value -= 1
        }
    }

    private fun rollFiveDice(): List<Int> {
        return List(5) { Random.nextInt(1, 7) }
    }

    fun score(isHuman: Boolean) {
        val score = if (isHuman) humanRolls.value.sum() else computerRolls.value.sum()

        if (isHuman) {
            humanScore.value += score
            humanAttempts.value += 1
        } else {
            computerScore.value += score
            computerAttempts.value += 1
        }

        rollsLeft.value = 3


        checkWinner()
    }


    fun resetGame() {
        humanScore.value = 0
        computerScore.value = 0
        rollsLeft.value = 3
        humanRolls.value = emptyList()
        computerRolls.value = emptyList()
        gameOver.value = false
        humanAttempts.value = 0
        computerAttempts.value = 0
    }

    private fun checkWinner() {
        if (humanScore.value >= targetScore.value) {
            gameOver.value = true
        } else if (computerScore.value >= targetScore.value) {
            gameOver.value = true
        }
    }
}
