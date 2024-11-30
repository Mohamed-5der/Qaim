package com.qaim.qaim.Activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.moyasar.android.sdk.PaymentConfig
import com.moyasar.android.sdk.PaymentResult
import com.moyasar.android.sdk.PaymentSheet
import com.moyasar.android.sdk.payment.models.Payment
import com.qaim.qaim.Classes.PaymentParams
import com.qaim.qaim.Models.AcceptedOrderUserResponse.AcceptedOrderUserResponse
import com.qaim.qaim.Models.Networks.JsonApi
import com.qaim.qaim.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoyasrPaymenyActivity : BaseActivity() {

    var paymentSheet: PaymentSheet? = null

    var retrofit: Retrofit? = null
    var jsonApi: JsonApi? = null
    var companyName: String? = null
    var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moyasr_paymeny)

        retrofit = Retrofit.Builder()
            .baseUrl("https://qaimha.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        jsonApi = retrofit?.create(JsonApi::class.java)


        val cost = intent.extras?.getString("cost")
        id = intent.extras?.getInt("id")
        companyName = intent.extras?.getString("company")

        val txtCost = findViewById<EditText>(R.id.emailEditText)
        txtCost.setText(cost + " ريال ")
        val txCompanyName = findViewById<TextView>(R.id.comp)
        txCompanyName.setText(" المبلغ مقدم مقابل خدمات شركة " + companyName)

        val finalCost = cost + ""

        val config= PaymentConfig(
            amount = finalCost.toDouble().toInt() * 100,
            currency = "SAR",
            description = companyName.toString(),
            apiKey = "pk_live_ZkFcF2nLTYZqkLgwJ8w5gXBT9ee4QTsaHDFU9naC"
//            metadata = mapOf(
//                "order_id" to "order_id_123"
//            )
        )
        paymentSheet = PaymentSheet(this, { handlePaymentResult(it) }, config)


        val donateBtn = findViewById<Button>(R.id.button)
        val imageButton = findViewById<ImageButton>(R.id.imageBtn)
        donateBtn.setOnClickListener {
            paymentSheet!!.present()
        }

        imageButton.setOnClickListener {
            startActivity(Intent(this@MoyasrPaymenyActivity,MainActivity::class.java))
            finishAffinity()
        }

    }

    fun handlePaymentResult(result: PaymentResult) {
        when (result) {
            is PaymentResult.Completed -> {
                handleCompletedPayment(result.payment);
            }
            is PaymentResult.Failed -> {
                // Handle error
            }
            PaymentResult.Canceled -> {
                // User has canceled the payment
                startActivity(Intent(applicationContext,MoyasrPaymenyActivity::class.java))
                finishAffinity()
            }
        }

    }
    fun handleCompletedPayment(payment: Payment) {
        when (payment.status) {
            "paid" -> {
                id?.let { onAcceptedBtnPressed(it, payment.id) }
            }
            "failed" -> {
                var errorMessage = payment.source["message"]
                Toast.makeText(applicationContext ,errorMessage , Toast.LENGTH_SHORT).show()
            }

        }
    }
    fun onAcceptedBtnPressed(id: Int , transaction_id:String) {
        MainActivity.dialog.show()
        val call: Call<AcceptedOrderUserResponse> =
            jsonApi!!.getAcceptedOrdersList("Bearer " + MainActivity.token, PaymentParams(id , transaction_id))
            call.enqueue(object : Callback<AcceptedOrderUserResponse?> {
            override fun onResponse(
                call: Call<AcceptedOrderUserResponse?>,
                response: Response<AcceptedOrderUserResponse?>
            ) {
                MainActivity.dialog.dismiss()
                val acceptedOrderUserResponse = response.body()
                if (response.code() == 200) {
                    MainActivity.dialog.dismiss()
                    Toast.makeText(
                        applicationContext,
                        acceptedOrderUserResponse!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    val dialog = Dialog(this@MoyasrPaymenyActivity)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setContentView(R.layout.accepted_payment_bottom_sheet_dialog)
                    val view = dialog.findViewById<TextView>(R.id.companyName)
                    view.setText(companyName + " المبلغ مقدم مقابل خدمة شركة ")
                    dialog.show()
                    dialog.window!!.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
                    dialog.window!!.setGravity(Gravity.BOTTOM)
                    Handler().postDelayed({
                        dialog.dismiss()
//                        applicationContext.recreate()
                        startActivity(Intent(this@MoyasrPaymenyActivity,MainActivity::class.java))
                        finishAffinity()
                    }, 3000)
                }
            }

            override fun onFailure(call: Call<AcceptedOrderUserResponse?>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


}