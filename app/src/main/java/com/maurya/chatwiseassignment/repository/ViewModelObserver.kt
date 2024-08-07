package com.maurya.chatwiseassignment.repository

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maurya.chatwiseassignment.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelObserver @Inject constructor(private val repository: Repository) :
    ViewModel() {

    val productStateFLow get() = repository.productStateFlow


    fun fetchProductItem(context: Context, limit: Int) {
        viewModelScope.launch {
            try {
                repository.getAajKeDarshanItem(context, limit)
            } catch (e: Exception) {
                showToast(context, e.message.toString())
            }
        }
    }


}