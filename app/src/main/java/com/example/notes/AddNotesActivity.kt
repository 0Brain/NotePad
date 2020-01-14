package com.example.notes

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
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
        var intentThatStartedThisActivity = getIntent()


        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            var partId = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT)
            var foodDes = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_SUBJECT)
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

    /**
     * This method is to initialize views
     */
    private fun initViews() {
        textInputEditNoteName = findViewById<View>(R.id.note_name) as MaterialEditText
        textInputEditTextNoteDescription = findViewById<View>(R.id.note_detail_description) as MaterialEditText
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
            val returnIntent = Intent()
            setResult(Activity.RESULT_CANCELED, returnIntent)
            finish()
        }
        else{
            verifyFromSQLite()
        }
        super.onBackPressed()
    }
}
