package com.maurya.chatwiseassignment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.maurya.chatwiseassignment.MainActivity.Companion.homeList
import com.maurya.chatwiseassignment.SeeAllActivity.Companion.seeAllProductList
import com.maurya.chatwiseassignment.databinding.ActivityProductDetailsBinding
import com.maurya.chatwiseassignment.repository.Product

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val position = intent.getIntExtra("position", -1)
        val clasS = intent.getStringExtra("class")

        if (clasS == "Main") {
            if (position != -1) {
                val product = homeList.getOrNull(position)
                product?.let {
                    bindProductDetails(it)
                }
            }
        } else if (clasS == "SeeAll") {
            if (position != -1) {
                val product = seeAllProductList.getOrNull(position)
                product?.let {
                    bindProductDetails(it)
                }
            }

        }

        binding.backDetailslActivity.setOnClickListener {
            finish()
        }

    }

    private fun bindProductDetails(product: Product) {
        binding.textViewTitle.text = product.title
        binding.textViewDescription.text = product.description
        binding.textViewPrice.text = "Price: $${product.price}"
        binding.textViewRating.text = "Rating: ${product.rating}"
        binding.textViewCategory.text = "Category: ${product.category}"
        binding.textViewStock.text = "Stock: ${product.stock}"
        binding.textViewBrand.text = "Brand: ${product.brand}"
        binding.textViewSku.text = "SKU: ${product.sku}"
        binding.textViewWeight.text = "Weight: ${product.weight}"
        binding.textViewDimensions.text =
            "Dimensions: ${product.dimensions.width} x ${product.dimensions.height} x ${product.dimensions.depth}"
        binding.textViewWarranty.text = "Warranty: ${product.warrantyInformation}"
        binding.textViewShipping.text = "Shipping: ${product.shippingInformation}"
        binding.textViewAvailability.text = "Availability: ${product.availabilityStatus}"
        binding.textViewReturnPolicy.text = "Return Policy: ${product.returnPolicy}"
        binding.textViewMinOrderQuantity.text =
            "Min Order Quantity: ${product.minimumOrderQuantity}"

        Glide.with(this)
            .load(product.thumbnail)
            .into(binding.imageViewProduct)
    }

}


