package com.maurya.chatwiseassignment

import android.widget.Toast
import kotlinx.coroutines.withContext
import android.content.Context
import com.maurya.chatwiseassignment.api.RetrofitService
import com.maurya.chatwiseassignment.repository.Product
import kotlinx.coroutines.Dispatchers



//toast
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

suspend fun fetchProductItem(
    context: Context,
    maxResults: Int
): ArrayList<Product> {
    val itemList: ArrayList<Product> = arrayListOf()

    return withContext(Dispatchers.IO) {
        try {
            val response = RetrofitService.apiService.getProducts()
            itemList.clear()
            itemList.addAll(response.products.take(maxResults))
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                showToast(context, e.message.toString())
                throw e
            }
        }
        itemList
    }
}

