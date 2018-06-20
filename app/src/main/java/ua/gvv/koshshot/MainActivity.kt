package ua.gvv.koshshot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ua.gvv.koshshot.ui.edit.EditActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_edit.setOnClickListener{ startActivity(Intent(this, EditActivity::class.java))}
    }
}
