package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.geoquiz.ui.theme.GeoQuizTheme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuizApp()
        }
    }
}

// Модель данных для вопроса
data class Question(
    val text: String,
    val answer: Boolean
)



@Composable
fun GeoQuizApp() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            //GeoQuizScreen()
        }
    }
}

@Composable
fun GeoQuizScreen(){

    // Список вопросов и ответов
    val questions = listOf(
        Question("Canberra is the capital of Australia.", true),
        Question("The Pacific Ocean is larger than the Atlantic Ocean.", true),
        Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false),
        Question("The source of the Nile River is in Egypt.", false),
        Question("The Amazon River is the longest river in the Americas.", true),
        Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true)
    )

    // Состояния приложения
    var currentQuestionIndex by remember { mutableStateOf(0) } // Текущий вопрос
    var userAnswer by remember { mutableStateOf<Boolean?>(null) } // Ответ пользователя
    var correctAnswersCount by remember { mutableStateOf(0) } // Правильный ответ
    var showResultDialog by remember { mutableStateOf(false) } // Показывать ли вопросы (выключение после всех вопросов)
    var isQuizFinished by remember { mutableStateOf(false) } // Квиз завершён

    // Текущий вопрос
    val currentQuestion = questions[currentQuestionIndex]

    // Функция для обработки ответа пользователя
    fun handleAnswer(userAnswer: Boolean) {
        //this.userAnswer = userAnswer

        // Проверяем правильность ответа
        if (userAnswer == currentQuestion.answer) {
            correctAnswersCount++
        }

        // Проверяем, был ли это последний вопрос
        if (currentQuestionIndex == questions.size - 1) {
            isQuizFinished = true
            showResultDialog = true
        }
    }


    // Функция для перехода к следующему вопросу
    fun nextQuestion() {
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            userAnswer = null // Сбрасываем ответ для нового вопроса
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Заголовок приложения
        Text(
            text = "GeoQuiz",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // Счетчик вопросов
        Text(
            text = "Question ${currentQuestionIndex + 1} of ${questions.size}",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Текст вопроса
        Text(
            text = currentQuestion.text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Кнопки ответов (видны только если пользователь еще не ответил на текущий вопрос)
        if (userAnswer == null && !isQuizFinished) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Кнопка TRUE
                Button(
                    onClick = { handleAnswer(true) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("TRUE", fontSize = 18.sp)
                }

                // Кнопка FALSE
                Button(
                    onClick = { handleAnswer(false) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("FALSE", fontSize = 18.sp)
                }
            }
        } else {
            // Показываем результат ответа, если пользователь ответил
            if (userAnswer != null) {
                val isCorrect = userAnswer == currentQuestion.answer
                Text(
                    text = if (isCorrect) "✓ Верно!" else "✗ Неверно!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isCorrect) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.error, // смена темы в зависимости от ответа.
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Показываем правильный ответ
                Text(
                    text = "Correct answer: ${if (currentQuestion.answer) "TRUE" else "FALSE"}",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Кнопка NEXT (заблокирована и невидима после последнего вопроса)
        if (!isQuizFinished && userAnswer != null) {
            Button(
                onClick = { nextQuestion() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                enabled = currentQuestionIndex < questions.size - 1,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(
                    text = if (currentQuestionIndex < questions.size - 1) "NEXT QUESTION"
                    else "SHOW RESULTS",
                    fontSize = 18.sp
                )
            }
        }

        // Индикатор прогресса
        Text(
            text = "Correct answers: $correctAnswersCount/${questions.size}",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 32.dp)
        )
    }

    // Диалоговое окно с результатами (появляется после последнего вопроса)
    if (showResultDialog) {
        AlertDialog(
            onDismissRequest = { showResultDialog = false },
            title = {
                Text(
                    text = "Quiz Completed!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "Ваш результат: $correctAnswersCount правильных ответов из ${questions.size}",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "В процентах: ${(correctAnswersCount.toDouble() / questions.size * 100).toInt()}%",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showResultDialog = false
                        // Можно добавить сброс викторины здесь, если нужно
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }


}