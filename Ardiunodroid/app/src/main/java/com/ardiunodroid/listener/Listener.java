package com.ardiunodroid.listener;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

/**
 * Created by hacker on 12/3/16.
 */
public interface Listener {
    public void connectionInitiated(BluetoothDevice device);
    public void connectionStarted();
    public void connectionFailed();
    public void connectionLost();
    public Context getContext();
}
