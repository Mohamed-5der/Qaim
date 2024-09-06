package com.qaim.qaim.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.qaim.qaim.R
import com.moyasar.android.sdk.PaymentConfig
import com.moyasar.android.sdk.PaymentResult
import com.moyasar.android.sdk.PaymentSheet
import com.moyasar.android.sdk.payment.models.Payment
import androidx.appcompat.app.AppCompatActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"

class MoyasrPaymentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: Int? = null
    private var param3: String? = null

    var paymentSheet: PaymentSheet? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getInt(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_moyasr_payment, container, false)
        val txtCost =v.findViewById<EditText>(R.id.emailEditText)
        txtCost.setText(param1 + " ريال ")
        val txCompanyName =v.findViewById<TextView>(R.id.comp)
        txCompanyName.setText(" المبلغ مقدم مقابل خدمات شركة " + param3)

        val donateBtn =v.findViewById<Button>(R.id.button)
        donateBtn.setOnClickListener {
            paymentSheet!!.present()
        }


        return v
    }




    fun handlePaymentResult(result: PaymentResult) {
        when (result) {
            is PaymentResult.Completed -> {
                handleCompletedPayment(result.payment);
            }
            is PaymentResult.Failed -> {
                // Handle error
                var error = result.error;
            }
            PaymentResult.Canceled -> {
                // User has canceled the payment
            }
        }
    }

    fun handleCompletedPayment(payment: Payment) {
        when (payment.status) {
            "paid" -> { /* Handle successful payment */ }
            "failed" -> {
                /* Handle failed payment */
            }
            else -> { /* Handle other statuses */ }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, id: Int, param3: String) =
            MoyasrPaymentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, id)
                    putString(ARG_PARAM3, param3)
                }
            }
    }
}