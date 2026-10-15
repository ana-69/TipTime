package com.example.tiptime

//para este proyecto me he ayudado de varios tutoriales de Youtube, entre otros:250
//https://www.youtube.com/watch?v=zeYvpRo-VRQ
//Obviamente no me ha quedado igual, porque hay cosas que
//no entendía del video y recursos que ofrecía a los que no pude acceder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipTimeLayout()
                }
            }
        }
    }
}

@Composable
fun TipTimeLayout() {
    // 'remember' permite conservar el estado durante la recomposición.
    // 'mutableStateOf' hace que la IU reaccione automáticamente a los cambios de valor.
    var amountInput by remember { mutableStateOf("") }
    var tipInput by remember { mutableStateOf("") }
    // Conversión de las entradas de texto a valores numéricos seguros
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0
    // Cálculo dinámico de la propina
    val tip = calculateTip(amount, tipPercent)

    // Column organiza los elementos visuales de forma vertical
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título de la aplicación
        Text(
            text = "Tip Time",
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 16.dp)
        )
        // Campo de entrada para el importe del servicio
        EditNumberField( //Campo de entrada para el importe del servicio (Estado elevado)
            label = "Importe del servicio",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = amountInput,
            onValueChanged = { amountInput = it },
            modifier = Modifier.fillMaxWidth()
        )
        // Spacer para agregar un espacio vacío vertical entre componentes
        Spacer(modifier = Modifier.height(16.dp))

        // Campo de entrada para el porcentaje de propina
        EditNumberField(
            label = "Porcentaje de propina (%)",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = tipInput,
            onValueChanged = { tipInput = it },
            modifier = Modifier.fillMaxWidth()
        )
        // Espaciador entre los formularios y el resultado
        Spacer(modifier = Modifier.height(24.dp))

        // Texto que muestra el resultado del cálculo formateado
        Text(
            text = "Propina total: $tip",
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleLarge
        )

        // Espacio final para empujar los elementos hacia el centro
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun EditNumberField(
    label: String,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        singleLine = true,
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

// Función auxiliar sin estado que realiza la lógica de negocio (cálculo y formato de moneda)
private fun calculateTip(amount: Double, tipPercent: Double = 15.0): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)
}

// Permite visualizar la interfaz en el panel de diseño de Android Studio sin ejecutar la app
@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            TipTimeLayout()
        }
    }
}