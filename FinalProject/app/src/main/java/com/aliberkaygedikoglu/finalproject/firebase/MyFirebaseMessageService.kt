package com.aliberkaygedikoglu.finalproject.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessageService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("newToken", token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("Message from", "${message.from}")
        Log.d("Message messageId", "${message.messageId}")
        Log.d("Message data", "${message.data}")
        message.notification?.let {
            Log.d("Message body", "${it.body}")
        }
    }
//newtoken
//dtKXsWlkQAW7uP4YAOO6VN:APA91bHpstzPzVLK_FoBj0RUC_DeJ4DTLYc3_vipoY0yI1klF9-XjJuPpuNbWUVAEcehZTFsckPZph4O0oOE--bviJxRKTCl9iMVa7dKrq0hoMriNcAKcO3dktNopISEtvC_1c6Vg_6_
}