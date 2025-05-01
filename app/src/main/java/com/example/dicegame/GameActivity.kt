package com.example.dicegame


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.random.Random

@Composable
fun GameActivityScreen(targetScore: Int, navController: NavController) {
    var humanScore by remember { mutableStateOf(0) }
    var computerScore by remember { mutableStateOf(0) }
    var rollsLeft by remember { mutableStateOf(3) }
    var humanDice by remember { mutableStateOf(listOf(1, 1, 1, 1, 1)) }
    var computerDice by remember { mutableStateOf(listOf(1, 1, 1, 1, 1)) }
    var roundScore by remember { mutableStateOf(0) }
    var playerRolled by remember { mutableStateOf(false) }

    // Game Over Condition
    val gameOver = humanScore >= targetScore || computerScore >= targetScore

    // Function to roll 5 dice randomly
    fun rollDice(): List<Int> {
        return List(5) { Random.nextInt(1, 7) }
    }

    fun throwDices() {
        if (!gameOver && rollsLeft > 0) {
            humanDice = rollDice()
            computerDice = rollDice()
            roundScore = humanDice.sum()
            rollsLeft--
            playerRolled = true
        }
    }

    fun scoreRound() {
        if (playerRolled) {
            humanScore += roundScore
            computerScore += computerDice.sum()
            rollsLeft = 3
            playerRolled = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Target Score: $targetScore", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Your Score: $humanScore", style = MaterialTheme.typography.bodyMedium)
            Text("Computer's Score: $computerScore", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            humanDice.forEach {
                Image(
                    painter = painterResource(id = getDiceImage(it)),
                    contentDescription = "Human Dice",
                    modifier = Modifier.size(40.dp).padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            computerDice.forEach {
                Image(
                    painter = painterResource(id = getDiceImage(it)),
                    contentDescription = "Computer Dice",
                    modifier = Modifier.size(40.dp).padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { throwDices() }, enabled = rollsLeft > 0 && !gameOver) {
            Text("Throw Dice")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { scoreRound() }, enabled = playerRolled && !gameOver) {
            Text("Score")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (gameOver) {
            Text(
                text = if (humanScore >= targetScore) "You Win!" else "You Lose",
                style = MaterialTheme.typography.headlineMedium,
                color = if (humanScore >= targetScore) Color.Green else Color.Red
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.popBackStack() }) {
                Text("Back to Home")
            }
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
