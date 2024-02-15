package fryctze.college.course

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import fryctze.college.course.databinding.ActivityMainMenuBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

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

            if (word != "null" || def != "null"){
                writeToFile(word, def)
            }

        }
    }

    private fun writeToFile(word: String, def: String) {
        val path = application.filesDir
        try {
            val writer = FileOutputStream(File(path, "file.txt"))
            val line = "$word\t$def"
            writer.write(line.toByteArray())
            writer.close()
            Log.d("TAG", "writeToFile: $line")
        }catch (e: Exception){
            Toast.makeText(this, "failed something wrong", Toast.LENGTH_SHORT).show()
            Log.d("TAG", "writeToFile: ${e.message}")
        }


        val h:String = readCreatedFile()
        Log.d("TAG", "readFullFile: $h")
        val t = h.split("\t").toTypedArray()
        if (t.size > 1){
            Log.d("main menu", "readCreatedFile: $t")
            Log.d("main menu", "readCreatedFile: Added word:${t[0]} dictionary:${t[1]}")
        }
    }

    private fun readCreatedFile(): String {
        val path = applicationContext.filesDir
        val readFrom = File(path, "file.txt")
        val content = ByteArray(readFrom.length().toInt())

        return try {
            val stream = FileInputStream(readFrom)
            stream.read(content)
            String(content)
        }catch (e: Exception){
            Log.d("TAG", "readCreatedFile: ${e.message}")
            e.toString()
        }
        /*val t = String(content).split("\t").toTypedArray()
        if (t.size > 1){
            Log.d("main menu", "readCreatedFile: $t")
            Log.d("main menu", "readCreatedFile: Added word:$t[0] dictionary:$t[1]")
        }*/
    }
}