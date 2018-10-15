package com.edgardo.a00813103_androidkotlin_calculadora

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var partialResult: MutableList<String> = arrayListOf()
    var operation: MutableList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_clean.setOnClickListener { clickFunction(it) }
        button_cleanAll.setOnClickListener { clickFunction(it) }
        button_plus.setOnClickListener { clickFunction(it) }
        button_minus.setOnClickListener { clickFunction(it) }
        button_mult.setOnClickListener { clickFunction(it) }
        button_divide.setOnClickListener { clickFunction(it) }
        button_equal.setOnClickListener { clickFunction(it) }
        button_change_sing.setOnClickListener { clickFunction(it) }
        button_0.setOnClickListener { clickNumber(it) }
        button_1.setOnClickListener { clickNumber(it) }
        button_2.setOnClickListener { clickNumber(it) }
        button_3.setOnClickListener { clickNumber(it) }
        button_4.setOnClickListener { clickNumber(it) }
        button_5.setOnClickListener { clickNumber(it) }
        button_6.setOnClickListener { clickNumber(it) }
        button_7.setOnClickListener { clickNumber(it) }
        button_8.setOnClickListener { clickNumber(it) }
        button_9.setOnClickListener { clickNumber(it) }
    }


    private fun extractString(items: List<String>, connect: String = ""): String {
        if (items.isEmpty()) return ""
        return items.reduce { acc, s -> acc + connect + s }

    }

    private fun updateScreen(updatString: String) {
        val calculationString = extractString(operation, "")
        text_operations.text = calculationString

        text_resultado.text = updatString
    }

    private fun evaluateOperation() {

        var sing = ""
        var auxResult: Long = 0

        if (operation.size.equals(1)) {
            text_resultado.text = operation[0]
        } else {

            operation.forEach { value ->


                when {
                    value.equals("+")
                            || value.equals("-")
                            || value.equals("X")
                            || value.equals("/") -> {
                        sing = value
                    }

                    sing.equals("+") -> {
                        if (validateNumber(value)) auxResult += value.toLong()
                    }
                    sing.equals("-") -> {
                        if (validateNumber(value)) auxResult -= value.toLong()
                    }
                    sing.equals("X") -> {
                        if (validateNumber(value)) auxResult *= value.toLong()
                    }
                    sing.equals("/") -> {

                        if (validateNumber(value)) {
                            if (value.toInt() == 0) {
                                partialResult.clear()
                                operation.clear()
                                text_resultado.text = "Division entre 0, Error!"
                                return
                            } else {
                                auxResult /= value.toLong()
                            }
                        }
                    }
                    else -> {
                        auxResult = value.toLong()
                    }

                }
            }
            partialResult.clear()
            operation.clear()

            text_resultado.text = auxResult.toString()


        }

    }

    fun validateNumber(s: String): Boolean {

        if ((!s.equals("+") || !s.equals("-") || !s.equals("X") || !s.equals("/"))
                && !s.isNullOrEmpty()) return true
        return false
    }

    private fun clickFunction(view: View) {

        val button = view as Button
        val buttonValue = button.text.toString()


        when (view.id) {
            R.id.button_clean -> {
                partialResult.clear()
                text_resultado.text = ""

            }
            R.id.button_cleanAll -> {
                partialResult.clear()
                operation.clear()
                updateScreen("")
            }
            R.id.button_equal -> {
                //updateScreen(partialResult[])
                if (!operation.isEmpty()) {
                    operation.add(extractString(partialResult))
                    updateScreen(extractString(partialResult))
                    evaluateOperation()
                }

            }
            R.id.button_change_sing -> {
                var aux = extractString(partialResult)
                if (validateNumber(aux)) {
                    val newNumbers = ((-1) * aux.toLong()).toString()

                    partialResult.clear()
                    partialResult.add(newNumbers)
                    text_resultado.text = newNumbers
                    updateScreen(newNumbers)
                }

            }

            else -> {

                if (!partialResult.isEmpty()) {

                    operation.add(extractString(partialResult))

                    partialResult.clear()
                    operation.add(buttonValue)

                    updateScreen(buttonValue)
                }
            }
        }


    }

    private fun clickNumber(view: View) {

        val button = view as Button
        val buttonValue = button.text.toString()

        partialResult.add(buttonValue)

        val numbers = extractString(partialResult)
        updateScreen(numbers)


    }
}
