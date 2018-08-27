package m7mdra.com.htmlrecycler.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.VideoView
import m7mdra.com.htmlrecycler.R

class AudioViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val videoView = view.findViewById<VideoView>(R.id.audioView)
}