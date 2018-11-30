package josephharrisonlimkevinma.comp304lab6.ex2

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.telephony.SmsManager
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import comp304lab6.comp304lab6.R

class MessageActivity : Activity() {
    private var eText: EditText? = null
    private val SMSes: TextView? = null
    private var textMessage: TextView? = null
    //
    private var SENT = "SMS_SENT"
    private var DELIVERED = "SMS_DELIVERED"
    //
    private lateinit var sentPI: PendingIntent
    private lateinit var deliveredPI: PendingIntent
    private lateinit var smsSentReceiver: BroadcastReceiver
    private lateinit var smsDeliveredReceiver: BroadcastReceiver
    private lateinit var intentFilter: IntentFilter
    //
    // receive intents sent by sendBroadcast()
    private val intentReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //display the SMS received in the TextView
            textMessage = findViewById<View>(R.id.textMessage) as TextView
            //display the content of the received message in text view
            //SMSes.setText(intent.getExtras().getString("sms"));
            textMessage!!.text = textMessage!!.text.toString() + "\n" +
                    intent.extras!!.getString("sms")
        }
    }

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        val extras = intent.extras
        var contactName: String? = ""
        if (extras != null)
            contactName = extras.getString("contactName")
        textMessage = findViewById<View>(R.id.textMessage) as TextView
        textMessage!!.movementMethod = ScrollingMovementMethod.getInstance()
        val tView = findViewById<View>(R.id.textView) as TextView
        tView.text = contactName
        //this.getSupportActionBar().setTitle(contactName);
        val imgView = findViewById<View>(R.id.imageView) as ImageView
        imgView.setImageResource(R.drawable.contacts)
        //
        eText = findViewById<View>(R.id.editText) as EditText
        eText!!.setOnClickListener {
            // clear text in edit text when user clicks on it
            eText!!.setText("")
        }

        //
        //an action to take in the future with same permission
        //as your application
        sentPI = PendingIntent.getBroadcast(this, 0, Intent(SENT), 0)
        deliveredPI = PendingIntent.getBroadcast(this, 0, Intent(DELIVERED), 0)
        //intent to filter the action for SMS messages received
        intentFilter = IntentFilter()
        intentFilter.addAction("SMS_RECEIVED_ACTION")
        //---register the receiver---
        registerReceiver(intentReceiver, intentFilter)
        //
    }

    //
    public override fun onResume() {
        super.onResume()
        //---create the BroadcastReceiver when the SMS is sent---
        smsSentReceiver = object : BroadcastReceiver() {
            override fun onReceive(arg0: Context, arg1: Intent) {
                //Retrieve the current result code, as set by the previous receiver
                when (resultCode) {
                    Activity.RESULT_OK -> Toast.makeText(baseContext, "SMS sent", Toast.LENGTH_LONG).show()

                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> Toast.makeText(baseContext, "Generic failure", Toast.LENGTH_SHORT).show()

                    SmsManager.RESULT_ERROR_NO_SERVICE -> Toast.makeText(baseContext, "No service", Toast.LENGTH_SHORT).show()

                    SmsManager.RESULT_ERROR_NULL_PDU -> Toast.makeText(baseContext, "Null PDU", Toast.LENGTH_SHORT).show()

                    SmsManager.RESULT_ERROR_RADIO_OFF -> Toast.makeText(baseContext, "Radio off", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //---create the BroadcastReceiver when the SMS is delivered---
        smsDeliveredReceiver = object : BroadcastReceiver() {
            override fun onReceive(arg0: Context, arg1: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> Toast.makeText(baseContext, "SMS delivered",
                            Toast.LENGTH_LONG).show()
                    Activity.RESULT_CANCELED -> Toast.makeText(baseContext, "SMS not delivered",
                            Toast.LENGTH_LONG).show()
                }
            }
        }
        //---register the two BroadcastReceivers---
        registerReceiver(smsDeliveredReceiver, IntentFilter(DELIVERED))
        registerReceiver(smsSentReceiver, IntentFilter(SENT))
    }

    public override fun onPause() {
        super.onPause()
        //---unregister the two BroadcastReceivers---
        unregisterReceiver(smsSentReceiver)
        unregisterReceiver(smsDeliveredReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        //---unregister the receiver---
        unregisterReceiver(intentReceiver)
    }

    //
    fun sendMessage(v: View) {
        eText = findViewById<View>(R.id.editText) as EditText
        sendSMS("5556", eText!!.text.toString())
        textMessage!!.text = textMessage!!.text.toString() + "\n" + eText!!.text
    }

    //sends an SMS message to another device
    private fun sendSMS(phoneNumber: String, message: String) {
        val sms = SmsManager.getDefault()
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
//            //Permission is not granted so need to request
//            ActivityCompat.requestPermissions(this,
//                    arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS), 0)
//            // The callback method gets the result of the request.
//        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI)
        } else {
            Toast.makeText(this, "You cannot use this feature as you have not granted permissions for ${arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS)}", Toast.LENGTH_LONG).show()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_message, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        val id = item.itemId
//
//        return if (id == R.id.action_settings) {
//            true
//        } else super.onOptionsItemSelected(item)
//    }
}