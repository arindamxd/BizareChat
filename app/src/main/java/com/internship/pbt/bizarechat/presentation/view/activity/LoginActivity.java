package com.internship.pbt.bizarechat.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.internship.pbt.bizarechat.R;
import com.internship.pbt.bizarechat.presentation.view.fragment.LoginFragment;

public class LoginActivity extends BasicActivity {

    public static Intent getCollingIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);
        addFragment(R.id.activityLayoutFragmentContainer, new LoginFragment());
    }
}
