package josephharrisonlimkevinma.comp304lab6.ex2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.*
import comp304lab6.comp304lab6.R

class Ex2MainActivity : AppCompatActivity() {

    private lateinit var contacts: Array<String>
    private lateinit var lstView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ex2)
        this.supportActionBar!!.title = "My Messaging App"
        lstView = findViewById(R.id.android_list)
        var textView = TextView(applicationContext)
        textView.setText("My Contacts")
        lstView.addHeaderView(textView)
        lstView.choiceMode = ListView.CHOICE_MODE_NONE
        lstView.isTextFilterEnabled = true
        contacts = resources.getStringArray(R.array.contacts)

        //request sms permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            //Permission is not granted so need to request
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS), 0)
            // The callback method gets the result of the request.
        }

        var adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                contacts
        )
        //assign adatper to listview
        lstView.adapter = adapter
        //listview item click listener
        lstView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, l ->
            var contactNameSelected = lstView.getItemAtPosition(position) as String
            startActivity(Intent(this, MessageActivity::class.java).putExtra("contactName", contactNameSelected))
        }
    }
}
