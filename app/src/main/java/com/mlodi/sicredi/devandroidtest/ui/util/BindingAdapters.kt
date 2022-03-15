package com.mlodi.sicredi.devandroidtest.ui.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.mlodi.sicredi.devandroidtest.R
import com.mlodi.sicredi.devandroidtest.util.asDateString
import com.mlodi.sicredi.devandroidtest.util.asMoneyString

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?){
    imageUrl?.let{
        Glide.with(imageView.context)
            .load(it)
            .placeholder(R.drawable.ic_no_image)
            .error(R.drawable.ic_no_image)
            .into(imageView)
    }
}

@BindingAdapter("dateText")
fun formatDatetimeMillis(textView: TextView, datetime: Long){
    textView.text = datetime.asDateString()
}

@BindingAdapter("moneyText")
fun formatDatetimeMillis(textView: TextView, money: Double){
    textView.text = money.asMoneyString()
}