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
import com.fintech.bijalpractical.R
import com.fintech.bijalpractical.databinding.FragmentLoginBinding
import com.fintech.bijalpractical.presentation.viewModels.LoginViewModel
import com.fintech.bijalpractical.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogin.setOnClickListener {
                val cc = ccp.selectedCountryCodeWithPlus
                val number = etPhoneNumberRegister.text.toString().trim()
                val phoneNumber = "$cc$number"
                viewModel.sendVerificationCode(phoneNumber, requireActivity())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.isVerificationInProgress.collect{
                    when(it){
                        is Resource.Loading -> {
                            binding.btnLogin.startAnimation()
                        }
                        is Resource.Success -> {
                            binding.btnLogin.revertAnimation()
                            val verificationId = viewModel.verificationId.value
                            if (verificationId != null) {
                                // Proceed to VerificationOtpFragment with the verification ID.
                                val action =
                                    LoginFragmentDirections.actionLoginFragmentToVerificationFragment(
                                        verificationId
                                    )
                                Navigation.findNavController(view).navigate(action)
                            } else {
                                // Handle error: Verification ID is null.
                                Toast.makeText(context, "Verification ID is null.", Toast.LENGTH_SHORT).show()
                            }
                        }
                        is Resource.Failed -> {
                            binding.btnLogin.revertAnimation()
                        }
                        else -> Unit
                    }
                }
            }
        }

    }
}