package com.example.calculadoratop

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private var currentInput = ""
    private var operator = ""
    private var firstOperand = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)

        // Botones numéricos
        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                onNumberClick((it as Button).text.toString())
            }
        }

        // Botones de operadores
        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperatorClick("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { onOperatorClick("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperatorClick("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperatorClick("/") }

        // Botón de igual
        findViewById<Button>(R.id.btnEquals).setOnClickListener { onEqualClick() }

        // Botón de borrar (CE)
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClearClick() }

        // Botón decimal
        findViewById<Button>(R.id.btnDecimal).setOnClickListener {
            if (!currentInput.contains(".")) {
                currentInput += "."
                tvResult.text = currentInput
            }
        }
    }

    // Manejo de entrada de números
    private fun onNumberClick(number: String) {
        currentInput += number
        tvResult.text = currentInput
    }

    // Manejo de operadores (+, -, *, /)
    private fun onOperatorClick(selectedOperator: String) {
        if (currentInput.isNotEmpty()) {
            // Actualizar el primer operando
            firstOperand = currentInput.toDoubleOrNull() ?: 0.0
            operator = selectedOperator

            // Mostrar la expresión en el TextView
            tvResult.text = "$firstOperand $operator "
            currentInput = "" // Reiniciar la entrada actual para el segundo operando
        }
    }

    // Cálculo al presionar "="
    private fun onEqualClick() {
        if (currentInput.isNotEmpty() && operator.isNotEmpty()) {
            try {
                val secondOperand = currentInput.toDouble()
                val result: Double = when (operator) {
                    "+" -> firstOperand + secondOperand
                    "-" -> firstOperand - secondOperand
                    "*" -> firstOperand * secondOperand
                    "/" -> {
                        if (secondOperand != 0.0) {
                            firstOperand / secondOperand
                        } else {
                            tvResult.text = "Error"
                            return
                        }
                    }
                    else -> 0.0
                }

                val formattedResult = if (result % 1 == 0.0) {
                    result.toInt().toString()
                } else {
                    result.toString()
                }

                tvResult.text = "$firstOperand $operator $secondOperand = $formattedResult"
                currentInput = formattedResult // Actualiza el currentInput con el resultado
                operator = "" // Reiniciar operador
            } catch (e: NumberFormatException) {
                tvResult.text = "Error"
            }
        }
    }

    // Borrar entrada
    private fun onClearClick() {
        currentInput = ""
        firstOperand = 0.0
        operator = ""
        tvResult.text = "0"
    }
}
