package com.example.pizzaorder;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RadioButton radioHamAndCheese, radioHawaiian;
    RadioButton radioSmall, radioMedium, radioLarge;
    RadioButton radioThin, radioThick;
    CheckBox checkCheese, checkMushrooms, checkOnions, checkTomatoe, checkPineapple;
    Button buttonProcessOrder, buttonNewOrder;
    TextView textOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioHamAndCheese = findViewById(R.id.radioButtonHamAndCheese);
        radioHawaiian = findViewById(R.id.radioButton2Hawaian);


        radioSmall = findViewById(R.id.radioButton3Small);
        radioMedium = findViewById(R.id.radioButton4Medium);
        radioLarge = findViewById(R.id.radioButton5Large);

        radioThin = findViewById(R.id.radioButton6Thin);
        radioThick = findViewById(R.id.radioButton7Thick);

        checkCheese = findViewById(R.id.checkBoxCheese);
        checkMushrooms = findViewById(R.id.checkBox2Mushroom);
        checkOnions = findViewById(R.id.checkBox3Onions);
        checkTomatoe = findViewById(R.id.checkBox4Tomatoe);
        checkPineapple = findViewById(R.id.checkBox5Pineapple);

        buttonProcessOrder = findViewById(R.id.buttonProcessOrder);
        buttonNewOrder = findViewById(R.id.button2NewOrder);
        textOutput = findViewById(R.id.textView7Output);

    }

}