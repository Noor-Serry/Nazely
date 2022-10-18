package noor.serry.nazely.utils

object Constants {
     const val SOUND_URL =
        "https://cdn.islamic.network/quran/audio/128/ar.alafasy/7.mp3"
     const val VIDEO_URL =
        "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1"
     const val UDACITY_URL =
        "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
     const val CHANNEL_ID = "channelId"

    fun convertStatusNumberToString(statusNumber : Int):String{
      return  when(statusNumber){
            16 ->  "Failed"
            8 -> "Successful"
            else -> "Unknown"
        }

    }


}