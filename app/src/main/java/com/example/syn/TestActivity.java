package com.example.syn;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class TestActivity extends AppCompatActivity implements OnClickButton {
    private TextView tvNotify;
    private Button btnAccept;
    private OnClickButton mCallBackOnClickButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activiti_register);
        tvNotify = findViewById(R.id.tv_notify);
        btnAccept = findViewById(R.id.btn_accept);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBackOnClickButton.onSendDataToFragment(false);
            }
        });
        replaceFragment();
    }

    private void replaceFragment() {
        FragmentRegister fragmentRegister = new FragmentRegister();
        mCallBackOnClickButton = (OnClickButton) fragmentRegister;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_register, fragmentRegister);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onSendDataToActivity(String name, String product) {
        Log.d("MyClass", "perform  Activity with void onClick");
        tvNotify.setText(name + " buy product " + product);
        btnAccept.setEnabled(true);
    }

    @Override
    public void onSendDataToFragment(boolean isEnable) {
    }
}
