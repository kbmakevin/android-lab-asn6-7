package josephharrisonlimkevinma.comp304lab6.ex1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import comp304lab6.comp304lab6.R

class TransitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transit)

        Toast.makeText(this, "You in da transit activity!", Toast.LENGTH_SHORT).show()
    }
}
