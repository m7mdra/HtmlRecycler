package m7mdra.com.htmlrecycler.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import m7mdra.com.htmlrecycler.R
import m7mdra.com.htmlrecycler.ResizableImageView

class ImageViewHolder(val view: View) :RecyclerView.ViewHolder(view) {
    val image=view.findViewById<ResizableImageView>(R.id.image)
}