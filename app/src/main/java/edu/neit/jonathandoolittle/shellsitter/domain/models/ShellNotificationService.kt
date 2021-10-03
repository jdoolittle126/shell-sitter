package edu.neit.jonathandoolittle.shellsitter.domain.models

import android.app.IntentService
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import edu.neit.jonathandoolittle.shellsitter.R
import edu.neit.jonathandoolittle.shellsitter.ui.MainActivity
import java.util.*

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

class ShellNotificationService : IntentService("ShellService!") {


    override fun onHandleIntent(intent: Intent?) {

        val name = intent?.extras?.getString("name")

        var builder = NotificationCompat.Builder(getApplication(), MainActivity.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_turtle)
            .setContentTitle(name ?: "Someone")
            .setContentText("is ready for feeding!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(getApplication())) {
            notify(Random().nextInt(), builder.build())
        }
    }

    // ******************************
    // Variables
    // ******************************

    // ******************************
    // Public methods
    // ******************************

    // ******************************
    // Private methods
    // ******************************

}