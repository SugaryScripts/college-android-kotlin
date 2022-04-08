package fryctze.college.course

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import fryctze.college.course.databinding.ActivityMainMenuBinding

class MainMenuActivity : AppCompatActivity() {
    private val REQ_CODE = 123

    private lateinit var binding: ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun playGameClick(view: View) {
        val firstQuestion = "beginner"
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("question", firstQuestion)
        }
        startActivity(intent)
    }
    fun addWordClick(view: View) {
        val intent = Intent(this, AddWordActivity::class.java)
        startActivityForResult(intent, REQ_CODE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE) {
            val word = data?.getStringExtra("word").toString()
            val def = data?.getStringExtra("def").toString()

            Toast.makeText(this, "Kata=$word def=$def", Toast.LENGTH_SHORT).show()
        }
    }
}