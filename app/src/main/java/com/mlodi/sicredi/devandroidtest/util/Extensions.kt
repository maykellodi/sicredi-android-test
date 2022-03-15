package com.mlodi.sicredi.devandroidtest.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.mlodi.sicredi.devandroidtest.R
import com.mlodi.sicredi.devandroidtest.ui.util.Constants.EMAIL_PATTERN
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Long.asDateString(): String{
    val date = Date(this)
    val dateFormat = SimpleDateFormat("dd/MM/yyyy ' ' HH:mm")
    return dateFormat.format(date)
}

fun Double.asMoneyString(): String{
    val deviceLocale =  Locale.getDefault()
    return NumberFormat.getCurrencyInstance(deviceLocale).format(this)
}

fun String.isValidEmail(): Boolean{
    return this.matches(EMAIL_PATTERN.toRegex())
}

fun Context.showGenericError(onDismissClicked: (() -> Unit)? = null){
    val builder = AlertDialog.Builder(this).apply {
        setTitle(R.string.error_generic_title_text)
        setMessage(R.string.error_generic_desc_text)
        setNeutralButton(R.string.error_generic_dismiss_btn_text) { dialog, _ ->
            onDismissClicked?.invoke()
            dialog.dismiss()
        }
    }

    builder.create().show()
}