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


    }


}