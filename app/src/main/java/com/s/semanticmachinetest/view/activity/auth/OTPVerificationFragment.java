package com.s.semanticmachinetest.view.activity.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.goodiebag.pinview.Pinview;
import com.s.semanticmachinetest.R;
import com.s.semanticmachinetest.view.activity.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OTPVerificationFragment extends Fragment {
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.mTextViewMobileNumber)
    TextView mTextViewMobileNumber;
    @BindView(R.id.pinView)
    Pinview pinView;
    @BindView(R.id.mButtonVerifyNow)
    TextView mButtonVerifyNow;
    @BindView(R.id.mTextViewCount)
    TextView mTextViewCount;
    @BindView(R.id.mTextViewResend)
    TextView mTextViewResend;
    private CountDownTimer countDownTimer;
    String mobileNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_o_t_p_verification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        ((AuthActivity) getActivity()).setToolbarTitle(getString(R.string.enter_otp));
        mobileNumber = getArguments().getString(getString(R.string.key_value));
        mTextViewMobileNumber.setText(getString(R.string.sending_otp_message, mobileNumber));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        startCounter();
    }

    private void startCounter() {
        countDownTimer = new CountDownTimer(31000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    mTextViewCount.setText(String.valueOf(millisUntilFinished / 1000) + " Sec");
                    mTextViewResend.setEnabled(false);
                    mTextViewResend.setTextColor(getResources().getColor(R.color.color_e0e0e0));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinish() {
                try {
                    mTextViewCount.setText("");
                    mTextViewResend.setTextColor(getResources().getColor(R.color.color_333333));
                    mTextViewResend.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @OnClick({R.id.mButtonVerifyNow, R.id.mTextViewResend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mButtonVerifyNow:

                String pin = pinView.getValue().trim();
                int pinLength = pin.length();
                if (pin.isEmpty()) {
                    Toast.makeText(getContext(), getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show();
                } else if (pinLength > 4 || pinLength < 4) {
                    Toast.makeText(getContext(), getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show();
                } else {
                    if (pin.equals("1055")) {
                        startActivity(new Intent(getContext(), HomeActivity.class));
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.mTextViewResend:
                startCounter();
                break;
        }
    }
}