package josephharrisonlimkevinma.comp304lab6.ex2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import comp304lab6.comp304lab6.R

class MessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        Toast.makeText(this, "You in da msg activity!", Toast.LENGTH_SHORT).show()
    }
}
