package com.example.buscus_khj

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // material button background color programmatically
        button_click.setOnClickListener {
            (it as MaterialButton).apply {
                // set material button background tint list as a single color
                backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
                // set material button background tint mode
                backgroundTintMode = PorterDuff.Mode.SRC_ATOP
            }

            val intent = Intent(this, BusChoiceActivity::class.java)
            startActivity(intent)
        }



    }
}