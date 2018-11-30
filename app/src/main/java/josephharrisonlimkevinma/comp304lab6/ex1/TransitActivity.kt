package josephharrisonlimkevinma.comp304lab6.ex1

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import comp304lab6.comp304lab6.R
import kotlinx.android.synthetic.main.activity_transit.*
import org.jetbrains.anko.*
import java.io.IOException
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
        transitRequest(reqStr)
    }

    private fun transitRequest(req: String) {

        doAsync {
            Log.d("TRANSIT", "making request")
            val reqTxt = URL(req).readText()
            //val reqTxt = URL(req).openStream().bufferedReader().readText()
            Log.d("TRANSIT", "Request finished")

            uiThread {
                Log.d("TRANSIT", "Parsing request")
                val parsedStr = parseRequest(reqTxt)
                Log.d("TRANSIT", "Setting text directions")
                txtDirections.text = parsedStr
            }
        }
    }

    private fun parseRequest(reqTxt: String): String {
        val stepStr = StringBuilder()
        try {
            ObjectMapper().readTree(reqTxt).map { node ->
                val route = node["routes"][0]
                val leg = route["legs"][0]
                val steps = leg["steps"]
                for (step in steps) {
                    stepStr.appendln(step["html_instructions"].asText())
                }
            }
        }
        catch (e: Throwable) {
            Log.e("PARSE_ERR", e.toString())
        }
        return stepStr.toString()
    }
}
