package com.lockwood.replicant.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ReplicantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(position: Int)
}
