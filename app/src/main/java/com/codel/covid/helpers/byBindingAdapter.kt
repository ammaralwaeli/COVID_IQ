package com.codel.covid.helpers

import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.robinhood.ticker.TickerUtils
import com.robinhood.ticker.TickerView
import java.text.DecimalFormat
import java.util.*


@BindingAdapter("android:setEnglishPrice")
fun setPriceEnglish(textView: TextView, price: String) {
    val p=Integer.parseInt(price)
    textView.text=String.format(Locale.ENGLISH, "%s", DecimalFormat("#,###").format(p))
}

@BindingAdapter("android:srcBitmap")
fun loadImage(iv: ImageView, bitmap: Bitmap?) {
    iv.setImageBitmap(bitmap)
}

@BindingAdapter("android:srcUrl")
fun loadImageUrl(iv: ImageView, url: String) {
    Glide
        .with(iv.context)
        .load(url)
        .into(iv)
}



