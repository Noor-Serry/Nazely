package noor.serry.nazely.ui.main

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CustomRunnable(context: Context,var downloadID : Long,var downloadListener: DownloadListener)  {
    private var stillWorking = false
    private val timeDelay : Long = 100
    private val  downloadManager =
        context.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
    lateinit var cursor : Cursor


    fun startListener(){
        stillWorking = true
        postDelayed()
    }

    private fun postDelayed(){
        if(!stillWorking)return
        GlobalScope.launch(Dispatchers.Default) {
            getNewAngel()
            Thread.sleep(timeDelay)
            postDelayed()
        }
    }

    @SuppressLint("Range")
    private fun getNewAngel(){
        cursor = getCursor()
        cursor.moveToNext()
        downloadListener.upDateCanvas(calcAngelOfCircle())
        cursor.close()
    }

    private fun calcAngelOfCircle():Float{
        return (getBytesDownloadSoFar()/getFileSizeAsBytes().toFloat())*360.0f
    }

    private fun getFileSizeAsBytes():Int{
        val columnName = DownloadManager.COLUMN_TOTAL_SIZE_BYTES
        val columnIndex =cursor.getColumnIndex(columnName)
        return cursor.getInt(columnIndex)
    }

    @SuppressLint("Range")
    private fun getBytesDownloadSoFar():Int{
        val columnName = DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR
        val columnIndex =cursor.getColumnIndex(columnName)
        return cursor.getInt(columnIndex)
    }

    @JvmName("getCursor1")
    private fun getCursor(): Cursor{
        val query = DownloadManager.Query().setFilterById(downloadID)
        return  downloadManager.query(query)
    }

    fun stopListener(){
        stillWorking = false
    }

}