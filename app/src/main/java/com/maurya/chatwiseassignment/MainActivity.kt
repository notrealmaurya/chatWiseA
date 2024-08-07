package com.maurya.chatwiseassignment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.maurya.chatwiseassignment.adapter.AdapterProduct
import com.maurya.chatwiseassignment.databinding.ActivityMainBinding
import com.maurya.chatwiseassignment.repository.ModelResult
import com.maurya.chatwiseassignment.repository.Product
import com.maurya.chatwiseassignment.repository.ViewModelObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var adapterHome: AdapterProduct

    companion object {
        var homeList: ArrayList<Product> = arrayListOf()
    }

    private lateinit var viewModel: ViewModelObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)


        viewModel = ViewModelProvider(this)[ViewModelObserver::class.java]

        activityMainBinding.recyclerViewMain.apply {
            setHasFixedSize(true)
            setItemViewCacheSize(13)
            adapterHome = AdapterProduct(
                this@MainActivity,
                homeList
            )
            adapter = adapterHome
        }

        fetchItemsHome(4)

        activityMainBinding.retryButtonMain.setOnClickListener {
            fetchItemsHome(4)
        }

        activityMainBinding.seeAlMain.setOnClickListener {
            val intent = Intent(this, SeeAllActivity::class.java)
            this.startActivity(intent)
        }

    }

    private fun fetchItemsHome(maxResults: Int) {
        viewModel.fetchProductItem(this, maxResults)
        this.lifecycleScope.launch {
            this@MainActivity.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productStateFLow.collect {
                    when (it) {
                        is ModelResult.Success -> {
                            activityMainBinding.progressBarMain.visibility =
                                View.GONE
                            activityMainBinding.retryLayoutMain.visibility =
                                View.GONE
                            activityMainBinding.recyclerViewMain.visibility =
                                View.VISIBLE
                            homeList.clear()
                            homeList.addAll(it.data!!)
                            adapterHome.notifyDataSetChanged()
                        }

                        is ModelResult.Error -> {
                            activityMainBinding.progressBarMain.visibility =
                                View.GONE
                            activityMainBinding.retryLayoutMain.visibility =
                                View.VISIBLE
                            activityMainBinding.recyclerViewMain.visibility =
                                View.GONE
                            showToast(this@MainActivity, it.message.toString())
                        }

                        is ModelResult.Loading -> {
                            activityMainBinding.progressBarMain.visibility =
                                View.VISIBLE
                            activityMainBinding.retryLayoutMain.visibility =
                                View.GONE
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}

