package com.lockwood.replicant.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

// TODO: Add DiffUtilsItem as arg instead of any and use DiffUtils
abstract class ReplicantAdapter<T : Any>(
  val data: Array<T>,
) : RecyclerView.Adapter<ReplicantViewHolder>() {

  override fun getItemCount(): Int {
    return data.size
  }

  override fun onBindViewHolder(
    holder: ReplicantViewHolder,
    position: Int,
  ) {
    holder.onBind(position)
  }

  protected companion object {

    @JvmStatic
    protected fun ViewGroup.inflate(
      @LayoutRes layoutRes: Int,
      attachToRoot: Boolean = false,
    ): View = LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
  }
}
