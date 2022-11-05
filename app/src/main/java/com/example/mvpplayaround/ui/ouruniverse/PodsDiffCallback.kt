package com.example.mvpplayaround.ui.ouruniverse

import androidx.recyclerview.widget.DiffUtil
import com.example.mvpplayaround.data.remote.models.AstronomyPicture

class PodsDiffCallback(private val oldList: List<AstronomyPicture>,
                       private val newList: List<AstronomyPicture>): DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when (oldList[oldItemPosition].url) {
            newList[newItemPosition].url -> true
            else -> false
        }
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}