package com.internship.pbt.bizarechat.presentation.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.facebook.FacebookSdk;
import com.internship.pbt.bizarechat.BuildConfig;
import com.internship.pbt.bizarechat.data.repository.UserToken;
import com.internship.pbt.bizarechat.presentation.model.CurrentUser;
import com.internship.pbt.bizarechat.presentation.presenter.splash.SplashPresenter;
import com.internship.pbt.bizarechat.presentation.presenter.splash.SplashScreenPresenter;

public class SplashActivity extends BaseActivity {

    private SplashScreenPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new SplashPresenter(this);

        // TODO: 1/30/17 [Code Review] The logic below is a part of business logic and should not be here
        if (!CurrentUser.getInstance().isAuthorized())
            navigateToLoginActivity();
        else if (CurrentUser.getInstance().isAuthorized() && UserToken.getInstance().isTokenExists())
            navigateToMainActivity();
        else
            navigateToLoginActivity();

        // TODO: 1/30/17 [Code Review] this will finish current activity, lol. What about 3 seconds delay?
        finish();
    }

    public void navigateToMainActivity() {
        mNavigator.navigateToMainActivity(this);
    }

    public void navigateToLoginActivity() {

        new Handler().postDelayed(
                () -> mNavigator.navigateToLoginActivity(this),
                BuildConfig.SPLASH_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();

    }
}