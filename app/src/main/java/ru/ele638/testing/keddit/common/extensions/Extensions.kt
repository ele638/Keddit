@file:JvmName("ExtensionUtils")

package ru.ele638.testing.keddit.common.extensions

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.ele638.testing.keddit.R


fun ViewGroup.inflate(layoutID: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutID, this, attachToRoot)
}

fun ImageView.loadImg(url: String) {
    if (TextUtils.isEmpty(url)) {
        Picasso.with(context).load(R.mipmap.ic_launcher).into(this)
    } else {
        Picasso.with(context).load(url).into(this)
    }

}

inline fun <reified T : Parcelable> createParsel(
    crossinline createFromParcel: (Parcel) -> T?): Parcelable.Creator<T> =
        object : Parcelable.Creator<T>{
            override fun createFromParcel(source: Parcel?): T = createFromParcel(source)

            override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
        }

