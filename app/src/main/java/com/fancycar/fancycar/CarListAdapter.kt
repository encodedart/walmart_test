package com.fancycar.fancycar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.car_item_view.view.*

class CarListAdapter: RecyclerView.Adapter<CarItemView>()  {

    private var data: MutableList<CarDetail> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarItemView {
        return CarItemView(
            LayoutInflater.from(parent.context).inflate(R.layout.car_item_view, parent, false)
        )
    }

    fun updateList(list: List<CarDetail>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CarItemView, position: Int) {
        holder.bindItem(data[position])
    }

}

class CarItemView(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bindItem(item: CarDetail) {
        println(item.name)
        itemView.carName.text = item.name
        itemView.inDealer.text = if (item.inDealer) "In Dealer" else "Not In Dealer"
        itemView.buyButton.visibility = if (item.inDealer) View.VISIBLE else View.GONE
        itemView.carDetail.text = "${item.make} ${item.make} ${item.year}"
    }
}