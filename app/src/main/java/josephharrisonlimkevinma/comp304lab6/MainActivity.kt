package josephharrisonlimkevinma.comp304lab6

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import comp304lab6.comp304lab6.R
import josephharrisonlimkevinma.comp304lab6.ex1.TransitActivity
import josephharrisonlimkevinma.comp304lab6.ex2.Ex2MainActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //attach event handlers
        findViewById<ListView>(R.id.exerciseListView).setOnItemClickListener { adapterView, _, position, _ ->
            run {
                when (adapterView.getItemAtPosition(position).toString()) {
                    getString(R.string.ex1) -> startActivity(Intent(this, TransitActivity::class.java))
                    getString(R.string.ex2) -> startActivity(Intent(this, Ex2MainActivity::class.java))
                }
            }
        }
    }
}
