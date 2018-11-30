package josephharrisonlimkevinma.comp304lab6.ex2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast

class SMSReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        //---get the SMS message passed in---
        val bundle = intent.extras
        var msgs: Array<SmsMessage>?
        var str = "SMS from "
        if (bundle != null) {
            //---retrieve the SMS message received---
            val pdus = bundle.get("pdus") as Array<SmsMessage>
//            msgs = arrayOfNulls(size = pdus.size)
            msgs = arrayOf()
            for (i in msgs.indices) {
                msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                if (i == 0) {
                    //---get the sender address/phone number---
                    str += msgs[i].originatingAddress
                    str += ": "
                }
                //---get the message body---
                str += msgs[i].messageBody.toString()
            }
            //---display the new SMS message---
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
            Log.d("SMSReceiver", str)
            //---stop the SMS message from being broadcasted---
            this.abortBroadcast()
            //---send a broadcast intent to update the SMS received in the activity
            val broadcastIntent = Intent()
            broadcastIntent.action = "SMS_RECEIVED_ACTION"
            broadcastIntent.putExtra("sms", str)
            context.sendBroadcast(broadcastIntent)
        }
    }
}