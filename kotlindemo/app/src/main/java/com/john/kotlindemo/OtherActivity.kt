package com.john.kotlindemo

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.john.kotlindemo.R
import com.john.kotlindemo.databinding.ActivityOtherBinding

class OtherActivity : AppCompatActivity() {

    lateinit var binding : ActivityOtherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding : ActivityOtherBinding = DataBindingUtil.setContentView(this, R.layout.activity_other)

        val user:User= User()
        user.phone = ""
        user.passwd = ""
        binding.data = user
        val toolBar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolBar)
        toolBar.setNavigationIcon(R.mipmap.ic_menu_white_36dp)
    }
}
