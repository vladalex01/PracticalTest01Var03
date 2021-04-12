package ro.pub.cs.systems.eim.lab3.myapplication;
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


public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {

    private Button correctBut, incorrectBut;
    private EditText resultField;
    private int result;


    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.correct:
//                    Intent intent = new Intent();
//                    intent.putExtra("OK_KEY", "ceva");
                    Log.d("TAG", "OK BUTTON PRESSED");
                    setResult(RESULT_OK, null);
                    break;
                case R.id.incorrect:
//                    Intent intent = new Intent();
//                    intent.putExtra("OK_KEY", "ceva");
                    Log.d("TAG", "CANCEL BUTTON PRESSED");
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        Log.d(Constants.TAG, "AICI ACTIVITY 2");
        Intent intent = getIntent();
        if (intent != null) {
            int firstNum = intent.getIntExtra("ro.pub.cs.systems.eim.lab3.activity2.FIRST_NUM", -1);
            int secondNum = intent.getIntExtra("ro.pub.cs.systems.eim.lab3.activity2.SECOND_NUM", -1);
            String op = intent.getStringExtra("ro.pub.cs.systems.eim.lab3.activity2.OP");
            if (op.contains("+")) {
                result = firstNum + secondNum;
            } else {
                result = firstNum - secondNum;
            }

            resultField = (EditText)findViewById(R.id.result);
            resultField.setText(firstNum + " " + op + " " + secondNum +  " = " + result);

        } else {
            Toast.makeText(this, "SOME ERROR", Toast.LENGTH_LONG).show();
        }



        correctBut = (Button)findViewById(R.id.correct);
        correctBut.setOnClickListener(buttonClickListener);
        incorrectBut = (Button)findViewById(R.id.incorrect);
        incorrectBut.setOnClickListener(buttonClickListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 2021:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }
}