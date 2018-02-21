package com.effone.registeration;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public TextInputEditText mEtNumber;
    public Button mBtnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtNumber=(TextInputEditText)findViewById(R.id.et_number);
        mBtnSend=findViewById(R.id.button);
        mBtnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,OtpScreenActivity.class);
        intent.putExtra("opt",mEtNumber.getText().toString().trim());
        startActivity(intent);
    }
}
