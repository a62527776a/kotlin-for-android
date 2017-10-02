package com.john.kotlindemo

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.TextView
import com.john.kotlindemo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.data="这是一个Kotlin和dataBinding的组合应用demo \n 并包含了自定义View  DraweeView"

        //点下试试
        bt_test.setOnClickListener {
            binding.data = "哈哈哈"
        }
        findData()
    }

    open fun findData() {
        var client = OkHttpClient()
        var httpTextView: TextView? = null
        var builder = Request.Builder()
        builder.url("https://www.v2ex.com/api/nodes/show.json")
        var request = builder.build()
        try {
            var response = client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("onFailure")
                }
                override fun onResponse(call: Call, response: Response) {
                    Looper.prepare()
                    println("onResponse")
                    var context: Context = applicationContext
                    var text: String? = response.body()?.string()
                    println(text)
                    var duration: Int = Toast.LENGTH_SHORT
                    var toast:Toast = Toast.makeText(context, text, duration)
                    toast.show()
                    Looper.loop()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //启动新界面
    fun startactivity(view: View){
        startActivity(Intent(this, OtherActivity::class.java))
    }



    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode== KeyEvent.KEYCODE_BACK&&web_test.canGoBack()){
            web_test.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
    override fun onDestroy() {
        if (web_test != null) {
            web_test.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            web_test.clearHistory()
            (web_test.getParent() as ViewGroup).removeView(web_test)
            web_test.destroy()
        }
        super.onDestroy()
    }
}
