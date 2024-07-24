package com.fintech.bijalpractical.utils

sealed class VerificationOTPValidation {
    object Success : VerificationOTPValidation()
    data class Failed(val message: String) : VerificationOTPValidation()
}

data class OTPFieldsState(
    val otp: VerificationOTPValidation,
)

fun validOTP(
    codeSendVerification: String
): VerificationOTPValidation {

    if (codeSendVerification.isEmpty()) {
        return VerificationOTPValidation.Failed("OTP is not valid!")
    }
    return VerificationOTPValidation.Success
}