package com.alexandre.android.koombea.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexandre.android.koombea.data.OperationResult
import com.alexandre.android.koombea.data.bl.IPostsRetrieve
import com.alexandre.android.koombea.data.models.KoombeaPostResponse
import com.alexandre.android.koombea.data.models.KoombeaUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KoombeaViewModel(private val postsRepository: IPostsRetrieve): ViewModel() {
    private val _postsList = MutableLiveData<MutableList<KoombeaUser>>().apply { value = arrayListOf() }
    val postList: LiveData<MutableList<KoombeaUser>> = _postsList

    private val _isViewLoading= MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    fun loadPosts(){
        _isViewLoading.postValue(true)
        viewModelScope.launch {
            val resultList: OperationResult<KoombeaPostResponse> = withContext(Dispatchers.IO){
                postsRepository.getPosts()
            }
            _isViewLoading.postValue(false)
            when(resultList){
                is OperationResult.Success -> {
                    _postsList.value = resultList.data?.data as MutableList<KoombeaUser>
                }

                is  OperationResult.Error -> {
                    Log.e("ErrorPost", "OperationResult.error")
                }
            }
        }
    }
}