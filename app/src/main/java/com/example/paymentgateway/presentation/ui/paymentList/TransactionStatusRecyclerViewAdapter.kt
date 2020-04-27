package com.example.paymentgateway.presentation.ui.paymentList

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.paymentgateway.databinding.FragmentTransactionStatusItemBinding
import com.example.paymentgateway.presentation.ui.paymentList.TransactionStatusRecyclerViewAdapter.OnListFragmentInteractionListener
import com.example.paymentgateway.presentation.ui.paymentSummary.state.CheckoutResultModel
import com.example.paymentgateway.presentation.util.getStatusColor
import com.example.paymentgateway.presentation.util.getStatusDrawable

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
        val binder = FragmentTransactionStatusItemBinding.inflate(LayoutInflater.from(parent.context))
        binder.root.apply {
            this.layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return ViewHolder(binder, parent.resources)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int = values?.size ?: 0

    inner class ViewHolder(private val itemBinding: FragmentTransactionStatusItemBinding, private val resources: Resources) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(checkoutResultModel: CheckoutResultModel?) {
            if (checkoutResultModel != null) {
                itemBinding.itemImage.setImageDrawable(checkoutResultModel.getStatusDrawable(resources))
                itemBinding.itemImage.setBackgroundColor(checkoutResultModel.getStatusColor(resources))
                itemBinding.itemTitle.text = checkoutResultModel.status
                itemBinding.itemSubtitle.text = checkoutResultModel.lastUpdate
                itemBinding.itemValue.text = "${checkoutResultModel.total} ${checkoutResultModel.currency}"
                with(itemBinding.root) {
                    tag = checkoutResultModel
                    setOnClickListener(mOnClickListener)
                }
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + itemBinding.itemSubtitle.text + "'"
        }
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: CheckoutResultModel?)
    }
}
