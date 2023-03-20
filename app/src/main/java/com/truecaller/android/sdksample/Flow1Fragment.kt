package com.truecaller.android.sdksample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.truecaller.android.sdk.common.models.TrueProfile
import kotlinx.android.synthetic.main.flow_home_page.getStartedBtn

class Flow1Fragment : BaseFragment(), FragmentPresenter {

    var customDialog: CustomDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        customDialog = CustomDialog(requireContext())
        return inflater.inflate(R.layout.fragment_flow1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getStartedBtn.setOnClickListener { fragmentListener.getProfile() }
        customDialog?.findViewById<ImageView>(R.id.close_layout)?.setOnClickListener {
            customDialog?.dismiss()
        }
        customDialog?.proceedButton?.setOnClickListener {
            when (customDialog?.proceedButton?.tag) {
                PHONE_LAYOUT -> {
                    val phoneNumber = customDialog?.findViewById<EditText>(R.id.editPhone)?.text.toString()
                    fragmentListener.initVerification(phoneNumber)
                }
                OTP_LAYOUT -> {
                    val otp = customDialog?.findViewById<EditText>(R.id.editOtp)?.text.toString()
                    fragmentListener.validateOtp(otp)
                }
                NAME_LAYOUT -> {
                    val firstName = customDialog?.findViewById<EditText>(R.id.editFirstName)?.text.toString()
                    val lastName = customDialog?.findViewById<EditText>(R.id.editLastName)?.text.toString()
                    val trueProfile = TrueProfile.Builder(firstName, lastName).build()
                    fragmentListener.verifyUser(trueProfile)
                }
            }
        }
        customDialog?.setOnDismissListener { closeVerificationFlow() }
    }

    override fun showVerificationFlow() {
        customDialog?.show()
        showInputNumberView(false)
    }

    override fun closeVerificationFlow() {
        customDialog?.dismiss()
        fragmentListener.closeFlow()
    }

    override fun showCallingMessageInLoader(ttl: Double?) {
        showCallingMessage(customDialog?.findViewById(R.id.callMessage), ttl, customDialog?.findViewById(R.id.timerTextProgress))
    }

    override fun showInputNumberView(inProgress: Boolean) {
        animateView(customDialog?.findViewById(R.id.loaderImageView), inProgress)
        dismissCountDownTimer()
        customDialog?.showInputNumberView(inProgress)
    }

    override fun showInputNameView(inProgress: Boolean) {
        animateView(customDialog?.findViewById(R.id.loaderImageView), inProgress)
        showCountDownTimerText()
        customDialog?.showInputNameView(inProgress)
    }

    override fun showInputOtpView(inProgress: Boolean, otp: String?, ttl: Double?) {
        animateView(customDialog?.findViewById(R.id.loaderImageView), inProgress)
        ttl?.let { showCountDownTimer(it) } ?: showCountDownTimerText()
        /*otp?.let { dismissCountDownTimer() } ?: ttl?.let {
            showCountDownTimer(it)
        }*/
        customDialog?.findViewById<EditText>(R.id.editOtp)?.setText(otp)
        customDialog?.showInputOtpView(inProgress)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        customDialog = null
    }
}
