package com.atl.ayan.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.atl.ayan.App
import com.atl.ayan.R
import com.atl.ayan.di.component.DaggerHistoryComponent
import com.atl.ayan.di.module.HistoryModule
import com.atl.ayan.model.entity.OrderHistory
import com.atl.ayan.presenter.HistoryPresenter
import com.atl.ayan.presenter.view.HistoryView
import com.atl.ayan.ui.adapter.HistoryAdapter
import kotlinx.android.synthetic.main.activity_history.*
import javax.inject.Inject

class HistoryActivity : AppCompatActivity(R.layout.activity_history), HistoryView {

    @Inject
    lateinit var historyPresenter: HistoryPresenter
    private val historyAdapter = HistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        setToolbar()

        DaggerHistoryComponent.builder()
            .historyModule(HistoryModule(this))
            .appComponent((this.applicationContext as App).getAppComponent())
            .build()
            .inject(this)

        history_recyclerView.layoutManager = LinearLayoutManager(this)
        history_recyclerView.adapter = historyAdapter

        on_error_button.setOnClickListener {
            historyPresenter.getHistory()
        }
        historyPresenter.getHistory()
    }

    private fun setToolbar() {
        toolbarHistory.title = resources.getText(R.string.orders)
        toolbarHistory.navigationIcon = resources.getDrawable(R.drawable.ic_back)
        toolbarHistory.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun setData(orderHistoryList: MutableList<OrderHistory>) {
        historyAdapter.submitList(orderHistoryList)

        error_history.visibility = View.GONE
        history_recyclerView.visibility = View.VISIBLE
        empty_history.visibility = View.GONE
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showError(msg: String) {
        errorMessage.text = msg
        error_history.visibility = View.VISIBLE
        history_recyclerView.visibility = View.GONE
        empty_history.visibility = View.GONE
    }

    override fun showEmpty() {
        error_history.visibility = View.GONE
        history_recyclerView.visibility = View.GONE
        empty_history.visibility = View.VISIBLE
    }
}