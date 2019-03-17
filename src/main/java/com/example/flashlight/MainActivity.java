package com.example.flashlight;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView stateText;
    private States currentState;
    private ConstraintLayout background;

    private FlashLight flashLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateText = (TextView) findViewById(R.id.stateText);
        stateText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        background = (ConstraintLayout) findViewById(R.id.background);

        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flashLight.destroy();
        finish();
    }

    public void onDisplayClick(View view) {
        changeState();
        handleState();
    }

    private void init() {
        flashLight = new FlashLight(this, this);

        try {
            flashLight.init();
            currentState = States.TURNED_OFF;
        } catch (Exception e) {
            currentState = States.ERROR;
        }

        handleState();
    }

    private void changeState() {
        if (currentState == States.TURNED_OFF) {
            currentState = States.TURNED_ON;
        } else if (currentState == States.TURNED_ON) {
            currentState = States.TURNED_OFF;
        }
    }

    private void handleState() {
        switch (currentState) {
            case TURNED_ON:
                flashLight.turnOn();
                stateText.setText("Вимкнути");
                background.setBackgroundColor(Color.YELLOW);
                break;
            case TURNED_OFF:
                flashLight.turnOff();
                stateText.setText("Увімкнути");
                background.setBackgroundColor(Color.WHITE);
                break;
            case ERROR:
                stateText.setText("Помилка...");
                background.setBackgroundColor(Color.RED);
                break;
        }
    }

    private enum States {
        TURNED_ON, TURNED_OFF, ERROR
    }
}
