package com.example.paymentgateway.presentation.ui.paymentList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paymentgateway.databinding.FragmentTransactionStatusListBinding
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.presentation.core.ServiceLocator
import com.example.paymentgateway.presentation.core.ViewModelFactory
import com.example.paymentgateway.presentation.ui.paymentSummary.state.CheckoutResultModel
import com.example.paymentgateway.presentation.util.toast
import timber.log.Timber

class TransactionStatusListFragment: Fragment(), TransactionStatusRecyclerViewAdapter.OnListFragmentInteractionListener {

    // Binding
    private var _binding: FragmentTransactionStatusListBinding? = null
    private val binding get() = _binding!!

    private val factory: ViewModelFactory = ServiceLocator.viewModelFactory
    private val viewModel by viewModels<TransactionStatusListViewModel> { factory }
    private var transactionsAdapter: TransactionStatusRecyclerViewAdapter = TransactionStatusRecyclerViewAdapter(this)
    private var dialogFragment: TransactionDetailDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionStatusListBinding.inflate(inflater)
        val view = binding.root

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = transactionsAdapter
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.transactionListLiveData.observe(viewLifecycleOwner, Observer { handleResourceStatus(it) })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialogFragment?.dismiss()
        dialogFragment = null
    }

    private fun handleResourceStatus(resource: Resource<List<CheckoutResultModel?>>) {
        when (resource) {
            is Resource.Success -> {
                transactionsAdapter.values = resource.data
                transactionsAdapter.notifyDataSetChanged()
            }
            is Resource.Loading -> Timber.v("Loading list") // TODO victor.valencia show a progress dialog
            is Resource.Error -> Timber.e(resource.exception, resource.message)
        }
    }

    override fun onListFragmentInteraction(item: CheckoutResultModel?) {
        toast("item selected: ${item?.reference}")
        if (item != null) {
            dialogFragment = TransactionDetailDialogFragment.newInstance(item)
            dialogFragment?.show(parentFragmentManager, TransactionDetailDialogFragment.TAG)
        }
    }
}
