package josephharrisonlimkevinma.comp304lab6.ex1

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Toast
import com.fasterxml.jackson.databind.ObjectMapper
import comp304lab6.comp304lab6.R
import kotlinx.android.synthetic.main.activity_transit.*
import org.jetbrains.anko.*
import java.net.URL

class TransitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transit)

        Toast.makeText(this, "You in da transit activity!", Toast.LENGTH_SHORT).show()
    }

    fun onGetInfoClick(view: View?) {
        val start = txtStartPoint.text.toString()
        val dest = txtEndPoint.text.toString()
        val url = "https://maps.googleapis.com/maps/api/directions/json"
        val reqStr = "$url?origin=${Uri.encode(start)}&destination=${Uri.encode(dest)}&key=${resources.getString(R.string.google_maps_key)}"
        transitRequest(reqStr)
    }

    private fun transitRequest(req: String) {
        doAsync {
            Log.d("QWERTY", "making request")
            var reqTxt = ""
            try {
                reqTxt = URL(req).readText()
            } catch (e: Throwable) {
                Log.d("QWERTY", e.toString())
            }
            //val reqTxt = URL(req).openStream().bufferedReader().readText()
            Log.d("QWERTY", "Request finished")

            uiThread {
                Log.d("QWERTY", "Parsing request")
                val parsedStr = parseRequest(reqTxt)
                Log.d("QWERTY", "Setting text directions")
                txtDirections.text = Html.fromHtml(parsedStr, Html.FROM_HTML_MODE_LEGACY).toString()
            }
        }
    }

    private fun parseRequest(reqTxt: String): String {
        val stepStr = StringBuilder()
        try {
            val json = ObjectMapper().readTree(reqTxt)
            val route = json["routes"][0]
            val leg = route["legs"][0]
            val steps = leg["steps"]
            for (step in steps) {
                stepStr.appendln(step["html_instructions"].asText())
                stepStr.appendln()
            }
        }
        catch (e: Throwable) {
            Log.e("PARSE_ERR", e.toString())
        }
        return stepStr.toString()
    }
}
