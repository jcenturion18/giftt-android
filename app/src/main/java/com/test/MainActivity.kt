package com.test

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.test.database.DataBaseInterface
import com.test.database.DatabaseImp
import com.test.databinding.ActivityMainBinding
import com.test.dto.User

class MainActivity(
    private val db: DataBaseInterface = DatabaseImp()
) : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var lastUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTextListener()
        loadLastUserName()
    }

    private fun setTextListener() {
        binding.language.setOnClickListener { }

        binding.addUser.setOnClickListener {
            showDialog()
        }
    }

    private fun loadLastUserName() {
        db.loadUserName {
            lastUser = it
            val text = "${getString(R.string.last_user)} ${it.name}"
            binding.lastUser.text = text
        }
    }

    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)

        builder.setTitle(getString(R.string.enter_name))

        val input = EditText(this)

        input.hint = getString(R.string.name)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton(getString(R.string.submit)) { _, _ ->
            db.store(input.text.toString())
        }
        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}
