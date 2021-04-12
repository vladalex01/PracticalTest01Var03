package ro.pub.cs.systems.eim.lab3.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;

public class ProcessingThread extends Thread {
    private Context context;
    private boolean isRunning;

    private int sum, dif;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;
        sum = firstNumber + secondNumber;
        dif = firstNumber - secondNumber;

        isRunning = true;
    }

    private void sendMessageAdd() {
        Intent intent = new Intent();
        intent.setAction("opeationPLUS");
        Log.d(Constants.TAG, "SUM is: " + sum);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " SUM IS " +  sum);
        context.sendBroadcast(intent);
    }
    private void sendMessageDif() {
        Intent intent = new Intent();
        intent.setAction("opeationPLUS");
        Log.d(Constants.TAG, "DIF is: " + dif);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                new Date(System.currentTimeMillis()) + " DIF IS " +  dif);
        context.sendBroadcast(intent);
    }


    @Override
    public void run() {
        Log.d(Constants.TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());

        sendMessageAdd();
        sleep();
        sendMessageDif();
        isRunning = false;
        Log.d(Constants.TAG, "Thread Stopped!");
    }

    public void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopThread() {
        Log.d(Constants.TAG,"Stopping THREAD");
        isRunning = false;
    }
}
