package com.maurya.chatwiseassignment.repository

import android.content.Context
import com.maurya.chatwiseassignment.fetchProductItem
import com.maurya.chatwiseassignment.repository.ModelResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class Repository @Inject constructor() {


    private val _productStateFlow =
        MutableStateFlow<ModelResult<ArrayList<Product>>>(ModelResult.Loading())
    val productStateFlow: StateFlow<ModelResult<ArrayList<Product>>> get() = _productStateFlow


    suspend fun getAajKeDarshanItem(context: Context, limit:Int) {
        _productStateFlow.emit(ModelResult.Loading())
        try {
            val fetchedItems = fetchProductItem(context, limit)
            if (fetchedItems.isEmpty()) {
                _productStateFlow.emit(ModelResult.Error("No Aaj Ke Darshan Image found"))
            } else {
                _productStateFlow.emit(ModelResult.Success(fetchedItems))
            }
            _productStateFlow.emit(ModelResult.Success(fetchedItems))
        } catch (e: Exception) {
            _productStateFlow.emit(ModelResult.Error("Failed to fetch images: ${e.message}"))
        }
    }

}



