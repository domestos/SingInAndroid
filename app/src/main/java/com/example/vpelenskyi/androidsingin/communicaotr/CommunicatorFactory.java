package com.example.vpelenskyi.androidsingin.communicaotr;

/**
 * Created by v.pelenskyi on 29.02.2016.
 */
public class CommunicatorFactory {
    public static BackendCommunicator creatBackendCommunicator(){
        return new BackendCommunicatorStub();
    }
}
