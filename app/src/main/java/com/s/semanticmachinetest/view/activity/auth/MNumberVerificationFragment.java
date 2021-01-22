package com.s.semanticmachinetest.view.activity.auth;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.s.semanticmachinetest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class MNumberVerificationFragment extends Fragment {


    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.mTextViewSTDCode)
    TextView mTextViewSTDCode;
    @BindView(R.id.mEditTextMobileNumber)
    TextInputEditText mEditTextMobileNumber;
    @BindView(R.id.mButtonNext)
    Button mButtonNext;
    @BindView(R.id.mTextViewTermsAndPrivacy)
    TextView mTextViewTermsAndPrivacy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_m_number_verification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        ((AuthActivity) getActivity()).setToolbarTitle(getString(R.string.enter_number_label));
        super.onViewCreated(view, savedInstanceState);
    }

    @OnEditorAction(R.id.mEditTextMobileNumber)
    public boolean setOnWorkLocationImOption(TextView v, int actionId, KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_DONE:
                getOtp();
                return true;
        }
        return false;
    }

    @OnClick(R.id.mButtonNext)
    public void onViewClicked() {
        getOtp();
    }


    public void getOtp() {
        String mobileNumber = mEditTextMobileNumber.getText().toString().trim();
        int numberLength = mobileNumber.length();
        if (mobileNumber.isEmpty()) {
            Toast.makeText(getContext(), getString(R.string.invalid_mobile_number), Toast.LENGTH_SHORT).show();
        } else if (numberLength > 10 || numberLength < 10) {
            Toast.makeText(getContext(), getString(R.string.invalid_mobile_number), Toast.LENGTH_SHORT).show();
        } else {
            mEditTextMobileNumber.setText("");
            OTPVerificationFragment fragment = new OTPVerificationFragment();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.key_value), mobileNumber);
            fragment.setArguments(bundle);
            ((AuthActivity) getActivity()).addFragment(fragment);
        }
    }

}