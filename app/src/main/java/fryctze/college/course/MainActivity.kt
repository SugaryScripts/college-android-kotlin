package fryctze.college.course

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import fryctze.college.course.databinding.ActivityMainBinding
import java.io.File
import java.io.FileInputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private val WORDS = arrayOf(
        "contract", "a binding agreement that enforceable by law",
        "assurance", "a binding agreement to do or give or refrain from something",
        "determine", "find out or learn with certainty, as by making an inquiry",
        "engage", "consume on one's attention or time",
        "establish", "set up or found",
        "provision", "the activity of supplying something",
        "resolve", "find a solution or answer",
        "specific", "state explicitly or in detail",
        "assure", "inform positively and with cerainty and confidence",
        "cancel", "declare null or void",
        "cancelled", "no longer planned or scheduled",
        "obligation", "the state of being bound to do or pay something",
    )

    private lateinit var binding: ActivityMainBinding

    private lateinit var dictionary: HashMap<String, String>
    private lateinit var words: ArrayList<String>
    private lateinit var question: String
    private lateinit var answerChoices: ArrayList<String>

    private val TAG = "state (^_^)"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        Log.d(TAG, "onCreate: dipanggil")

        val firstQuestion = intent.getStringExtra("question")

        setContentView(view)
        readFromFile()
        readCreatedFile()
        Log.d(TAG, "onCreate: last added ${words[words.size-1]} and the def ${dictionary[words[words.size-1]]}")
        Toast.makeText(this, "onCreate: last added ${words[words.size-1]} and the def ${dictionary[words[words.size-1]]}", Toast.LENGTH_SHORT).show()
        //readFromCode()
        letsPlay(firstQuestion)
    }

    private fun readCreatedFile() {
        val path = applicationContext.filesDir
        val readFrom = File(path, "file.txt")
        val content = ByteArray(readFrom.length().toInt())

        val stream = FileInputStream(readFrom)
        stream.read(content)
        val original = String(content)
        Log.d(TAG, "readFullFile: $original")
        val t = original.split("\t").toTypedArray()
        if (t.size > 1){
            words.add(t[0])
            dictionary[t[0]] = t[1]
            Log.d(TAG, "readCreatedFile: $t")
            Log.d(TAG, "readCreatedFile: Added word:${t[0]} dictionary:${t[1]}")
        }
    }

    private fun readFromFile() {
        words = ArrayList<String>()
        dictionary = HashMap<String, String>()

        val scan: Scanner = Scanner(resources.openRawResource(R.raw.vocab))
        while (scan.hasNextLine()) {
            val line: String = scan.nextLine()
            val t = line.split("\t").toTypedArray()
            // t[0] = acceptance
            // t[1] = state of being okay with something or someone

            if (t.size >= 2) {
                val word = t[0]
                val def = t[1]
                words.add(word)
                dictionary[word] = def
            }
        }
    }

    private fun readFromCode() {
        words = ArrayList<String>()
        // fill the dictionary
        dictionary = HashMap<String, String>()
        for (i in WORDS.indices step 2) {
            words.add(WORDS[i])
            dictionary[WORDS[i]] = WORDS[i+1]
        }
    }

    private fun letsPlay(firstQuestion: String?) {
        words.shuffle()

        question = firstQuestion ?: words[0]
        binding.tvQuestion.text = question

        answerChoices = ArrayList<String>()
        for (i in 0..5){
            dictionary[words[i]]?.let { answerChoices.add(it) }
        }

        if (firstQuestion != null)
            answerChoices[0] = dictionary[firstQuestion].toString()

        answerChoices.shuffle()

        showQuestionAnswer()
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

    private fun showQuestionAnswer() {
        // show question
        binding.tvQuestion.text = question

        // show answers
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, answerChoices)

        binding.opsys.adapter = adapter

        binding.opsys.setOnItemClickListener { adapterView, view, i, l ->
            val clicked = answerChoices[i]
            val rightAnswer = dictionary[question]

            when (clicked) {
                rightAnswer -> Toast.makeText(applicationContext, "Jawaban benar!!", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(applicationContext, "salah", Toast.LENGTH_SHORT).show()
            }
            letsPlay(null)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("question_origin", question)
        outState.putStringArrayList("answer_options_origin", answerChoices)

        Log.d(TAG, "onSaveInstanceState: dipanggil")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        question = savedInstanceState.getString("question_origin").toString()
        answerChoices = savedInstanceState.getStringArrayList("answer_options_origin") as ArrayList<String>
        showQuestionAnswer()

        Log.d(TAG, "onRestoreInstanceState: dipanggil")
    }
}