package com.example.bmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bmicalculator.ui.theme.BMICalculatorTheme

// Actividad principal de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configuramos el contenido utilizando Jetpack Compose y nuestro tema personalizado
        setContent {
            BMICalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BMICalculatorScreen() // Llamada a la pantalla principal
                }
            }
        }
    }
}
// Función composable que define la pantalla del calculador de BMI
@Composable
fun BMICalculatorScreen() {
    // Estados para los campos de entrada: peso y altura
    var weightInput by remember { mutableStateOf(TextFieldValue("")) }
    var heightInput by remember { mutableStateOf(TextFieldValue("")) }

    // Estados para almacenar el resultado del BMI y su interpretación
    var bmiResult by remember { mutableStateOf<Double?>(null) }
    var bmiInterpretation by remember { mutableStateOf("") }

    // Caja que define el fondo con un degradado vertical
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        // Columna principal que contiene todos los elementos de la pantalla
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de la aplicación
            Text(
                text = "BMI Calculator",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Tarjeta que agrupa los campos de entrada (peso y altura)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                // Columna interna de la tarjeta para los inputs
                Column(modifier = Modifier.padding(16.dp)) {
                    // Campo de entrada para el peso en libras
                    OutlinedTextField(
                        value = weightInput,
                        onValueChange = { weightInput = it },
                        label = { Text("Weight (lbs)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    // Campo de entrada para la altura en centímetros
                    OutlinedTextField(
                        value = heightInput,
                        onValueChange = { heightInput = it },
                        label = { Text("Height (cm)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para calcular el BMI
            Button(
                onClick = {
                    // Convertimos el texto ingresado a números
                    val weight = weightInput.text.toDoubleOrNull()
                    val height = heightInput.text.toDoubleOrNull()
                    // Validamos que el peso y la altura sean valores válidos y mayores que 0
                    if (weight != null && weight > 0 && height != null && height > 0) {
                        val bmiValue = calculateBmi(weight, height) // Calculamos el BMI
                        bmiResult = bmiValue
                        bmiInterpretation = interpretBmi(bmiValue) // Obtenemos la interpretación del BMI
                    } else {
                        bmiResult = null
                        bmiInterpretation = "Please enter valid numeric values." // Mensaje de error
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                // Texto del botón
                Text(text = "Calculate BMI", color = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.height(24.dp))


            // Caja que centra la tarjeta circular que muestra el resultado del BMI
            bmiResult?.let { bmi ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Tarjeta circular para mostrar el BMI
                    Card(
                        modifier = Modifier.size(120.dp),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        // Caja interna para centrar el texto dentro de la tarjeta
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = String.format("%.2f", bmi),
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto que muestra la interpretación del BMI
            if (bmiInterpretation.isNotEmpty()) {
                Text(
                    text = bmiInterpretation,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

// Función para calcular el BMI a partir del peso (libras) y la altura (cm)
fun calculateBmi(weightInPounds: Double, heightInCm: Double): Double {
    // Convertir el peso de libras a kilogramos
    val weightInKg = weightInPounds * 0.45359237
    // Convertir la altura de centímetros a metros
    val heightInMeters = heightInCm / 100.0
    // Fórmula del BMI: peso (kg) / altura² (m²)
    return weightInKg / (heightInMeters * heightInMeters)
}

// Función para interpretar el valor del BMI y devolver un texto descriptivo
fun interpretBmi(bmi: Double): String {
    return when {
        bmi < 18.5 -> "Underweight"
        bmi < 25 -> "Normal weight"
        bmi < 30 -> "Overweight"
        else -> "Obese"
    }
}

// Vista previa de la pantalla del BMI Calculator para el editor
@Preview(showBackground = true)
@Composable
fun BMICalculatorScreenPreview() {
    BMICalculatorTheme {
        BMICalculatorScreen()
    }
}


