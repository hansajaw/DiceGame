import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dicegame.R

@Composable
fun GameScreen(viewModel: GameViewModel, onBack: () -> Unit) {
    // Collecting state from the ViewModel
    val humanScore = viewModel.humanScore.value
    val computerScore = viewModel.computerScore.value
    val rollsLeft = viewModel.rollsLeft.value
    val humanRolls = viewModel.humanRolls.value
    val computerRolls = viewModel.computerRolls.value
    val gameOver = viewModel.gameOver.value
    val targetScore = viewModel.targetScore.value

    val winner = when {
        humanScore >= targetScore -> "You Win!"
        computerScore >= targetScore -> "You Lose"
        else -> null
    }



    var showScores by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Your Score: $humanScore", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Computer Score: $computerScore", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Your Dice:")
        DiceRow(diceValues = humanRolls)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Computer's Dice:")
        DiceRow(diceValues = computerRolls)

        Spacer(modifier = Modifier.height(8.dp))

        // Show winner if game is over
        if (winner != null) {
            Text(text = winner, style = MaterialTheme.typography.headlineLarge)
        }

        Button(onClick = { viewModel.rollDice() }, enabled = !gameOver && rollsLeft > 0) {
            Text("Throw Dice")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Score Button
        Button(onClick = { viewModel.score(isHuman = true) }, enabled = rollsLeft > 0 && !gameOver) {
            Text("Score (Human)")
        }

        Button(onClick = { viewModel.score(isHuman = false) }, enabled = rollsLeft > 0 && !gameOver) {
            Text("Score (Computer)")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Toggle for showing/hiding scores (if needed)
        Button(onClick = { showScores = !showScores }) {
            Text(if (showScores) "Hide Scores" else "Show Scores")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text("Back to Home")
        }
    }
}

@Composable
fun DiceRow(diceValues: List<Int>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        diceValues.forEach { diceValue ->
            Image(
                painter = painterResource(id = getDiceImage(diceValue)),
                contentDescription = "Dice $diceValue",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

fun getDiceImage(diceValue: Int): Int {
    return when (diceValue) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}
