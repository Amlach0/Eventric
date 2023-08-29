package com.eventric.repo

import android.net.Uri
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.inject.Singleton

const val I_TAG = "ImageRepository"

@Singleton
class ImagesRepository @Inject constructor() {
    private val storage = Firebase.storage
    private val storageRef = storage.reference

    private suspend fun uploadImage(uri: Uri, name: String, path: String) {
        try {
            val imageRef = storageRef.child("$path/$name.jpg")

            imageRef.putFile(uri).await()
            Log.d(I_TAG, "Image $path/$name.jpg uploaded")
        } catch (e: Exception) {
            Log.w(I_TAG, "Error Uploading Image $path/$name.jpg : ", e)
            throw e
        }
    }

    suspend fun uploadUserImage(uri: Uri, userId: String) =
        uploadImage(uri, userId, "users")

    suspend fun uploadEventImage(uri: Uri, eventId: String) =
        uploadImage(uri, eventId, "events")

    private fun downloadImage(name: String, path: String) = try {
        val imageRef = storageRef.child("$path/$name.jpg")

        flowOf(imageRef).map {
            try {
                it.downloadUrl.await()
            } catch (e: Exception) {
                Log.w(I_TAG, "Error Downloading Image $path/$name.jpg : ", e)
                Uri.EMPTY
            }
        }
    } catch (ce: CancellationException) {
        throw ce
    } catch (e: Exception) {
        Log.w(I_TAG, "Error Downloading Image $path/$name.jpg : ", e)
        flowOf(Uri.EMPTY)
    }

    fun downloadUserImage(userId: String) = downloadImage(userId, "users")

    fun downloadEventImage(eventId: String) = downloadImage(eventId, "events")

    private fun downloadAllImages(path: String) = try {
        val pathRef = storageRef.child(path)

        flowOf(pathRef).map { pRef ->
            pRef.listAll().await().items.map {
                it.name.substringBeforeLast(".") to it.downloadUrl.await()
            }.toMap()
        }
    } catch (ce: CancellationException) {
        throw ce
    } catch (e: Exception) {
        Log.w(I_TAG, "Error Downloading all Images $path : ", e)
        flowOf(mapOf())
    }

    fun downloadAllUserImages() = downloadAllImages("users")

    fun downloadAllEventsImages() = downloadAllImages("events")

    private suspend fun deleteImage(name: String, path: String) {
        try {
            val imageRef = storageRef.child("$path/$name.jpg")

            imageRef.delete().await()
            Log.d(I_TAG, "Image $path/$name.jpg deleted")
        } catch (_: StorageException) {
        } catch (e: Exception) {
            Log.w(I_TAG, "Error Deleting Image $path/$name.jpg : ", e)
            throw e
        }
    }

    suspend fun deleteUserImage(userId: String) = deleteImage(userId, "users")

    suspend fun deleteEventImage(eventId: String) = deleteImage(eventId, "events")

}