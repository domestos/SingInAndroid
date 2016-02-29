package com.example.vpelenskyi.androidsingin.singin;

import android.os.Bundle;
import android.app.Fragment;

/**
 * Created by v.pelenskyi on 29.02.2016.
 */
public class SignInWorkerFragment extends Fragment {
    private final SingInModel mSingInModel;

    public SignInWorkerFragment() {
        mSingInModel = new SingInModel();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // забороняємо фрагменту (цьому класу)
        // заново створюватись при повороті  екрана
        setRetainInstance(true);
    }

    // тобто при повороті екрану нове актівіти отримає старий обєкт mSingInModel
    // оскільки фрагменту забороненно перестворюватись
    public SingInModel getmSingInModel() {
        return mSingInModel;
    }
}
