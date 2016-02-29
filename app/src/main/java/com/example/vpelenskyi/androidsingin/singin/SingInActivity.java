package com.example.vpelenskyi.androidsingin.singin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vpelenskyi.androidsingin.R;
import com.example.vpelenskyi.androidsingin.SuccessActivity;

/**
 * Created by v.pelenskyi on 26.02.2016.
 */
public class SingInActivity extends AppCompatActivity implements SingInModel.Observer {
    private static final String TAG = "SignInActivity";
    private static final String TAG_WORKER = "TAG_WORKER";

    private EditText mUserName;
    private EditText mPassword;
    private View mSubmit;
    private View mProgress;

    private SingInModel mSingInModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);

        mUserName = (EditText) findViewById(R.id.view_username);
        mPassword = (EditText) findViewById(R.id.view_password);
        mSubmit = findViewById(R.id.view_submit);
        mProgress = findViewById(R.id.view_progress);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = mUserName.getText().toString();
                final String password = mPassword.getText().toString();
                mSingInModel.singIn(userName, password);
            }
        });

//        final SignInWorkerFragment retainedWorkerFragment = (SignInWorkerFragment) getFragmentManager().findFragmentByTag(TAG_WORKER);
        final SignInWorkerFragment retainedWorkerFragment = (SignInWorkerFragment) getFragmentManager().findFragmentByTag(TAG_WORKER);

        if(retainedWorkerFragment !=null){
                mSingInModel = retainedWorkerFragment.getmSingInModel();
        }else{
            final SignInWorkerFragment workerFragment  = new SignInWorkerFragment();

            getFragmentManager().beginTransaction().add(workerFragment, TAG_WORKER).commit();
            mSingInModel = workerFragment.getmSingInModel();
            mSingInModel.registerObserver(this);
        }

    }




    @Override
    protected void onRestart() {
        Log.i(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        mSingInModel.unregisterObserver(this);
        if (isFinishing()) {
            mSingInModel.stopSingIn();
       }
    }


    /**
     * починає крутити progressBar коли зупускається метод singIn() в SingInModel.class
     * @param singInModel
     */
    @Override
    public void onSingInStarted(SingInModel singInModel) {
        Log.i(TAG, "onSignInStarted");
        showProgress(true);
    }


    @Override
    public void onSingInSucceeded(SingInModel singInModel) {
        Log.i(TAG, "onSignInSucceeded");
        finish();
        startActivity(new Intent(this, SuccessActivity.class));
    }

    @Override
    public void onSingInFainled(SingInModel singInModel) {
        Log.i(TAG, "onSignInFailed");
        showProgress(false);
        Toast.makeText(this, R.string.sign_in_error, Toast.LENGTH_SHORT).show();
    }
    private void showProgress(boolean show) {
        mUserName.setEnabled(!show);
        mPassword.setEnabled(!show);
        mSubmit.setEnabled(!show);
        mProgress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
