package com.example.vpelenskyi.androidsingin.communicaotr;

/**
 * Created by v.pelenskyi on 29.02.2016.
 */
public class BackendCommunicatorStub implements BackendCommunicator {
    private static  final String  VALID_USERNAME = "varenik";
    private static  final String VALID_PASSWORD = "4554722";


    @Override
    public boolean postSingIn(String userName, String password) throws InterruptedException {
        Thread.sleep(8000);
        return VALID_USERNAME.equals(userName) && VALID_PASSWORD.equals(password);
    }
}
