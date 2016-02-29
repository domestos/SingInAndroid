package com.example.vpelenskyi.androidsingin.singin;

import android.database.Observable;
import android.os.AsyncTask;
import android.util.Log;

import com.example.vpelenskyi.androidsingin.communicaotr.BackendCommunicator;
import com.example.vpelenskyi.androidsingin.communicaotr.CommunicatorFactory;

/**
 * Created by v.pelenskyi on 29.02.2016.
 */
public class SingInModel {
    private static final String TAG = "SignInModel";
    private final SingInObservable mObservable = new SingInObservable();     //калс, що повідомляє підпищиків про зміни якщо вони відбулись
    private SingInTask mSingInTask;     //Калс потік який буде виконувати всю логіку
    private boolean mIsWorking; // мітка

    public SingInModel() {
        Log.i(TAG, "new Instance");
    }

    public void singIn(final String userName, final String password) {
        if (mIsWorking) {
            return;
        }
        mObservable.notifyStarted(); // запускаю progressBar в каласах які імплементять Observer
        mIsWorking = true; //міняю мітку - вказую, що запустили
        mSingInTask = new SingInTask(userName, password); // сворюється обєк AsynkTask
        mSingInTask.execute(); // запускаємо AsynkTask
    }

    public void stopSingIn() {
        if (mIsWorking) {
            mSingInTask.cancel(true);
            mIsWorking = false;
        }
    }

    public void registerObserver(final Observer observer) {
        mObservable.registerObserver(observer);
        if (mIsWorking) {
            observer.onSingInStarted(this);
        }
    }

    public void unregisterObserver(final Observer observer) {
        mObservable.unregisterObserver(observer);
    }


    //  методи в цьому класі будуть імпленемтовані в Observers  класи (SingInActivity)
    public interface Observer {
        void onSingInStarted(SingInModel singInModel);

        void onSingInSucceeded(SingInModel singInModel);

        void onSingInFainled(SingInModel singInModel);
    }

    // Observable клас розсилає всім Observers (SingInActivity) вказівку
    private class SingInObservable extends Observable<Observer> {

        //(зупускає progressBar в класі SingInActivity)
        public void notifyStarted() {
            // перебирає всі класи які реалізують методи інтерфейсу Observer  (SingInActivity )
            for (final Observer observer : mObservers) {
                //і запускає в них  реалізові метод даного інтерфейсу
                observer.onSingInStarted(SingInModel.this);
            }
        }

        public void notifySucceeded() {
            for (final Observer observer : mObservers) {
                observer.onSingInSucceeded(SingInModel.this);
            }
        }

        public void notifyFailed() {
            for (final Observer observer : mObservers) {
                observer.onSingInFainled(SingInModel.this);
            }
        }

    }

    private class SingInTask extends AsyncTask<Void, Void, Boolean> {

        private String mUserName;
        private String mPassword;

        public SingInTask(final String userName, final String password) {
            mUserName = userName;
            mPassword = password;

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            final BackendCommunicator communicator = CommunicatorFactory.creatBackendCommunicator();
            try {
                return communicator.postSingIn(mUserName, mPassword);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mIsWorking = false;
            if (success) {
                //повідомляю усіх обєктів що імплеменитують Observer,що відало відбувся процес
                // тобото викликаю у цих обєктів відповідний реалізований метод інтерфейсу Observer
                mObservable.notifySucceeded();
            } else {
                mObservable.notifyFailed();
            }

        }
    }
}
