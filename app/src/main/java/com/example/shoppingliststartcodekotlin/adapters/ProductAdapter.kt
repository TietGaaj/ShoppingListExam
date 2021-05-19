package com.example.shoppingliststartcodekotlin.adapters

import android.view.View
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingliststartcodekotlin.R
import com.example.shoppingliststartcodekotlin.data.Product
import com.example.shoppingliststartcodekotlin.data.Repository
import kotlinx.android.synthetic.main.card_layout.view.*


class ProductAdapter(var products: MutableList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text= products.get(position).name
        holder.itemDetail.text= products.get(position).name


    }

    override fun getItemCount(): Int {
        return products.size
        println(products.size)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage : ImageView
       var itemTitle : TextView
       var itemDetail : TextView
        init{
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)
        }
        //here you need to to do stuff also - to back to the exercises
        //about recyclerviews and you can use approach that were used
        //in the exercise about recyclerviews from the book (lesson 3)
        //if you did not do that exercise - then first do that exercise in
        //a seperate project

    }
}
