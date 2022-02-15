package com.end.mvi.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.end.mvi.R
import com.end.mvi.models.ClothesShoesModel

class EndRVAdapter : RecyclerView.Adapter<EndRVAdapter.ViewHolder>() {
    private val clothesItems = arrayListOf<ClothesShoesModel.Product>()
    var onItemClicked: ((ClothesShoesModel.Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.end_item_layout, parent, false)
        )

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(clothesItems: List<ClothesShoesModel.Product>) {
        this.clothesItems.clear()
        this.clothesItems.addAll(clothesItems)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = clothesItems[position]
        with(holder) {
            endClothesImageView.load(item.image)
            endClothesTextView.text = item.name
            itemView.setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }

    override fun getItemCount() = clothesItems.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val endClothesImageView: ImageView = itemView.findViewById(R.id.endClothesImageView)
        val endClothesTextView: TextView = itemView.findViewById(R.id.endClothesTextView)
    }
}