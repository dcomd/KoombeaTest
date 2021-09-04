package com.alexandre.android.koombea.utils

import android.app.Activity
import android.util.DisplayMetrics
import android.view.View
import com.couchbase.lite.Document
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import com.alexandre.android.koombea.KApplication
import com.alexandre.android.koombea.data.local.DatabaseManager
import com.alexandre.android.koombea.data.models.KoombeaPostResponse
import java.lang.Integer.parseInt
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


object Utils {
    fun saveLocalData(data: String){
        val databaseManager = DatabaseManager.getSharedInstance()
        databaseManager?.openOrCreateDatabaseForUser(KApplication.applicationContext(), "db")

        val map: HashMap<String, Any> = HashMap<String, Any>()
        map["data"] = data
        databaseManager?.saveDocument(map)
    }

    fun getLocalDocument(): KoombeaPostResponse?{
        val databaseManager = DatabaseManager.getSharedInstance()
        databaseManager?.openOrCreateDatabaseForUser(KApplication.applicationContext(), "db")
        val document: Document? = databaseManager?.fetchDocument("0")
        var response: KoombeaPostResponse? = null
        document?.let {
            response = Gson().fromJson(it.getString("data"), KoombeaPostResponse::class.java)
        }

        return response!!
    }

    fun shimmerStart(shimmer: ShimmerFrameLayout){
        shimmer.startShimmerAnimation()
        shimmer.visibility = View.VISIBLE
    }

    fun shimmerStop(shimmer: ShimmerFrameLayout){
        shimmer.stopShimmerAnimation()
        shimmer.visibility = View.GONE
    }

    fun parseDate(
            inputDateString: String?,
            inputFormat: String,
            outputFormat: String
    ): String? {
        val inputDateFormat = SimpleDateFormat(inputFormat, Locale.US)
        val outputDateFormat = SimpleDateFormat(outputFormat, Locale.US)
        var date: Date? = null
        var outputDateString: String? = null
        try {
            date = inputDateFormat.parse(inputDateString)
            outputDateString = outputDateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDateString + getDayOfMonthSuffix(parseInt(outputDateString?.last().toString()))
    }

    fun resizeImage(view: View): Int{
        val displayMetrics = DisplayMetrics()
        (view.context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        return (displayMetrics.widthPixels / 2) - 48
    }

    private fun getDayOfMonthSuffix(n: Int): String {
        return  when (n) {
            1 -> "st"
            2 -> "nd"
            3 -> "rd"
            else -> "th"
        }
    }
}