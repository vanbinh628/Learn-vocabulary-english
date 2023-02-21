package com.example.syn;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentRegister extends Fragment implements OnClickButton {
    private String mName;
    private String mProduct;
    private Button btn;
    private OnClickButton mCallBackOnClickButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        EditText edName = view.findViewById(R.id.edit_name);
        EditText edProduct = view.findViewById(R.id.edit_product);
        btn = view.findViewById(R.id.btn_register);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MyClass", "perform  Fragment with void onClick");
                mName = edName.getText().toString();
                mProduct = edProduct.getText().toString();
                mCallBackOnClickButton.onSendDataToActivity(mName, mProduct);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallBackOnClickButton = (OnClickButton) context;
            Log.d("MyClass", "set OnClickButton with" + context.getApplicationInfo().toString());
        } catch (ClassCastException e) {
            Log.d("MyClass", "must implement OnSelectedListener");
            throw new ClassCastException(context.toString()
                    + " must implement OnSelectedListener");
        }
    }

    @Override
    public void onSendDataToActivity(String name, String product) {

    }

    @Override
    public void onSendDataToFragment(boolean isEnable) {
        btn.setEnabled(isEnable);
    }
}