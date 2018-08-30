package com.djakatechnology.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


class MainActivity : AppCompatActivity() {
    private var operation : String = ""
    private var justOperation : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val expression : Expression = ExpressionBuilder("3+2").build()

        tvResult.text = "0"

        zero.setOnClickListener {addToOperation("0") }
        one.setOnClickListener {addToOperation("1")}
        two.setOnClickListener {addToOperation("2")}
        three.setOnClickListener {addToOperation("3")}
        four.setOnClickListener {addToOperation("4")}
        five.setOnClickListener {addToOperation("5")}
        six.setOnClickListener {addToOperation("6")}
        seven.setOnClickListener {addToOperation("7")}
        eight.setOnClickListener {addToOperation("8")}
        nine.setOnClickListener {addToOperation("9")}
        plus.setOnClickListener {addToOperation("+")}
        minus.setOnClickListener {addToOperation("-")}
        divide.setOnClickListener {addToOperation("/")}
        multiply.setOnClickListener {addToOperation("*")}
        clear.setOnClickListener {addToOperation("")}
        del.setOnClickListener {addToOperation("DEL")}
        point.setOnClickListener {addToOperation(".") }
        equals.setOnClickListener{applyOperation()}
    }

    fun applyOperation(){
        try {
            val expression : Expression = ExpressionBuilder(operation).build()
            tvResult.text = "0"
            val otherSymbols = DecimalFormatSymbols(Locale.US)
            val df = DecimalFormat("#.##########", otherSymbols)
            tvOperation.text = df.format(expression.evaluate())
        }catch (e : Exception){
            tvOperation.text = operation
        }
    }

    fun addToOperation(value : String){
        if(value == "DEL"){
            try {
                operation = operation.substring(0, operation.length - 1)
                tvOperation.text = operation
                if(operation == "")
                    tvResult.text = "0"
            }catch (e : Exception){
                tvResult.text = "0"
                tvOperation.text = ""
            }
        }else{
            if(value != "." && justOperation && value.toIntOrNull() == null) return
            if(value == "") {
                operation = ""
                tvResult.text = ""
                tvOperation.text = ""
                return
            }
            else if(justOperation && value.toIntOrNull() != null) justOperation = false
            else if(!justOperation && value.toIntOrNull() == null){
                operation += value
                justOperation = true
                tvOperation.text = operation
                return
            }

            operation += value
        }

        try {
            val expression : Expression = ExpressionBuilder(operation).build()
            tvOperation.text = operation
            val otherSymbols = DecimalFormatSymbols(Locale.US)
            val df = DecimalFormat("#.##########", otherSymbols)
            tvResult.text = df.format(expression.evaluate())
        }catch (e : Exception){
            tvOperation.text = operation
        }
    }
}
