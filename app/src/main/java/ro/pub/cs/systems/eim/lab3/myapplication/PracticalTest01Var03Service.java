package ro.pub.cs.systems.eim.lab3.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PracticalTest01Var03Service extends Service {

    private ProcessingThread processingThread = null;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d(Constants.TAG, "On START COMMAND SERVICE");
        int firstNumber = intent.getIntExtra(Constants.FIRST_NUM, -1);
        int secondNumber = intent.getIntExtra(Constants.SECOND_NUM, -1);
        processingThread = new ProcessingThread(this, firstNumber, secondNumber);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}