package Kotlin.FirebaseUtils

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient

import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException


class FirebaseConnection {
    fun init(): Firestore? {
        var serviceAccount: FileInputStream? = null
        val firebaseApp = try {
            serviceAccount = FileInputStream("javafb-in-a575e-firebase-adminsdk-edfq3-165705a4a1.json")
            val options = FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
            FirebaseApp.initializeApp(options)
        } catch (ex: FileNotFoundException) {
            println(ex.message)
        } catch (ex: IOException) {
            println(ex.message)
        } finally {
            try {
                serviceAccount!!.close()
            } catch (ex: IOException) {
                println(ex.message)
            }
        }
        return FirestoreClient.getFirestore()
    }
}