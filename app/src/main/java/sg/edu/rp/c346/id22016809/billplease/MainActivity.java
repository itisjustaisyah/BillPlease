package sg.edu.rp.c346.id22016809.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
public class MainActivity extends AppCompatActivity {
    EditText amt;
    EditText pax;
    EditText discount;

    ToggleButton svs;
    ToggleButton gst;

    Button split;
    Button reset;

    RadioGroup paymentType;


    TextView total;
    TextView eachPay;


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amt = findViewById(R.id.editAmount);
        pax = findViewById(R.id.editNumberPax);
        discount = findViewById(R.id.editDiscount);

        svs = findViewById(R.id.svs);// taxation
        gst = findViewById(R.id.gst);

        paymentType = findViewById(R.id.paymentType); //payBy

        total = findViewById(R.id.total); //in total pay
        eachPay = findViewById(R.id.eachPay); //how much each pAX pay

        split = findViewById(R.id.split); //split by
        reset = findViewById(R.id.reset);


        split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float normalAmt;
                float disc = 1;
                float tax = 1;
                int numberToSplit;

                if((pax.getText().toString().isEmpty()) && (amt.getText().toString().isEmpty())){
                    amt.setError("REQUIRED!");
                    pax.setError("REQUIRED!");
                }else{
                    normalAmt= Float.parseFloat(amt.getText().toString());
                    numberToSplit = Integer.parseInt(pax.getText().toString());
                    if (!discount.getText().toString().isEmpty()) {
                        disc -= (Float.parseFloat((discount.getText().toString()))/100);}

                    if(svs.isChecked()){
                        tax += 0.10;
                    }
                    if (gst.isChecked()) {
                        tax += 0.07;
                    }
                    float amtApplyTaxDisc = normalAmt*disc * tax ;
                    total.setText(String.format("Total Bill: $%.2f", amtApplyTaxDisc));

                    int checkedRadioId = paymentType.getCheckedRadioButtonId();
                    String s;
                    s = "Each Pays: $%.2f";
                    split.setEnabled(false);

                    if (checkedRadioId == R.id.cash) {
                        s = "Each Pays: $%.2f in cash";
                    }else if (checkedRadioId == R.id.paynow){
                        s = "Each Pays: $%.2f via PayNow to 12345678";
                    }


                    eachPay.setText(String.format(s, amtApplyTaxDisc / numberToSplit));
                }
            }
        });
        reset.setOnClickListener(v -> {
            amt.setText("");
            pax.setText("");
            discount.setText("");
            svs.setChecked(false);
            gst.setChecked(true);
            paymentType.check(R.id.cash);
        });

    }
}