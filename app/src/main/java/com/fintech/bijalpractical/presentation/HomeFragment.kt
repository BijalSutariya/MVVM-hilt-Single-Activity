package com.fintech.bijalpractical.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fintech.bijalpractical.R
import com.fintech.bijalpractical.data.DataModel
import com.fintech.bijalpractical.databinding.FragmentHomeBinding
import com.fintech.bijalpractical.presentation.adapter.HomeAdapter
import com.fintech.bijalpractical.presentation.viewModels.HomeViewModel
import com.fintech.bijalpractical.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private  val  viewModel: HomeViewModel by viewModels()
    private lateinit var mAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeHomeData()


        mAdapter = HomeAdapter()
        binding.recyclerViewList.adapter = mAdapter
    }

    private fun observeHomeData() {
        viewModel.fetchList.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { resources -> resources?.let { handleFetchData(resources) } }
            .launchIn(lifecycleScope)
    }

    private fun handleFetchData(state: Resource<List<DataModel>>) {
        when (state) {
            is Resource.Loading -> {
                binding.btnProgress.visibility = View.VISIBLE
                binding.btnProgress.startAnimation()
            }
            is Resource.Success -> state.data?.let { list ->
                binding.btnProgress.visibility = View.GONE
                binding.btnProgress.revertAnimation()
                handleSuccess(list)
            }

            is Resource.Failed -> {
                binding.btnProgress.visibility = View.GONE
                binding.btnProgress.startAnimation()
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
            else -> Unit
        }
    }

    private fun handleSuccess(data: List<DataModel>) {
        if (data.isNotEmpty()) {
            binding.recyclerViewList.visibility = View.VISIBLE
            Log.e("TAG", "handleSuccess: ${data}")
            setupAdapter(data)
        } else {
            binding.recyclerViewList.visibility = View.GONE
            Log.e("TAG", "No DAta")

        }
    }

    private fun setupAdapter(list: List<DataModel>) {
        mAdapter.updateData(list)
    }

}