package br.com.jvcm;

import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class IMCActivity extends AppCompatActivity {

    private EditText edtHeight;
    private EditText edtWeight;
    private TextView tvResult;
    private ImageView imgResult;
    private Button btnCalc;
    private View resultContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);

        edtHeight = findViewById(R.id.edt_height);
        edtWeight = findViewById(R.id.edt_weight);
        tvResult = findViewById(R.id.tv_imc_result);
        imgResult = findViewById(R.id.img_result);
        btnCalc = findViewById(R.id.btn_calc);
        btnCalc.setOnClickListener(onCalcImcButton);
        resultContainer = findViewById(R.id.results_container);
    }

    private View.OnClickListener onCalcImcButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //executa codigo quando o botão for clicado
            //peso / (altura * altura)

            Double weight = null;
            Double height = null;

            try {
                weight = Double.parseDouble(edtWeight.getText().toString());
                height = Double.parseDouble(edtHeight.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Valor invalido", Toast.LENGTH_SHORT).show();
            }

            if (weight != null && height != null) {
                resultContainer.setVisibility(View.VISIBLE);

                double imc = weight / (height * height);
                showStatusIMC(imc);
            }
        }
    };

    private void showStatusIMC(double imc) {
        StringBuilder builder = new StringBuilder();
        builder.append(round(imc, 2));
        builder.append(", ");

        @StringRes
        int stringResource = 0;
        if (imc < 18) {
            stringResource = R.string.activity_imc_result_low_weight;
        } else if (imc >= 18 && imc < 25) {
            stringResource = R.string.activity_imc_result_normal_weight;
        } else if (imc >= 25 && imc < 28) {
            stringResource = R.string.activity_imc_result_more_fast;
        } else {
            stringResource = R.string.activity_imc_result_supreme_fast;
        }

        builder.append(getString(stringResource));
        tvResult.setText(builder);
    }

    public static double round(double value, int places) {
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
