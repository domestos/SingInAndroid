package com.example.vpelenskyi.androidsingin.communicaotr;

/**
 * Created by v.pelenskyi on 29.02.2016.
 */
public interface BackendCommunicator {
    boolean postSingIn(String userName, String password) throws InterruptedException;
}
