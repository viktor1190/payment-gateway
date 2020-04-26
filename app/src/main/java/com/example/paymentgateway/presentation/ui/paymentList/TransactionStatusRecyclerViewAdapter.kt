package com.example.paymentgateway.presentation.ui.paymentList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.paymentgateway.R
import com.example.paymentgateway.presentation.ui.paymentList.TransactionStatusRecyclerViewAdapter.OnListFragmentInteractionListener
import com.example.paymentgateway.presentation.ui.paymentSummary.state.CheckoutResultModel
import kotlinx.android.synthetic.main.fragment_transaction_status_item.view.content
import kotlinx.android.synthetic.main.fragment_transaction_status_item.view.item_number

/**
 * [RecyclerView.Adapter] that can display a [CheckoutResultModel] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class TransactionStatusRecyclerViewAdapter(
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<TransactionStatusRecyclerViewAdapter.ViewHolder>() {

    var values: List<CheckoutResultModel?>? = null
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as CheckoutResultModel
            // Notify the active callbacks interface that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_transaction_status_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        holder.mIdView.text = item?.status
        holder.mContentView.text = "${item?.total.toString()} ${item?.currency}"

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = values?.size ?: 0

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: CheckoutResultModel?)
    }
}
