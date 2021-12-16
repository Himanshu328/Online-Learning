package com.sgnr.sgnrclasses.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.sgnr.sgnrclasses.Api.ApiClient;
import com.sgnr.sgnrclasses.Api.ApiInterface;
import com.sgnr.sgnrclasses.Model.LoginDetailsModel;
import com.sgnr.sgnrclasses.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaymentActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    private String amount,name,course_name,mobile,email;
    private static final String PREF_NAME = "Shared pref";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String COURSE_NAME = "course_name";
    private static final String COURSE_PRICE = "course_price";
    private SharedPreferences sharedPreferences;
    private ApiInterface apiInterface;
    public static final String TAG = "Payment Status";
    private TextView courseName,coursePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        Button payment = findViewById(R.id.paymentBTN);
        courseName = findViewById(R.id.payCourseName);
        coursePrice = findViewById(R.id.pay_Price);


        name = sharedPreferences.getString(KEY_NAME,null);
        email = sharedPreferences.getString(KEY_EMAIL,null);
        mobile = sharedPreferences.getString(KEY_MOBILE,null);
        course_name = sharedPreferences.getString(COURSE_NAME,null);
        amount = sharedPreferences.getString(COURSE_PRICE,null);

        courseName.setText(course_name);
        coursePrice.setText("\u20B9 "+amount);

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment(mobile,amount,email);
            }
        });
    }

   private void sendPayment(String name,String mobile,String course_name,String paymentStatus,String amount)
   {
        apiInterface.sendPayment(name,course_name,mobile,paymentStatus,amount).enqueue(new Callback<LoginDetailsModel>() {
            @Override
            public void onResponse(Call<LoginDetailsModel> call, Response<LoginDetailsModel> response) {
                try{
                    if(response != null){
                        if(response.body().getStatus().equals("1")){
                            Toast.makeText(PaymentActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(PaymentActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }catch(Exception e){
                    Log.d("failure",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginDetailsModel> call, Throwable throwable) {
                Log.d("failure",throwable.getLocalizedMessage());
            }
        });
   }

   public void startPayment(String mobile_number,String amount,String email) {
        int payment = Math.round(Float.parseFloat(amount) * 100);

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_dI7Kh9GbtJOKz2");

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Online Learning");
            options.put("description", "Payment");
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            // options.put("order_id", "20219020702021");//from response of step 3.
            options.put("theme.color", "#5987ff");
            options.put("currency", "INR");
            options.put("amount", payment);//pass amount in currency subunits
            options.put("prefill.email", email);
            options.put("prefill.contact","+91"+mobile_number);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(this, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        sendPayment(name,mobile,course_name,"success",amount);
        Log.d(TAG,"Success:"+s);
        Toast.makeText(this, "Payment send successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(this, "Failed!...", Toast.LENGTH_SHORT).show();
    }
}