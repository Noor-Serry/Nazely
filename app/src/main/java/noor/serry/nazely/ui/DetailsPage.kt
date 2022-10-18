package noor.serry.nazely.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import noor.serry.nazely.R
import noor.serry.nazely.databinding.ActivityDetailsPageBinding
import noor.serry.nazely.ui.main.MainActivity

class DetailsPage : AppCompatActivity() {

      lateinit var binding: ActivityDetailsPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details_page)
       setDataForViews()
    }

    private fun setDataForViews(){
        var data = intent
        binding.fileName = data.getStringExtra("fileName")
        binding.status = data.getStringExtra("status")
    }

    override fun onStart() {
        super.onStart()
        binding.button.setOnClickListener(this::goToMainActivity)
    }

    private fun goToMainActivity(view: View){
        var intent = Intent( this, MainActivity::class.java)
        startActivity(intent)
    }
}