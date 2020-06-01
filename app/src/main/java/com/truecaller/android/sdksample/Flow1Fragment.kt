package com.truecaller.android.sdksample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import com.truecaller.android.sdk.TrueProfile
import kotlinx.android.synthetic.main.flow_home_page.getStartedBtn

class Flow1Fragment : BaseFragment() {

    private lateinit var customDialog: CustomDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flow1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getStartedBtn.setOnClickListener { fragmentListener.getProfile() }
        customDialog = CustomDialog(requireContext())
        customDialog.findViewById<ImageView>(R.id.close_layout).setOnClickListener {
            closeVerificationFlow()
        }
        customDialog.proceedButton.setOnClickListener {
            when (customDialog.proceedButton.tag) {
                PHONE_LAYOUT -> {
                    val phoneNumber = customDialog.findViewById<EditText>(R.id.editPhone).text.toString()
                    fragmentListener.initVerification(phoneNumber)
                }
                OTP_LAYOUT -> {
                    val otp = customDialog.findViewById<EditText>(R.id.editOtp).text.toString()
                    fragmentListener.validateOtp(otp)
                }
                NAME_LAYOUT -> {
                    val firstName = customDialog.findViewById<EditText>(R.id.editFirstName).text.toString()
                    val lastName = customDialog.findViewById<EditText>(R.id.editLastName).text.toString()
                    val trueProfile = TrueProfile.Builder(firstName, lastName).build()
                    fragmentListener.verifyUser(trueProfile)
                }
            }
        }
    }

    fun showVerificationFlow() {
        customDialog.show()
    }

    fun closeVerificationFlow() {
        customDialog.dismiss()
    }

    fun showInputNumberView(inProgress: Boolean) {
        customDialog.showInputNumberView(inProgress)
    }

    fun showInputNameView(inProgress: Boolean) {
        customDialog.showInputNameView(inProgress)
    }

    fun showInputOtpView(inProgress: Boolean) {
        customDialog.showInputOtpView(inProgress)
    }


}
