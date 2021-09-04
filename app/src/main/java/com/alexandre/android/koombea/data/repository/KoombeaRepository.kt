package com.alexandre.android.koombea.data.repository

import android.util.Log
import com.alexandre.android.koombea.data.bl.IPostsRetrieve
import com.google.gson.Gson
import com.alexandre.android.koombea.KApplication
import com.alexandre.android.koombea.data.OperationResult
import com.alexandre.android.koombea.data.models.KoombeaPostResponse
import com.alexandre.android.koombea.data.remote.ApiClient
import com.alexandre.android.koombea.utils.NetManager
import com.alexandre.android.koombea.utils.Utils
import java.lang.Exception

class KoombeaRepository: IPostsRetrieve {

    override suspend fun getPosts(): OperationResult<KoombeaPostResponse> {
        val isNetWorkAvailable = NetManager(KApplication.applicationContext())
        if(isNetWorkAvailable.isConnectedToInternet!!){
            try {
                val result = ApiClient.build().getPosts()
                result.let {
                    return if (it.isSuccessful && it.body() != null) {
                        val data = it.body()
                        Utils.saveLocalData(Gson().toJson(data))
                        OperationResult.Success(data)
                    } else {
                        val message = it.body()?.statusMessage
                        OperationResult.Error(Exception(message))
                    }
                }
            }catch (e: Exception){
                Log.e("ExceptionRepo", e.message)
                return OperationResult.Error(e)
            }

        }else{
            val local = Utils.getLocalDocument()
            local?.let {
                return OperationResult.Success(local)
            }?: run {
                Log.e("ExceptionRepo", "Error reading database")
                return OperationResult.Error(Exception("Error reading database"))
            }
        }
    }
}