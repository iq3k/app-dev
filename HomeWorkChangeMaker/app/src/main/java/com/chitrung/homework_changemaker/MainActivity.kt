package com.chitrung.homework_changemaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arrayOf<View>(
            findViewById(R.id.btn0),
            findViewById(R.id.btn1),
            findViewById(R.id.btn2),
            findViewById(R.id.btn3),
            findViewById(R.id.btn4),
            findViewById(R.id.btn5),
            findViewById(R.id.btn6),
            findViewById(R.id.btn7),
            findViewById(R.id.btn8),
            findViewById(R.id.btn9),
            findViewById(R.id.btnClear)
        ).forEach { it.setOnClickListener(this) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("txtInput", findViewById<TextView>(R.id.txtInput).text.toString())

        elements.forEachIndexed { id, el ->
            outState.putString(
                "priceElement$id",
                findViewById<TextView>(el).text.toString()
            )
        }

        outState.putString("unformattedTxtInput", unformattedTxtInput)

        super.onSaveInstanceState(outState)
    }

    private val elements = arrayOf(
        R.id.txtAmount20dollars,
        R.id.txtAmount10dollars,
        R.id.txtAmount5dollars,
        R.id.txtAmount1dollar,
        R.id.txtAmount25cents,
        R.id.txtAmount10cents,
        R.id.txtAmount5cents,
        R.id.txtAmount1cent
    )
    private var unformattedTxtInput: String = ""
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        findViewById<TextView>(R.id.txtInput).text = savedInstanceState.getString("txtInput")

        elements.forEachIndexed { id, el ->
            findViewById<TextView>(el).text = savedInstanceState.getString("priceElement$id")
        }

        unformattedTxtInput = savedInstanceState.getString("unformattedTxtInput").toString()
    }


    fun getValue(v: View): String {
        return when (v.id) {
            R.id.btnClear -> "-1"
            else -> (v as TextView).text.toString()
        }
    }
    private val numbers = arrayOf(20.0, 10.0, 5.0, 1.0, 0.25, 0.1, 0.05, 0.01)
    private val maxTextview = 8
    override fun onClick(v: View) {
        val value = getValue(v)
        val txtInput: TextView = findViewById(R.id.txtInput)

        if (value == "-1") {
            txtInput.text = ""
            unformattedTxtInput = ""
            elements.forEach { findViewById<TextView>(it).text = "0" }
            return
        } else {
            if (unformattedTxtInput.length > maxTextview) {
                Toast.makeText(
                    this@MainActivity,
                    "The number seems too large!",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            unformattedTxtInput = (unformattedTxtInput + value).toInt().toString()
        }
        var parsedNumber = unformattedTxtInput.toDouble() / 100
        txtInput.text = parsedNumber.toString()

        numbers.forEachIndexed { id, it ->
            val amounts = (parsedNumber / it).toInt().toString()
            findViewById<TextView>(elements[id]).text = amounts
            parsedNumber -= amounts.toDouble() * it
        }
    }

}