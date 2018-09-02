package com.example.gungde.reminder_medicine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionaryActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rg_1)
    RadioGroup rg1;
    @BindView(R.id.rg_2)
    RadioGroup rg2;
    @BindView(R.id.rg_3)
    RadioGroup rg3;
    @BindView(R.id.rg_4)
    RadioGroup rg4;
    @BindView(R.id.rg_5)
    RadioGroup rg5;
    @BindView(R.id.rg_6)
    RadioGroup rg6;

    public static void start(Context context) {
        Intent i = new Intent(context, QuestionaryActivity.class);
        context.startActivity(i);
    }

    private static final int X = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionary);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kuisioner");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean checkRadioGroup(RadioGroup[] rg) {
        boolean status = true;
        boolean needFirstFocus = true;
        for (int i = 0; i < rg.length; i++) {
            if (rg[i].getCheckedRadioButtonId() == -1) {
                if (needFirstFocus) {
                    Toast.makeText(this, "Harap isi pertanyaan " + i + 1, Toast.LENGTH_SHORT).show();
                    needFirstFocus = false;
                }
                status = status && false;
            } else {
                status = status && true;
            }
        }
        return status;
    }

    public String calculateMMSA(RadioGroup[] rg) {
        //default dari variabel X
        int result = X;
        for (RadioGroup aRg : rg) {
            if (((RadioButton) findViewById(aRg.getCheckedRadioButtonId())).getText().toString().equals("Ya"))
                result++;
        }
        if(result>=3)
            return "tidak patuh";
        else if(result>=1)
            return "sedang";
        else
            return "patuh";
    }

    public String calculateAnjuran(RadioGroup[] rg, RadioGroup rgBTA) {
        String MMSA = calculateMMSA(rg);
        String BTA = ((RadioButton) findViewById(rgBTA.getCheckedRadioButtonId())).getText().toString();

        if(MMSA.equals("patuh") && BTA.equalsIgnoreCase("negatif"))
            return "OAT Diteruskan";
        else if(MMSA.equals("sedang") && BTA.equalsIgnoreCase("negatif"))
            return "OAT Diteruskan";
        else if(MMSA.equals("tidak patuh") && BTA.equalsIgnoreCase("negatif"))
            return "OAT Diteruskan";
        else if(MMSA.equals("patuh") && BTA.equalsIgnoreCase("positif"))
            return "Uji Resistensi OAT";
        else if(MMSA.equals("sedang") && BTA.equalsIgnoreCase("positif"))
            return "Uji Resistensi OAT";
        else if(MMSA.equals("tidak patuh") && BTA.equalsIgnoreCase("positif"))
            return "Ulang Dari Awal/Menjalani Pengobatan OAT Kategori 2";
        else
            return "Gagal";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_questionary, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                RadioGroup[] rg = {rg1, rg2, rg3, rg4, rg5,rg6};
                if (checkRadioGroup(rg)) {
                    rg = new RadioGroup[]{rg1, rg2, rg3, rg4, rg5};
                    Toast.makeText(this, "Hasilnya " + calculateAnjuran(rg, rg6), Toast.LENGTH_SHORT).show();
                }
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
