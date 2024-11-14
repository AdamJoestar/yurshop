package com.example.yurshop
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appNameTextView = findViewById<TextView>(R.id.appNameTextView)
        val text = "Yurshop"
        val spannableString = SpannableString(text)
        val colorGreen = ContextCompat.getColor(this, R.color.green)
        spannableString.setSpan(
            ForegroundColorSpan(colorGreen),
            0,
            3,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        appNameTextView.text = spannableString

        val bottomNavigationBar = findViewById<BottomNavigationView>(R.id.bottomNavigationBar)
        bottomNavigationBar?.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.cart -> {
                    // Handle cart click
                    true
                }
                R.id.home -> {
                    // Handle home click
                    true
                }
                R.id.profile -> {
                    // Handle profile click
                    true
                }
                else -> false
            }
        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationBar)
        bottomNavigationView.setBackgroundColor(ContextCompat.getColor(this, R.color.green))

        val popularTextView = findViewById<TextView>(R.id.popularTextView)
        val paketTextView = findViewById<TextView>(R.id.paketTextView)

        popularTextView.setOnClickListener {
            // Handle Popular click
            // ...
        }

        paketTextView.setOnClickListener {
            // Handle Paket click
            // ...
        }

        viewPager = findViewById(R.id.viewPager)

        val images = listOf(R.drawable.carousel1) // Ganti dengan gambar Anda
        val adapter = CarouselAdapter(images)
        viewPager.adapter = adapter

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            val currentItem = viewPager.currentItem
            viewPager.currentItem = (currentItem + 1) % adapter.itemCount
        }


        // Mulai auto scroll
        startAutoScroll()

    }

    private fun startAutoScroll() {
        handler.postDelayed(runnable, 5000) // 5 detik
    }

    private fun stopAutoScroll() {
        handler.removeCallbacks(runnable)
    }

    override fun onPause() {
        super.onPause()
        stopAutoScroll()
    }

    override fun onResume() {
        super.onResume()
        startAutoScroll()
    }
}
class CarouselAdapter(private val images: List<Int>) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {

    inner class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position])
    }

    override fun getItemCount(): Int = images.size
}
