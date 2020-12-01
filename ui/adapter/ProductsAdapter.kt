package com.atl.ayan.ui.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atl.ayan.BuildConfig
import com.atl.ayan.R
import com.atl.ayan.model.entity.Product
import com.atl.ayan.ui.adapter.diff.ProductDiff
import com.atl.ayan.ui.listener.OnAddToCartClickListener
import com.atl.ayan.ui.listener.OnProductClickListener
import com.atl.ayan.utils.Utils
import com.bumptech.glide.Glide

class ProductsAdapter(var onProductClickListener: OnProductClickListener,
                      var onAddToCartClickListener: OnAddToCartClickListener
)
    : ListAdapter<Product, ProductsAdapter.ViewHolder>(ProductDiff()),
    Observer<MutableList<Product>> {

    override fun onChanged(t: MutableList<Product>?) {
        submitList(t)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
        holder.item.setOnClickListener {
            onProductClickListener.onProductClick(getItem(position))
        }
        holder.addToCart.setOnClickListener {
            onAddToCartClickListener.onAddToCartClick(getItem(position))

            holder.onclickAnim.visibility = View.VISIBLE
            holder.onclickAnim.animate()
                .scaleX(2f)
                .scaleY(2f)
                .alpha(0f)
                .setDuration(400)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        holder.onclickAnim.visibility = View.GONE
                        holder.onclickAnim.alpha = 1f
                        holder.onclickAnim.scaleX = 1f
                        holder.onclickAnim.scaleY = 1f
                    }
                })
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val item: View = view
        val name: TextView = view.findViewById(R.id.product_name)
        val price: TextView = view.findViewById(R.id.product_price)
        private val ratioUnit: TextView = view.findViewById(R.id.product_ratio_unit)
        private val priceWithoutCard: TextView = view.findViewById(R.id.product_price_without_card)
        private val image: ImageView = view.findViewById(R.id.product_image)
        private val discountImage: ImageView = view.findViewById(R.id.discount_icon)
        private val propertyOne: TextView = view.findViewById(R.id.property_one)
        private val propertyTwo: TextView = view.findViewById(R.id.property_two)
        private val propertyThree: TextView = view.findViewById(R.id.property_three)
        private val propertyFour: TextView = view.findViewById(R.id.property_four)
        val addToCart: ImageView = view.findViewById(R.id.add_to_cart)
        val onclickAnim: TextView = view.findViewById(R.id.onClickAnimation)

        fun bindView(item: Product) {
            val priceS: String = Utils.priceFormatCart(item.price * item.ratio, 1.0)
            val priceForCard: String = Utils.priceFormatCart(item.priceForCard * item.ratio, 1.0)
            ratioUnit.text = "за ${Utils.doubleFormat(item.ratio)} ${item.unit}"
            name.text = item.nameProduct

            if (item.propertyOne.isEmpty()) {
                propertyOne.visibility = View.GONE
            } else {
                propertyOne.visibility = View.VISIBLE
                propertyOne.text = item.propertyOne
            }

            if (item.propertyTwo.isEmpty()) {
                propertyTwo.visibility = View.GONE
            } else {
                propertyTwo.visibility = View.VISIBLE
                propertyTwo.text = item.propertyTwo
            }

            if (item.propertyThree.isEmpty()) {
                propertyThree.visibility = View.GONE
            } else {
                propertyThree.visibility = View.VISIBLE
                propertyThree.text = item.propertyThree
            }

            if (item.propertyFour.isEmpty()) {
                propertyFour.visibility = View.GONE
            } else {
                propertyFour.visibility = View.VISIBLE
                propertyFour.text = item.propertyFour
            }

            if (item.priceForCard > 0.0 && item.priceForCard < item.price) {
                discountImage.visibility = View.VISIBLE
                priceWithoutCard.visibility = View.VISIBLE
                priceWithoutCard.text = Html.fromHtml("<s>$priceS</s>")
                price.text = priceForCard
            } else {
                discountImage.visibility = View.GONE
                priceWithoutCard.visibility = View.GONE
                price.text = priceS
            }

            Glide.with(itemView)
//                .load(BuildConfig.BASE_URL_IMAGES + "products/${Utils.removeSpaces(item.idProduct)}.png")
                .load(BuildConfig.BASE_URL_IMAGES + "products/${item.idProduct}.png")
                .placeholder(R.drawable.placeholder_product)
                .error(R.drawable.placeholder_product)
                .into(image)
        }
    }
}