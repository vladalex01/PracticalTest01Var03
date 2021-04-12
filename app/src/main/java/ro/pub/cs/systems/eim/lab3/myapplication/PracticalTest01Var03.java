package ro.pub.cs.systems.eim.lab3.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.telecom.Call;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PracticalTest01Var03 extends AppCompatActivity {

    private int  sum, dif;
    private EditText firstNum, secondNum, operationField;
    private Button plusBut, minusBut, nextActivity;
    private String op;

    private int serviceStatus = Constants.SERVICE_STOPPED;
    private IntentFilter intentFilter = new IntentFilter();

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.plus:
                    Log.d(Constants.TAG, "PLUS BUTTON PRESSED");
                    if (firstNum.getText().toString().length() > 0 && secondNum.getText().toString().length() > 0 ) {
                        op = "+";
                        operationField.setText(firstNum.getText() + " + " + secondNum.getText());
                    }
                    break;
                case R.id.minus:
                    Log.d(Constants.TAG, "MINUS BUTTON PRESSED");
                    if (firstNum.getText().toString().length() > 0 && secondNum.getText().toString().length() > 0 ) {
                        op = "-";
                        operationField.setText(firstNum.getText() + " - " + secondNum.getText());
                    }
                    break;
            }
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var03Service.class);
        stopService(intent);
        super.onDestroy();
    }


    private final SaveInfoClickListener saveInfoClickListener = new SaveInfoClickListener();
    private class SaveInfoClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Log.d(Constants.TAG,"Save info");
            if (firstNum.getText().toString().length() > 0 && secondNum.getText().toString().length() > 0 && op.length() > 0) {

                if (serviceStatus == Constants.SERVICE_STOPPED) {
                    Log.d(Constants.TAG, "Service started");
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                    intent.putExtra(Constants.FIRST_NUM,  Integer.parseInt(firstNum.getText().toString()));
                    intent.putExtra(Constants.SECOND_NUM,  Integer.parseInt(secondNum.getText().toString()));
                    getApplicationContext().startService(intent);
                    serviceStatus = Constants.SERVICE_STARTED;
                }


                Intent intent = new Intent("ro.pub.cs.systems.eim.lab3.myapplication.intent.action.PracticalTest01Var03SecondaryActivity");
                intent.putExtra("ro.pub.cs.systems.eim.lab3.activity2.FIRST_NUM", Integer.parseInt(firstNum.getText().toString()));
                intent.putExtra("ro.pub.cs.systems.eim.lab3.activity2.SECOND_NUM",Integer.parseInt(secondNum.getText().toString()));
                intent.putExtra("ro.pub.cs.systems.eim.lab3.activity2.OP", op);
                startActivityForResult(intent, 2021);
            } else {
                Toast.makeText(getApplication(), "SOME ERROR", Toast.LENGTH_LONG).show();
            }

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNum = (EditText)findViewById(R.id.first_number);
        secondNum = (EditText)findViewById(R.id.second_number);

        plusBut = (Button)findViewById(R.id.plus);
        plusBut.setOnClickListener(buttonClickListener);

        minusBut = (Button)findViewById(R.id.minus);
        minusBut.setOnClickListener(buttonClickListener);

        operationField = (EditText)findViewById(R.id.operation);

        nextActivity = (Button)findViewById(R.id.navigate_second_activity);
        nextActivity.setOnClickListener(saveInfoClickListener);

        intentFilter.addAction("opeationPLUS");
        intentFilter.addAction("opeationMINUS");

        if (savedInstanceState == null) {
            Log.d(Constants.TAG , "NO PREVIOUS STATE");
        } else {
            Log.d(Constants.TAG , "FOUND PREVIOUS STATE");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(Constants.TAG, "onSaveInstanceState() method invoked");
        savedInstanceState.putString(Constants.FIRST_NUM, firstNum.getText().toString());
        savedInstanceState.putString(Constants.SECOND_NUM, secondNum.getText().toString());
        savedInstanceState.putString(Constants.OPERATION, operationField.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState ) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(Constants.TAG, "onRestoreInstanceState() method invoked");
        if (savedInstanceState.containsKey(Constants.FIRST_NUM)) {
            firstNum = (EditText)findViewById(R.id.first_number);
            firstNum.setText(savedInstanceState.getString(Constants.FIRST_NUM));
        }
        if (savedInstanceState.containsKey(Constants.SECOND_NUM)) {
            secondNum = (EditText)findViewById(R.id.second_number);
            secondNum.setText(savedInstanceState.getString(Constants.SECOND_NUM));
        }
        if (savedInstanceState.containsKey(Constants.OPERATION)) {
            operationField = (EditText)findViewById(R.id.operation);
            operationField.setText(savedInstanceState.getString(Constants.OPERATION));
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 2021:
                Toast.makeText(this, "Activity 2 returned with result " + resultCode, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
