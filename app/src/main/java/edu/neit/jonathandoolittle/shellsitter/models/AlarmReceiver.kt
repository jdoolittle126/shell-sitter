package edu.neit.jonathandoolittle.shellsitter.models

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

/**
 *
 * Class Description - TODO
 *
 * Class Logic - TODO
 *
 * <pre>
 *  Class Usage - TODO
 * </pre>
 *
 * @author Jonathan Doolittle
 * @version 0.1 - 9/22/2021
 *
 */

class AlarmReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Alarm running", Toast.LENGTH_SHORT).show();
        val name = intent?.extras?.getString("name")
        val intent1 = Intent(context, ShellNotificationService::class.java)
        intent1.putExtra("name", name)
        context?.startService(intent1)
    }

}