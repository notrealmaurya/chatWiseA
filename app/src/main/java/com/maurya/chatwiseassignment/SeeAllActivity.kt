package com.maurya.chatwiseassignment

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.maurya.chatwiseassignment.adapter.AdapterProductSeeAll
import com.maurya.chatwiseassignment.databinding.ActivitySeeAllBinding
import com.maurya.chatwiseassignment.repository.ModelResult
import com.maurya.chatwiseassignment.repository.Product
import com.maurya.chatwiseassignment.repository.ViewModelObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeeAllActivity : AppCompatActivity() {

    private lateinit var activitySeeAllBinding: ActivitySeeAllBinding
    private lateinit var adapterProductSeeAll: AdapterProductSeeAll

    companion object {
        var seeAllProductList: ArrayList<Product> = arrayListOf()
    }

    private lateinit var viewModel: ViewModelObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySeeAllBinding = ActivitySeeAllBinding.inflate(layoutInflater)
        setContentView(activitySeeAllBinding.root)

        viewModel = ViewModelProvider(this)[ViewModelObserver::class.java]

        activitySeeAllBinding.seeAllNameSeeAllActivity.text = "All Products"
        activitySeeAllBinding.recyclerViewSeeAllActivity.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(13)
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapterProductSeeAll = AdapterProductSeeAll(
                context,
                seeAllProductList
            )
            adapter = adapterProductSeeAll
        }


        fetchItemsSeeAll(30)

        activitySeeAllBinding.retryButtonSeeAllActivity.setOnClickListener {
            fetchItemsSeeAll(30)
        }
        activitySeeAllBinding.backSeeAllActivity.setOnClickListener {
            finish()
        }
    }

    private fun fetchItemsSeeAll(maxResults: Int) {
        viewModel.fetchProductItem(this, maxResults)
        this.lifecycleScope.launch {
            this@SeeAllActivity.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productStateFLow.collect {
                    when (it) {
                        is ModelResult.Success -> {
                            activitySeeAllBinding.progressBarSeeAllActivity.visibility =
                                View.GONE
                            activitySeeAllBinding.retryLayoutSeeAllActivity.visibility =
                                View.GONE
                            activitySeeAllBinding.recyclerViewSeeAllActivity.visibility =
                                View.VISIBLE
                            seeAllProductList.clear()
                            seeAllProductList.addAll(it.data!!)
                            adapterProductSeeAll.notifyDataSetChanged()
                        }

                        is ModelResult.Error -> {
                            activitySeeAllBinding.progressBarSeeAllActivity.visibility =
                                View.GONE
                            activitySeeAllBinding.retryLayoutSeeAllActivity.visibility =
                                View.VISIBLE
                            activitySeeAllBinding.recyclerViewSeeAllActivity.visibility =
                                View.GONE
                            showToast(this@SeeAllActivity, it.message.toString())
                        }

                        is ModelResult.Loading -> {
                            activitySeeAllBinding.progressBarSeeAllActivity.visibility =
                                View.VISIBLE
                            activitySeeAllBinding.retryLayoutSeeAllActivity.visibility =
                                View.GONE
                        }

                        else -> {}
                    }
                }
            }
        }
    }

}
