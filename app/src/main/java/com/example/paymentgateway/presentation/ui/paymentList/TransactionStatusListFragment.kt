package com.example.paymentgateway.presentation.ui.paymentList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paymentgateway.databinding.FragmentTransactionStatusListBinding
import com.example.paymentgateway.domain.repository.Resource
import com.example.paymentgateway.presentation.core.ServiceLocator
import com.example.paymentgateway.presentation.core.ViewModelFactory
import com.example.paymentgateway.presentation.ui.paymentSummary.state.CheckoutResultModel
import timber.log.Timber

class TransactionStatusListFragment : Fragment(), TransactionStatusRecyclerViewAdapter.OnListFragmentInteractionListener {

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

        setupView()
        return view
    }

    private fun setupView() {
        // Set the adapter
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = transactionsAdapter
            itemAnimator = DefaultItemAnimator()
        }

        binding.swipeRefreshLayout.isEnabled = false
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshPendingTransactions()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.transactionListLiveData.observe(viewLifecycleOwner, Observer { handleResourceStatus(it) })
    }

    private fun handleResourceStatus(resource: Resource<List<CheckoutResultModel?>>) {
        when (resource) {
            is Resource.Success -> {
                binding.swipeRefreshLayout.isRefreshing = false
                binding.recyclerViewNoDataPlaceholder.visibility = if (resource.data.isNullOrEmpty()) View.VISIBLE else View.GONE
                transactionsAdapter.values = resource.data
                transactionsAdapter.notifyDataSetChanged()
            }
            is Resource.Loading -> binding.swipeRefreshLayout.isRefreshing = true
            is Resource.Error -> {
                binding.swipeRefreshLayout.isRefreshing = false
                Timber.e(resource.exception, resource.message)
            }
        }
    }

    override fun onListFragmentInteraction(item: CheckoutResultModel?) {
        if (item != null) {
            dialogFragment = TransactionDetailDialogFragment.newInstance(item)
            dialogFragment?.show(parentFragmentManager, TransactionDetailDialogFragment.TAG)
        }
    }
}
