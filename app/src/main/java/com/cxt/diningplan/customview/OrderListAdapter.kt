package com.cxt.diningplan.customview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cxt.diningplan.R
import com.cxt.diningplan.entity.Order
import com.cxt.diningplan.extend.with
import kotlinx.android.synthetic.main.item_order.view.*

class OrderListAdapter(private val onItemClick: (Order) -> Unit) : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    var orderList: List<Order> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false))

    override fun getItemCount() = orderList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.getNameText().text = orderList[position].name
        holder.getAddressText().text = orderList[position].address
        holder.itemView.setOnClickListener { onItemClick.invoke(orderList[position]) }
        Glide.with(holder.itemView.context).load(orderList[position].avatar).into(holder.getAvatarImage())
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun getNameText(): TextView = itemView.name_text
        fun getAvatarImage(): ImageView = itemView.elder_avatar
        fun getAddressText(): TextView = itemView.address_text
    }
}