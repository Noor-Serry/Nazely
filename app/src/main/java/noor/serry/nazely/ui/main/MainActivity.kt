package noor.serry.nazely.ui.main

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.*
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import noor.serry.nazely.*
import noor.serry.nazely.databinding.ActivityMainBinding
import noor.serry.nazely.ui.DetailsPage
import noor.serry.nazely.utils.Constants.CHANNEL_ID
import noor.serry.nazely.utils.Constants.SOUND_URL
import noor.serry.nazely.utils.Constants.UDACITY_URL
import noor.serry.nazely.utils.Constants.VIDEO_URL
import noor.serry.nazely.utils.Constants.convertStatusNumberToString


class MainActivity : AppCompatActivity() , DownloadListener {
    private lateinit var notificationManager : NotificationManager
    lateinit var binding : ActivityMainBinding
    private lateinit var customDownloadManager : CustomDownloadManager
    lateinit var customRunnable : CustomRunnable
    private var downloadID :Long = 10
    private var fileName : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        customDownloadManager = CustomDownloadManager(baseContext)
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadID==id){
                customRunnable.stopListener()
                showNotification()
                changeButtonViewAfterDownload()
            }}
    }

    private fun changeButtonViewAfterDownload(){
        binding.downloadButton.text = getString(R.string.downloadButton)
        binding.downloadButton.removeDraw()
    }

    private fun showNotification(){
        createNotificationChannel()
        buildNotification()
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "Download channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            var notificationChannel = NotificationChannel( CHANNEL_ID, channelName, importance )
            notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun buildNotification(){
        var builder = NotificationCompat.Builder(baseContext, CHANNEL_ID)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_description))
            .setSmallIcon(R.drawable.ic_assistant_black_24dp)
            .addAction(0,getString(R.string.checkStatus),preparationPendingIntent())
        var notificationManagerCompat = NotificationManagerCompat.from(this)
        notificationManagerCompat.notify(10,builder.build())
    }

    private fun preparationPendingIntent():PendingIntent{
        val intent = Intent(this , DetailsPage::class.java)
        intent.putExtra("fileName",fileName)
        intent.putExtra("status",getDownloadStatus())
        return PendingIntent.getActivity(this,0,intent,0)

    }

    private fun getDownloadStatus():String{
        var cursor = customDownloadManager.getCursor(downloadID)
        cursor.moveToNext()
        var columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
        return convertStatusNumberToString(cursor.getInt(columnIndex))
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onStart() {
        super.onStart()
        binding.downloadButton.setOnClickListener(this::onClickDownloadListener)
    }

    private fun onClickDownloadListener(view : View){
          selectTheDownloadFile()
    }

    private fun selectTheDownloadFile() {
         when(binding.radioGroup.checkedRadioButtonId){
            R.id.radioButton1 -> {
                downloadID = customDownloadManager.download(UDACITY_URL,"Udacity file")
                fileName = getString(R.string.firstRadioButtonText)
                startDownload()}
            R.id.radioButton2 -> {
                downloadID = customDownloadManager.download(VIDEO_URL,"Video")
                fileName = getString(R.string.secondRadioButtonText)
                startDownload()}
            R.id.radioButton3 -> {
                downloadID = customDownloadManager.download(SOUND_URL,"Sound")
                fileName = getString(R.string.thirdRadioButtonText)
                startDownload()}
            else -> {
                Toast.makeText(this, getText(R.string.didNotSelectedFile), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startDownload(){
         changeDownloadButtonViewWhenStart()
         startDownloadListener()
    }

    private fun changeDownloadButtonViewWhenStart(){
        binding.downloadButton.text = getString(R.string.button_loading)
    }

    private fun startDownloadListener(){
        customRunnable = CustomRunnable(baseContext,downloadID,this)
        customRunnable.startListener()
    }

    override fun upDateCanvas(angel: Float) {
        binding.downloadButton.addNewAngel(angel)
    }



}


