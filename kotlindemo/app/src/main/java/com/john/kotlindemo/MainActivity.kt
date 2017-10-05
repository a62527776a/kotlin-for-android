package com.john.kotlindemo

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.john.kotlindemo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    lateinit var dataSet: MoveList
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        var mRecyclerView: RecyclerView;
        var mAdapter: RecyclerView.Adapter<*>
        var mLayoutManager: RecyclerView.LayoutManager

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.data="这是一个Kotlin和dataBinding的组合应用demo \n 并包含了自定义View  DraweeView"
        val toolBar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolBar.setNavigationIcon(R.mipmap.ic_launcher)
        //点下试试
        bt_test.setOnClickListener {
            binding.data = "哈哈哈"
        }
        findData()
        mRecyclerView = findViewById(R.id.recycler_view) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.setLayoutManager(mLayoutManager)
    }

    class MoveList {
        lateinit var subjects: List<Movie>
        lateinit var movieLi: Object

        class Movie {
            lateinit var title: String
            lateinit var id: String
            data class Rating (var average: Int)
            data class Genres (var genres: Array<String>)
            class Casts {
                lateinit var alt: String
                lateinit var id: String
                lateinit var name: String
                data class avatars (var small: String)
            }
        }

        fun getmovieLi(): Object {
            return this.movieLi
        }

        fun setmovieLi(Movie: Object): Object {
            this.movieLi = Movie
        }
    }

    open fun findData() {
        var client = OkHttpClient()
        var httpTextView: TextView? = null
        var builder = Request.Builder()
        builder.url("https://api.douban.com/v2/movie/in_theaters")
        var request = builder.build()
        try {
            var response = client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("onFailure")
                }
                override fun onResponse(call: Call, response: Response) {
//                    Looper.prepare()
//                    println("onResponse")
//                    var context: Context = applicationContext
                    var text: String? = response.body()?.string()
//                    println(text)
//                    var duration: Int = Toast.LENGTH_SHORT
//                    var toast:Toast = Toast.makeText(context, text, duration)
//                    toast.show()
//                    Looper.loop()
                    var type = object : TypeToken<MoveList>() {

                    }.type

                    dataSet = Gson().fromJson(text, type)
                    binding.data = text
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
}
