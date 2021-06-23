package com.example.practicalapp.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.practicalapp.R
import com.example.practicalapp.util.Constants
import com.example.practicalapp.util.MessageDialog
import java.util.*

@Suppress("DEPRECATION")
open class BaseActivity : AppCompatActivity() {

    lateinit var appContext: Context

    internal var dialog: Dialog? = null
    var configuration: Configuration? = null
    val msgDialog = MessageDialog.getInstance()!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    open fun restartActivity(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.finish()
            activity.startActivity(activity.intent)
        } else {
            activity.finish()
            activity.startActivity(activity.intent)
        }
    }

    fun setStatusBar(color: Int, isTextColorWhite: Boolean? = false) {
        if (isTextColorWhite!!) {
            getWindow().getDecorView().setSystemUiVisibility(
                getWindow().getDecorView()
                    .getSystemUiVisibility() and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            ) //set status text  light
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color)
        }
    }

    @Synchronized
    fun showLoading() {

        if (dialog == null) {
            dialog = Dialog(this)
        }
        dialog!!.setContentView(R.layout.progress_loader)
        // ((TextView) dialog.findViewById(R.id.tvMsg)).setText(getString(R.string.text_please_wait));
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog!!.show()

    }

    fun hideLoading() {

        if ((dialog != null) and dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    fun replaceFragment(
        @NonNull fragment: Fragment,
        backStackName: Boolean = false,
        popStack: Boolean = false,
        clearAll: Boolean = false,
        @IdRes containerViewId: Int = R.id.main_content
    ) {
        Log.e("DSfsdfsdsdf", "" + popStack)
        var transition = supportFragmentManager.beginTransaction()
        /* transition.setCustomAnimations(
             R.anim.slide_in_from_right,
             R.anim.slide_out_from_left,
             R.anim.slide_in_from_left,
             R.anim.slide_out_from_right
         )*/
        transition.setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        /* if (clearAllFragment) {
             supportFragmentManager.popBackStack(
                 null,
                 FragmentManager.POP_BACK_STACK_INCLUSIVE
             )
             supportFragmentManager.executePendingTransactions()
         }*/
        if (popStack) {
            supportFragmentManager.popBackStack()
        }

        if (backStackName) {
            transition.addToBackStack("")
        }

        if (clearAll) {
            supportFragmentManager.popBackStack(
                "",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }

        transition.replace(containerViewId, fragment).commit()

    }

    fun addFragmentWithoutAnimation(
        @NonNull fragment: Fragment,
        backStackName: Boolean = false,
        popStack: Boolean = false,
        clearAll: Boolean = false,
        @IdRes containerViewId: Int = R.id.main_content
    ) {
        Log.e("DSfsdfsdsdf", "" + popStack)
        var transition = supportFragmentManager.beginTransaction()
        /* transition.setCustomAnimations(
             R.anim.slide_in_from_right,
             R.anim.slide_out_from_left,
             R.anim.slide_in_from_left,
             R.anim.slide_out_from_right
         )*/
        /* if (clearAllFragment) {
             supportFragmentManager.popBackStack(
                 null,
                 FragmentManager.POP_BACK_STACK_INCLUSIVE
             )
             supportFragmentManager.executePendingTransactions()
         }*/
        if (popStack) {
            supportFragmentManager.popBackStack()
        }


        if (backStackName) {
            transition.addToBackStack("")
        }

        if (clearAll) {
            supportFragmentManager.popBackStack(
                "",
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }

        transition.add(containerViewId, fragment).commit()

    }

    fun addFragment(
        @NonNull fragment: Fragment,
        backStackName: Boolean = false,
        aTAG: String = "",
        popBackStack: Boolean = false,
        @IdRes containerViewId: Int = R.id.main_content
    ) {
        /*supportFragmentManager
            .beginTransaction()
            .add(containerViewId, fragment)
            .commit()*/

        var transition = supportFragmentManager.beginTransaction()
        transition.setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )/* transition.setCustomAnimations(
            R.anim.slide_in_from_right,
            R.anim.slide_out_from_left,
            R.anim.slide_in_from_left,
            R.anim.slide_out_from_right
        )*/
        /*if (backStackName)
            transition.addToBackStack(aTAG)*/
        Log.e("aTAG", "aTAG==>" + aTAG)
        transition.addToBackStack(aTAG)

        if (popBackStack) {
            supportFragmentManager.popBackStack()
        }

        transition.add(containerViewId, fragment, aTAG).commitAllowingStateLoss()
    }

    open fun setLocaleAsPerLanguage(selecedLangCode: String) {
        var locale = Locale(selecedLangCode.toLowerCase())
        val resources: Resources = resources
        val configuration: Configuration = resources.getConfiguration()
        val displayMetrics: DisplayMetrics = resources.getDisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale)
        } else {
            configuration.locale = locale
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            applicationContext.createConfigurationContext(configuration)
        } else {
            resources.updateConfiguration(configuration, displayMetrics)
        }
    }

    fun msgDialog(msg: String, dialogType: String? = Constants.Param.ERROR) {
        var dialogMsg = MessageDialog.getInstance()
        val bundle = Bundle()
        bundle.putString("tvMsgText", msg)
        bundle.putString("okTxt", "OK")
        bundle.putString("msgType", "" + dialogType)
        dialogMsg.arguments = bundle
//        if (dialogType.equals(Constants.Param.ERROR)) {
//            setStatusBar(ContextCompat.getColor(baseContext!!, R.color.colorPrimary))
//        } else {
//            setStatusBar(ContextCompat.getColor(baseContext!!, R.color.colorPrimary))
//        }
        if (dialogMsg.isAdded) {
            return
        }
        dialogMsg.show(supportFragmentManager, "")
        /*timer = object : CountDownTimer(3500, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
//                setStatusBar(ContextCompat.getColor(baseContext!!, R.color.transparent))
                timer.cancel()
                if (dialogMsg.isVisible)
                    dialogMsg!!.dismiss()
            }
        }.start()*/
        dialogMsg.setListener(object : MessageDialog.OnClicks {
            override fun set(ok: Boolean) {
                dialogMsg.dismiss()
                /*if (dialogMsg.isVisible)
                    timer!!.cancel()
*/
            }
        })
    }


    fun shareKit(context: Context, shareBodyText: String, subject: String, SharingOption: String) {

        val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject)
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText)
        context.startActivity(Intent.createChooser(sharingIntent, SharingOption))

    }


    fun clearAllFragment() {
        val fm = this.supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


    /*if (baseContext!! is MainActivity) {
        if ((baseContext!! as MainActivity).resideMenu.isOpened) {
            (baseContext!! as MainActivity).resideMenu.closeMenu()
        }
    }*/
    fun showInternetDialog() {
        val msgDialog = MessageDialog.getInstance()
        val bundle = Bundle()
        bundle.putString(
            "tvMsgText",
            "Seems like you don't have an active internet connection. Please check your connection and try again."
        )
        bundle.putString("okTxt", "OK")
        bundle.putString("msgType", "" + Constants.Param.ERROR)
        msgDialog.setArguments(bundle)
        msgDialog.show(supportFragmentManager, "")
        /* timer = object : CountDownTimer(3500, 1000) {
             override fun onTick(millisUntilFinished: Long) {
             }

             override fun onFinish() {
                 timer.cancel()
                 if (msgDialog.isVisible)
                     msgDialog!!.dismiss()
             }
         }.start()*/
        msgDialog.setListener(object : MessageDialog.OnClicks {
            override fun set(ok: Boolean) {
                msgDialog.dismiss()
                /*    if (msgDialog.isVisible) {
                        timer.cancel()
                    }*/
            }


        })

    }

    fun showMessageDialog(msg: String, isConfirm: Boolean = false) {
        val bundle = Bundle()
        bundle.putString("tvMsgText", msg)
        if (isConfirm) {
            bundle.putString(
                "okTxt",
                "Yes"/*repo!!.labelPref.getValue(PrefKeys.BTN_GLOBAL_YES)*/
            )
            bundle.putString(
                "cancelTxt",
                "No"/*repo!!.labelPref.getValue(PrefKeys.BTN_GLOBAL_NO)*/
            )
        } else {
            bundle.putString("okTxt", "Ok"/*repo!!.labelPref.getValue(PrefKeys.BTN_GLOBAL_OK)*/)
        }

        msgDialog.arguments = bundle
        if (msgDialog.isAdded)
            msgDialog.dismiss()

        if (!this.isFinishing) {
            msgDialog.show(this.supportFragmentManager, "")
        }
    }


}