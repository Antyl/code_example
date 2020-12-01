package com.atl.ayan.ui.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atl.ayan.R
import com.atl.ayan.model.entity.OrderHistory
import com.atl.ayan.model.entity.ProductHistory
import com.atl.ayan.ui.adapter.diff.HistoryDiff
import com.atl.ayan.utils.Utils

class HistoryAdapter : ListAdapter<OrderHistory, HistoryAdapter.ViewHolder>(HistoryDiff()),
    Observer<MutableList<OrderHistory>> {

    override fun onChanged(t: MutableList<OrderHistory>?) {
        submitList(t)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item: View = view
        val time: TextView = view.findViewById(R.id.history_time)
        val date: TextView = view.findViewById(R.id.history_date)
        val orderNumber: TextView = view.findViewById(R.id.history_order)
        val orderAddress: TextView = view.findViewById(R.id.history_address)
        val orderSum: TextView = view.findViewById(R.id.history_sum)
        val arrow: ImageView = view.findViewById(R.id.arrow)
        val products: RecyclerView = view.findViewById(R.id.history_products)

        fun bindView(item: OrderHistory) {
            date.text = item.date
            time.text = item.time
            orderNumber.text = "№ заказа: ${item.orderNumber}"
            orderAddress.text = item.address
            orderSum.text = "Сумма: ${item.orderSum} " + Html.fromHtml("&#x20bd")

            products.layoutManager = LinearLayoutManager(itemView.context)
            val adapter = HistoryProductsAdapter()
            adapter.submitList(item.products)
            products.adapter = adapter

            itemView.setOnClickListener {
                arrow.startAnimation(Utils.animateDown())
                if (products.visibility == View.GONE) {
                    products.visibility = View.VISIBLE
                    arrow.startAnimation(Utils.animateDown())

                } else {
                    products.visibility = View.GONE
                    arrow.startAnimation(Utils.animateUp())
                }
            }
        }
    }

    class HistoryProductsAdapter : ListAdapter<ProductHistory, HistoryProductsAdapter.ViewHolder>(ProductHistoryDiff()),
        Observer<MutableList<ProductHistory>> {

        override fun onChanged(t: MutableList<ProductHistory>?) {
            submitList(t)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history_product, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindView(getItem(position))
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val item: View = view
            private val productName: TextView = view.findViewById(R.id.history_product_name)
            private val productCalculation: TextView = view.findViewById(R.id.history_product_calculation)

            fun bindView(item: ProductHistory) {
                productName.text = item.nameProduct
                productCalculation.text = "${item.countProduct} ${item.unitProduct} * ${item.priceProduct} = ${item.sumProduct}"
            }
        }

        class ProductHistoryDiff : DiffUtil.ItemCallback<ProductHistory>() {
            override fun areItemsTheSame(oldItem: ProductHistory, newItem: ProductHistory): Boolean {
                return oldItem.nameProduct == newItem.nameProduct
            }
            override fun areContentsTheSame(oldItem: ProductHistory, newItem: ProductHistory): Boolean {
                return oldItem.countProduct == newItem.countProduct
            }
        }
    }
}