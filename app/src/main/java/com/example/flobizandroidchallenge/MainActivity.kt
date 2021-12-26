package com.example.flobizandroidchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flobizandroidchallenge.ui.main.MainFragment
import java.lang.reflect.Array.newInstance
import java.net.URLClassLoader.newInstance

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, MainFragment()).commit();}
    }

}