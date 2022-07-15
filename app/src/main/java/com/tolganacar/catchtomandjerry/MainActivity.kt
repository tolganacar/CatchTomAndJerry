package com.tolganacar.catchtomandjerry

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.tolganacar.catchtomandjerry.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var score = 0
    var imageArray = ArrayList<ImageView>()
    var handler = Handler()
    var runnable = Runnable {  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        imageArray.add(binding.imageView1)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)

        for (image in imageArray){
            image.visibility = View.INVISIBLE
        }

    }

    fun start(view : View){
        binding.button.isEnabled = false
        hideImages()
        object : CountDownTimer(15500,1000){
            override fun onTick(p0: Long) {
                binding.textTime.text = "Time : " + p0/1000
            }

            override fun onFinish() {
                binding.button.isEnabled = true
                handler.removeCallbacks(runnable)

                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                binding.textTime.text = "Time : 0"

                val alert = AlertDialog.Builder(this@MainActivity)

                alert.setTitle("Game Over")
                alert.setMessage("Restart the game ?")

                alert.setPositiveButton("Yes") {dialog , which ->
                    val intent = intent
                    finish()
                    startActivity(intent)
                }

                alert.setNegativeButton("No") {dialog , which ->
                    Toast.makeText(this@MainActivity,"Game Over! Your Score : $score",Toast.LENGTH_LONG).show()
                }

                alert.show()
            }

        }.start()
    }

    fun hideImages(){

        runnable = object : Runnable{
            override fun run() {
                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                val random = Random()
                val randomNumber = random.nextInt(9)
                imageArray[randomNumber].visibility = View.VISIBLE

                handler.postDelayed(runnable,500)
            }
        }

        handler.post(runnable)
    }

    fun increaseScore(view : View){
        score++
        binding.textScore.text = "Score : $score"
    }
}