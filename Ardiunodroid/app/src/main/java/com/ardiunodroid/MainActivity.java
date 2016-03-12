package com.ardiunodroid;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ardiunodroid.controller.BluetoothController;
import com.ardiunodroid.controller.UIController;
import com.ardiunodroid.dialog.DeviceSelectDialog;
import com.ardiunodroid.listener.Listener;
import com.ardiunodroid.view.WASDControllerView;

public class MainActivity extends Activity implements Listener, WASDControllerView.WASDListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final int REQUEST_TURN_BLUETOOTH = 1;
    private static int STARTORSTOP = 0;

    private BluetoothController bluetoothController;
    private UIController uiController;
    private MenuItem statusItem;
    private MenuItem nameItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setTitle("");

        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.header_bg));
        bluetoothController = new BluetoothController(this);
        uiController        = new UIController(MainActivity.this);

        if (!bluetoothController.isBluetoothEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_TURN_BLUETOOTH);
        }

        WASDControllerView wasdView = (WASDControllerView) findViewById(R.id.wasd_view);
        wasdView.addOnItemClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TURN_BLUETOOTH:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "nooo oooook", Toast.LENGTH_LONG).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        statusItem = menu.findItem(R.id.action_connect);
        nameItem   = menu.findItem(R.id.device_name);

        View nameView = getLayoutInflater().inflate(R.layout.texview_action, null);
        nameItem.setActionView(nameView).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_connect) {
            if (bluetoothController.isDeviceConnected()) {
                Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
            } else {
                DeviceSelectDialog dialog = new DeviceSelectDialog(this, bluetoothController);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSendClick(View view) {
        bluetoothController.sendData("foo".getBytes());
    }

    @Override
    public void connectionInitiated(final BluetoothDevice device) {
        uiController.startStatusAnimation(statusItem);
        uiController.changeTextHeader(nameItem, device.getName());
    }

    @Override
    public void connectionStarted() {
        uiController.stopStatusAnimation(statusItem, true);
        bluetoothController.startCommunication();
    }

    @Override
    public void connectionFailed() {
        uiController.stopStatusAnimation(statusItem, false);
    }

    @Override
    public void connectionLost() {
        uiController.setStatus(statusItem, false);
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onWASDItemClicked(int command) {
        String text = "";

        switch (command) {
            case WASDControllerView.COMMAND_ACTION:
                if(STARTORSTOP == 0) {
                    text = "start";
                    STARTORSTOP = 1;
                } else {
                    text = "stop";
                    STARTORSTOP = 0;
                }
                break;
            case WASDControllerView.COMMAND_UP:
                text = "UP";
                break;
            case WASDControllerView.COMMAND_DOWN:
                text = "DOWN";
                break;
            case WASDControllerView.COMMAND_LEFT:
                text = "LEFT";
                break;
            case WASDControllerView.COMMAND_RIGHT:
                text = "RIGHT";
                break;
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        bluetoothController.sendData(text.getBytes());
    }
}