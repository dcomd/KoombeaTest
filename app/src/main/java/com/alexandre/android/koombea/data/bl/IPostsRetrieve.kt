package com.alexandre.android.koombea.data.bl

import com.alexandre.android.koombea.data.OperationResult
import com.alexandre.android.koombea.data.models.KoombeaPostResponse

interface IPostsRetrieve {
    suspend fun getPosts(): OperationResult<KoombeaPostResponse>
}