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
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.with
import androidx.compose.foundation.layout.add
import androidx.compose.ui.semantics.text
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlin.collections.getValue
import kotlin.text.clear
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var productAdapter: ProductAdapter
    private val products = mutableListOf<Product>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.productRecyclerView)
        productAdapter = ProductAdapter(products)
        recyclerView.adapter = productAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch data from Firebase
        val database = Firebase.database
        val productsRef = database.getReference("products")

        productsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                products.clear()
                for (productSnapshot in dataSnapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java) // Now using your Product data class
                    product?.let { products.add(it) }
                }
                productAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })

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

data class Product(
    val name: String? = null,
    val stock: Int? = null,
    val rating: Float? = null,
    val imageUrl: String? = null // Add imageUrl property
)

class ProductAdapter(private val products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        val productStockTextView: TextView = itemView.findViewById(R.id.productStockTextView)
        val productRatingBar: RatingBar = itemView.findViewById(R.id.productRatingBar)
        val productImageView: ImageView = itemView.findViewById(R.id.productImageView) // Add productImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.productNameTextView.text = product.name
        holder.productStockTextView.text = "Stok: ${product.stock}"
        holder.productRatingBar.rating = product.rating ?: 0f

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .into(holder.productImageView)
    }


    override fun getItemCount(): Int = products.size
}

