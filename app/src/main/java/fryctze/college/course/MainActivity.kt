package fryctze.college.course

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import fryctze.college.course.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadList()
    }

    private fun loadList() {
        // Items of listview
        val item: ArrayList<String> = ArrayList()
        item.add("Android")
        item.add("Windows")
        item.add("iOS")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            item)

        binding.opsys.adapter = adapter

        item.add("Linux")

        // list doesn't recognize xml on click
        // solution is must from java/kotlin code
        binding.opsys.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(
                this,
                "Hello clicked $i",
                Toast.LENGTH_SHORT
            ).show() }
    }
}