package fryctze.college.course

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import fryctze.college.course.databinding.ActivityAddWordBinding

class AddWordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun saveClick(view: View) {
        val intent = Intent().apply {
            putExtra("word", binding.etNewWord.text.toString())
            putExtra("def", binding.etNewDef.text.toString())
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}