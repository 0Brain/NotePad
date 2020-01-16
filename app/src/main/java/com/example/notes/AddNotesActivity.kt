package com.example.notes

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rengwuxian.materialedittext.MaterialEditText
import kotlinx.android.synthetic.main.activity_add_notes.*


class AddNotesActivity : AppCompatActivity()  {

    private val activity = this@AddNotesActivity
    private lateinit var textInputEditNoteName: MaterialEditText
    private lateinit var textInputEditTextNoteDescription: MaterialEditText
    val dbTable = "Notes"
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        val intentThatStartedThisActivity = intent
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            val partId = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT)
            val foodDes = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_SUBJECT)
            note_name.setText(partId)
            note_detail_description.setText(foodDes)
        }


//        try {
//            val bundle:Bundle = intent.extras!!
//            id = bundle.getInt("ID", 0)
//            if (id!=0){
//                note_name.setText(bundle.getString(Intent.EXTRA_TEXT))
//                note_detail_description.setText(bundle.getString(Intent.EXTRA_SUBJECT))
//            }
//        }catch (ex:Exception){}
        initViews()
    }

    private fun initViews() {
        textInputEditNoteName = findViewById<View>(R.id.note_name) as MaterialEditText
        textInputEditTextNoteDescription = findViewById<View>(R.id.note_detail_description) as MaterialEditText
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun verifyFromSQLite() {
        var dbManager = DbManager(this)
        var values= ContentValues()
        values.put("Title",textInputEditNoteName.text.toString())
        values.put("Description",textInputEditTextNoteDescription.text.toString())
        if (id == 0){
            val ID = dbManager.insert(values)
            if (ID>0){
                Toast.makeText(this, "Note is added", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Error adding note...", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            var selectionArgs = arrayOf(id.toString())
            val ID = dbManager.update(values, "ID=?", selectionArgs)
            if (ID>0){
                Toast.makeText(this, "Note is added", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Error adding note...", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onBackPressed() {
        if(textInputEditNoteName.text.toString().equals("") && textInputEditTextNoteDescription.text.toString().equals(""))  {
            setResult(2)
            finish()
        }
        else{
            setResult(1)
            verifyFromSQLite()
        }
        super.onBackPressed()
    }
}
