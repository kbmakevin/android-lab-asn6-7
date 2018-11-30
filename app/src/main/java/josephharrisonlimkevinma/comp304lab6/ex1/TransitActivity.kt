package josephharrisonlimkevinma.comp304lab6.ex1

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import comp304lab6.comp304lab6.R
import kotlinx.android.synthetic.main.activity_transit.*
import org.jetbrains.anko.doAsync
import java.net.URL

class TransitActivity : AppCompatActivity() {
    companion object {
        private const val API_URL: String = "https://maps.googleapis.com/maps/api/directions/json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transit)

        Toast.makeText(this, "You in da transit activity!", Toast.LENGTH_SHORT).show()
    }

    fun onGetInfoClick(view: View?) {
        val start = txtStartPoint.text.toString()
        val dest = txtEndPoint.text.toString()
        val reqStr = "${TransitActivity.API_URL}?origin=${Uri.encode(start)}&destination=${Uri.encode(dest)}&key${R.string.google_maps_key}"

    }

    private fun transitRequest(req: String) {
        doAsync {
            var lines = emptyList<String>()
            URL(req).openStream().use {
                lines = it.bufferedReader().readLines()
            }

        }
    }
}
