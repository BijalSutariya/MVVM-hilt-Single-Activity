package com.fintech.bijalpractical.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.fintech.bijalpractical.R
import com.fintech.bijalpractical.databinding.FragmentVerificationBinding
import com.fintech.bijalpractical.presentation.viewModels.LoginViewModel
import com.fintech.bijalpractical.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerificationFragment : Fragment() {

    private lateinit var binding: FragmentVerificationBinding
    private val viewModel:LoginViewModel by viewModels()
    private val args: VerificationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_verification, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnVerify.setOnClickListener {
                val smsCode = etCodeSendVerification.text.toString()
                val verificationId = args.verification
                viewModel.signInWithVerificationCode(verificationId, smsCode)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isVerificationInProgress.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            binding.btnVerify.startAnimation()
                        }

                        is Resource.Success -> {
                            binding.btnVerify.revertAnimation()
                            Navigation.findNavController(view)
                                .navigate(R.id.action_verificationFragment_to_homeFragment)
                        }

                        is Resource.Failed -> {
                            Toast.makeText(
                                context,
                                "Verification failed: ${resource.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.btnVerify.revertAnimation()
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

}