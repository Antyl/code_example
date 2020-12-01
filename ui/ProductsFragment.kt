package com.atl.ayan.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.atl.ayan.App
import com.atl.ayan.R
import com.atl.ayan.di.component.DaggerProductsComponent
import com.atl.ayan.di.module.ProductsModule
import com.atl.ayan.model.entity.Product
import com.atl.ayan.presenter.ProductsPresenter
import com.atl.ayan.presenter.view.ProductsView
import com.atl.ayan.ui.adapter.ProductsAdapter
import com.atl.ayan.ui.listener.OnAddToCartClickListener
import com.atl.ayan.ui.listener.OnProductClickListener
import kotlinx.android.synthetic.main.fragment_products.*
import kotlinx.android.synthetic.main.layout_empty.*
import javax.inject.Inject

class ProductsFragment : Fragment(R.layout.fragment_products), ProductsView, OnProductClickListener, OnAddToCartClickListener {

    @Inject
    lateinit var productsPresenter: ProductsPresenter

    private lateinit var productsAdapter: ProductsAdapter

    companion object {
        @JvmStatic
        fun newInstance(type: String, subType: String) =
            ProductsFragment().apply {
                arguments = Bundle().apply {
                    putString("type", type)
                    putString("subType", subType)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerProductsComponent.builder()
            .productsModule(ProductsModule(this))
            .appComponent((requireContext().applicationContext as App).getAppComponent())
            .build()
            .inject(this)

        productsAdapter = ProductsAdapter(this, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        
        productsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        productsRecyclerView.adapter = productsAdapter

        productsPresenter.requestData(getType(), getSubType())
    }

    private fun getType(): String {
        return arguments?.getString("type").toString()
    }

    private fun getSubType(): String {
        return arguments?.getString("subType").toString()
    }

    private fun setToolbar() {
        productsToolbar.title = getSubType()
        productsToolbar.setTitleTextColor(resources.getColor(R.color.colorWhite))
        productsToolbar.navigationIcon = resources.getDrawable(R.drawable.ic_back)
        productsToolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        setHasOptionsMenu(true)
        productsToolbar.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener true
        }
    }

    override fun setData(products: MutableList<Product>) {
        productsRecyclerView.visibility = View.VISIBLE
        emptyFrame.visibility = View.GONE
        productsAdapter.submitList(products)
    }

    override fun setEmpty() {
        productsRecyclerView.visibility = View.GONE
        emptyFrame.visibility = View.VISIBLE
        info_message_empty.text = resources.getString(R.string.empty_products)
    }

    override fun setCountProducts(count: Double) {
        (requireActivity() as MainActivity).setCountIndicator(count)
    }

    override fun onProductClick(product: Product) {
        val intent = Intent(context, ProductActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }

    override fun onAddToCartClick(product: Product) {
        productsPresenter.onAddButtonClick(product)
    }
}
