package com.end.mvi.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.end.mvi.R
import com.end.mvi.models.ClothesShoesModel
import com.end.nond.extensions.loadImage

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
            endClothesImageView.loadImage("https://demo-app-photos-45687895456123.s3.amazonaws.com/9355183956e3445e89735d877b798689?AWSAccessKeyId=ASIASV3YI6A47T7UAM5N&Signature=6iv43GgvMLPX4UvXQWvtsf6L3%2Fc%3D&x-amz-security-token=IQoJb3JpZ2luX2VjEGgaCXVzLWVhc3QtMSJIMEYCIQCMcOGVpmik02UF4TAmlQUAlYHx%2BmGSZlgjnNZsPl2lLwIhALoZJSGUb7z9LbZRlBLOXBBL6kymVo%2Bx5EqzIp6IIXt3Kp0CCNH%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEQAxoMMTg0Mzk4OTY2ODQxIgyUvDn0K6jzwFT5wKcq8QHVugInie7WThewFqbixgiACPv8gFM%2BWtBDKV95ewomsRDLwrFw8UTw9xE%2Bgg4%2BkohjYOurlXPJ%2BVAaareB6jnHz1qws4rCg9AELhHs4ZxJR3BibS7nyBtNi0EDkvcXqOv9pfMCHbZKmGI8e3s%2FpJGlQ4qrM0FPwTwNXiqqAOqgX6i7dqQObl1NEU4h6PZggQdR4XHCdsqNb7VCkaNfphivKHSpVEnDrjn3PyaLeVFAN0lpj8cBQ%2BTMgEHUgsVVtV2%2BhQTyI0kWmyeQm16VPURp9MSU9nW360MTQ3TZnL9rWSDlXJuJITwKozTzW33aQxKFMLy6oZEGOpkBidyY7q8jzv9BRLdyUFqwWs92QvX9HcVt0ZnrVViwLXFMlPZq0wkX3R0bpsun%2F1KwxKUmWkI9clLPsuEni65hhkZi%2BkfswegmVIrvOpOwXEnkVRun3d1YK0PKpu4ATrJX19zxNW%2FKHmUsy5m7BgeoERSssfDk%2BIWUXOOoqYxzoGLHONojGXMgiU00nIfAwVQf6LYuBWkWx7bX&Expires=1646816078")
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