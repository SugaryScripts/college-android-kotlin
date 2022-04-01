package fryctze.college.course

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import fryctze.college.course.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPreference()
    }

    private fun initPreference() {
        pref = getSharedPreferences("MY_PREF", MODE_PRIVATE)
        editor = pref.edit()
    }

    fun btnSave(view: View) {
        with(editor) {
            putString("NAME", binding.etName.text.toString())
            putBoolean("READ", binding.cbRead.isChecked)
            putBoolean("SWIM", binding.cbSwim.isChecked)
            if (binding.etWeight.text.isNullOrEmpty()) {
                putInt("WEIGHT", 0)
            }else{
                putInt("WEIGHT", binding.etWeight.text.toString().toInt())
            }
            commit()
        }

    }

    fun btnLoad(view: View) {
        binding.etName.setText(pref.getString("NAME", "belum memasukan nama"))
        binding.cbRead.isChecked = pref.getBoolean("READ", false)
        binding.cbSwim.isChecked = pref.getBoolean("SWIM", false)
        binding.etWeight.setText(pref.getInt("WEIGHT", 0).toString())
    }

}