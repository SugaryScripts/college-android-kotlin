package fryctze.college.course

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import fryctze.college.course.databinding.ActivityMainBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        readFromFile()
        //readFromCode()
        letsPlay()
    }

    private fun readFromFile() {
        words = ArrayList<String>()
        dictionary = HashMap<String, String>()

        val scan: Scanner = Scanner(resources.openRawResource(R.raw.vocab))
        while (scan.hasNextLine()){
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

    private fun letsPlay() {
        words.shuffle()

        val question = words[0]
        binding.tvQuestion.text = question

        val answerChoices = ArrayList<String>()
        for (i in 0..5){
            dictionary[words[i]]?.let { answerChoices.add(it) }
        }

        answerChoices.shuffle()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, answerChoices)

        binding.opsys.adapter = adapter

        binding.opsys.setOnItemClickListener { adapterView, view, i, l ->
            val clicked = answerChoices[i]
            val rightAnswer = dictionary[question]

            when (clicked) {
                rightAnswer -> Toast.makeText(applicationContext, "Jawaban benar!!", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(applicationContext, "salah", Toast.LENGTH_SHORT).show()
            }
            letsPlay()
        }
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