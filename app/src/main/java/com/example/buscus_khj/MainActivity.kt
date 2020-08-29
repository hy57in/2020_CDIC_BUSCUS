package com.example.buscus_khj

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // material button background color programmatically
        val buttonClick = findViewById<View>(R.id.button_click)

        buttonClick.setOnClickListener {
            (it as MaterialButton).apply {
                // set material button background tint list as a single color
                backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
                // set material button background tint mode
                backgroundTintMode = PorterDuff.Mode.SRC_ATOP
            }
        }

    }
}