package com.example.newdemo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newdemo.R;
import com.example.newdemo.model.OrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PaymentActivity extends AppCompatActivity {
    String key = "UGtwZvo3";
    String txnid = "1";
    String amount ;
    String productinfo = "RJ Veg";
    String firstname = "Hitesh";
    String email = "hiteshdkhatri2001@gmail.com";
    String salt = "zgmv9DfRyi";
    String merchantId = "7386353";
    String serverCalculatedHash;
    String userMobile;
    OrderModel orderModel;
    SharedPreferences preferences;
    private static final String TAG = "PaymentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        amount = getIntent().getStringExtra("total");
        preferences = getSharedPreferences("User", 0);
        userMobile = preferences.getString("mobiletext", "");
        orderModel = (OrderModel) getIntent().getSerializableExtra("order");
        String hashSequence = key + "|" + txnid + "|" + amount + "|" + productinfo + "|" + firstname + "|" + email + "|||||||||||" + salt;
        serverCalculatedHash = hashCal("SHA-512", hashSequence);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new
                PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount(amount)                          // Payment amount
                .setTxnId(txnid)                                             // Transaction ID
                .setPhone("6355283451")                                           // User Phone number
                .setProductName("RJ Veg")                   // Product Name or description
                .setFirstName(firstname)                              // User First name
                .setEmail(email)                                            // User Email ID
                .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")                    // Success URL (surl)
                .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")     //Failure URL (furl)
                .setUdf1("")
                .setUdf2("")
                .setUdf3("")
                .setUdf4("")
                .setUdf5("")
                .setUdf6("")
                .setUdf7("")
                .setUdf8("")
                .setUdf9("")
                .setUdf10("")
                .setIsDebug(false)                              // Integration environment - true (Debug)/ false(Production)
                .setKey(key)                        // Merchant key
                .setMerchantId(merchantId);

        PayUmoneySdkInitializer.PaymentParam paymentParam = null;
        try {
            paymentParam = builder.build();
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        //set the hash
        paymentParam.setMerchantHash(serverCalculatedHash);
        // Invoke the following function to open the checkout page.
        PayUmoneyFlowManager.startPayUMoneyFlow(
                paymentParam,
                this,
                R.style.AppTheme_default,
                true);


    }

    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d(TAG, "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    FirebaseFirestore.getInstance().collection("USERS").document(userMobile).collection("ORDERS")
                            .add(orderModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PaymentActivity.this, "Order Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PaymentActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "Transaction Failed", Toast.LENGTH_SHORT).show();
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
            } else {
                Log.d(TAG, "Both objects are null!");
            }
        }
    }
}
