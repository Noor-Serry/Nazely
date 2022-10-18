package noor.serry.nazely.ui.main

import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import noor.serry.nazely.R

class CustomDownloadManager(var context: Context){

   private val  downloadManager =
       context.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager

     fun download (URL : String,name : String):Long  {
        val request =
            DownloadManager.Request(Uri.parse(URL))
                .setTitle("Download ... ")
                .setDescription(context.getString(R.string.app_description))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,name)

        return downloadManager.enqueue(request)
    }

    fun getCursor(downloadId : Long):Cursor{
        var query = DownloadManager.Query().setFilterById(downloadId)
        return downloadManager.query(query)
    }



}