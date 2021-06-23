package com.example.practicalapp.base



import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.example.practicalapp.R
import com.example.practicalapp.util.Constants
import com.example.practicalapp.util.MessageDialog
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


open class BaseFragment : Fragment() {

    public var baseActivity: BaseActivity? = null
    val msgDialog = MessageDialog.getInstance()!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = activity as BaseActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    fun showLoading() {
        if (activity != null) {
            (activity as BaseActivity).showLoading()
        }
    }
    fun hideLoading() {
        if (activity != null) {
            (activity as BaseActivity).hideLoading()
        }
    }



    fun setLocaleAsPerLanguage(msg: String) {
        baseActivity?.setLocaleAsPerLanguage(msg)
    }

   /* fun notificationClickEvent() {
        baseActivity?.notificationClickEvent()
    }*/

    fun setStatusBar(color: Int, isTextColorWhite: Boolean? = false) {
        baseActivity?.setStatusBar(color, isTextColorWhite)
    }


    fun msgDialog(msg: String, dialogTye: String? = Constants.Param.ERROR) {
        if (activity != null) {
            (activity as BaseActivity).msgDialog(msg, dialogTye)
        }
    }

    fun clearAllFragment() {
        baseActivity?.clearAllFragment()
    }

    fun shareKit(
        context: Context,
        shareBodyText: String,
        subject: String,
        SharingOption: String
    ) {
        baseActivity?.shareKit(context, shareBodyText, subject, SharingOption)
    }



    fun replaceFragment(
        @NonNull fragment: Fragment,
        backStackName: Boolean = false,
        popStack: Boolean = false,
        clearAll: Boolean = false,
        @IdRes containerViewId: Int = R.id.main_content
    ) {

        Log.e("SDfjkjksdhkfjsf", "" + popStack)
        baseActivity!!.replaceFragment(
            fragment,
            backStackName,
            popStack,
            clearAll,
            containerViewId
        )
    }

    fun addFragment(
        @NonNull fragment: Fragment,
        backStackName: Boolean = false,
        aTAG: String = "",
        popBackstack: Boolean = false,
        @IdRes containerViewId: Int = R.id.main_content

    ) {
        baseActivity!!.addFragment(fragment, backStackName, aTAG, popBackstack, containerViewId)
    }


    // Find the distance  on lat  and log
    open fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = (Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + (Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta))))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

    open fun getCurrentDate():String{
        val c: Date = Calendar.getInstance().time
        println("Current time => $c")
        val df = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val currentDate: String = df.format(c)


        return currentDate;
    }

    open fun changeDateFormatYYYY(date: String?,inputFormat:String,outputFormat:String): String? {
        var outputDate: String? = ""
        if (date != null && !date.equals("", ignoreCase = true)) {
            val input = SimpleDateFormat(inputFormat, Locale.ENGLISH)
            val output = SimpleDateFormat(outputFormat, Locale.getDefault())
            try {
                val finalDate = input.parse(date)
                Log.e("", "datenewinfunction : $date") // format output
                outputDate = output.format(finalDate)
            } catch (e: ParseException) {
                outputDate = date
                e.printStackTrace()
            }
        }
        return outputDate
    }



}