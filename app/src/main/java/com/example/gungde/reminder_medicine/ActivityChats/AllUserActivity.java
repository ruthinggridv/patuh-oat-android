package com.example.gungde.reminder_medicine.ActivityChats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.gungde.reminder_medicine.About;
import com.example.gungde.reminder_medicine.MainActivity;
import com.example.gungde.reminder_medicine.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllUserActivity extends AppCompatActivity {
    @BindView(R.id.pindah_to_about)
    LinearLayout pindahToAbout;
    @BindView(R.id.btnBack)
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_user);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.pindah_to_about)
    public void onPindahToAboutClicked() {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnBack)
    public void onViewClicked() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
