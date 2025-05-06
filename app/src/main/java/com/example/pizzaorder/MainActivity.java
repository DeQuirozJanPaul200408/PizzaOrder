package com.example.pizzaorder;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    RadioGroup pizzaTypeGroup; // Added this line for pizza types

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize radio buttons
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

        // Grouping pizza type buttons manually (optional if you don't use RadioGroup in XML)
        radioHamAndCheese.setOnClickListener(v -> radioHawaiian.setChecked(false));
        radioHawaiian.setOnClickListener(v -> radioHamAndCheese.setChecked(false));

        // Setting listeners for size selection to ensure one is unchecked when the other is selected
        radioSmall.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radioMedium.setChecked(false);  // Uncheck Medium
                radioLarge.setChecked(false);   // Uncheck Large
            }
        });

        radioMedium.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radioSmall.setChecked(false);   // Uncheck Small
                radioLarge.setChecked(false);   // Uncheck Large
            }
        });

        radioLarge.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radioSmall.setChecked(false);   // Uncheck Small
                radioMedium.setChecked(false);  // Uncheck Medium
            }
        });

        // Setting listeners for crust selection to ensure one is unchecked when the other is selected
        radioThin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radioThick.setChecked(false);  // Uncheck Thick
            }
        });

        radioThick.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radioThin.setChecked(false);  // Uncheck Thin
            }
        });

        buttonProcessOrder.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("PWD ID or SENIOR ID")
                    .setMessage("Do you have a PWD or Senior Citizen ID?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        final EditText input = new EditText(MainActivity.this);
                        input.setHint("Enter 12-digit PWD/Senior ID number");
                        input.setInputType(InputType.TYPE_CLASS_NUMBER);

                        AlertDialog dialog1 = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Enter ID Number")
                                .setView(input)
                                .setCancelable(false)
                                .setPositiveButton("Submit", null)
                                .setNegativeButton("Cancel", (d, w) -> d.dismiss())
                                .create();

                        dialog1.setOnShowListener(dialogInterface -> {
                            Button submitButton = dialog1.getButton(AlertDialog.BUTTON_POSITIVE);
                            submitButton.setOnClickListener(view -> {
                                String idNumber = input.getText().toString().trim();
                                if (idNumber.length() == 12 && idNumber.matches("\\d{12}")) {
                                    processOrder(true, idNumber);
                                    dialog1.dismiss();
                                } else {
                                    input.setError("ID number must be exactly 12 digits");
                                }
                            });
                        });

                        dialog1.show();
                    })
                    .setNegativeButton("No", (dialog, which) -> processOrder(false, ""))
                    .setCancelable(false)
                    .show();
        });

        buttonNewOrder.setOnClickListener(v -> resetForm());
    }

    private void processOrder(boolean hasPWD, String idNumber) {
        StringBuilder order = new StringBuilder("Your Order is:\n");

        // Pizza Type
        String pizzaType = "";
        double pizzaPrice = 0.0;
        if (radioHamAndCheese.isChecked()) {
            pizzaType = "Ham and Cheese";
            pizzaPrice = 150;
        } else if (radioHawaiian.isChecked()) {
            pizzaType = "Hawaiian";
            pizzaPrice = 250;
        }
        order.append("Pizza Type: ").append(pizzaType).append(" - ₱").append(pizzaPrice).append("\n");

        // Pizza Size
        String size = "";
        double sizePrice = 0.0;

        if (radioSmall.isChecked()) {
            size = "Small";
            sizePrice = pizzaType.equals("Ham and Cheese") ? 150 : 250;
        } else if (radioMedium.isChecked()) {
            size = "Medium";
            sizePrice = pizzaType.equals("Ham and Cheese") ? 200 : 350;
        } else if (radioLarge.isChecked()) {
            size = "Large";
            sizePrice = pizzaType.equals("Ham and Cheese") ? 250 : 450;
        }
        order.append("Size: ").append(size).append(" - ₱").append(sizePrice).append("\n");

        // Crust
        String crust = "";
        double crustPrice = 0.0;
        if (radioThin.isChecked()) {
            crust = "Thin";
        } else if (radioThick.isChecked()) {
            crust = "Thick";
            crustPrice = sizePrice * 0.5;
        }
        order.append("Crust: ").append(crust);
        if (crustPrice > 0) {
            order.append(" - ₱").append(crustPrice);
        }
        order.append("\n");

        // Toppings
        order.append("Toppings: ");
        double toppingsPrice = 0.0;
        boolean hasTopping = false;

        if (checkCheese.isChecked()) {
            order.append("Cheese - ₱20 ");
            toppingsPrice += 20;
            hasTopping = true;
        }
        if (checkMushrooms.isChecked()) {
            order.append("Mushrooms - ₱20 ");
            toppingsPrice += 20;
            hasTopping = true;
        }
        if (checkOnions.isChecked()) {
            order.append("Onions - ₱10 ");
            toppingsPrice += 10;
            hasTopping = true;
        }
        if (checkTomatoe.isChecked()) {
            order.append("Tomato - ₱10 ");
            toppingsPrice += 10;
            hasTopping = true;
        }
        if (checkPineapple.isChecked()) {
            order.append("Pineapple - ₱15 ");
            toppingsPrice += 15;
            hasTopping = true;
        }
        if (!hasTopping) {
            order.append("None");
        }

        double totalPrice = pizzaPrice + sizePrice + crustPrice + toppingsPrice;

        if (hasPWD) {
            double discount = totalPrice * 0.20;
            totalPrice -= discount;
            order.append("\nPWD/Senior Discount: 20%");
            order.append("\nID Number: ").append(idNumber);
        } else {
            order.append("\nPWD/Senior Discount: Not Applied");
        }

        order.append(String.format("\nTotal Price: ₱%.2f", totalPrice));
        textOutput.setText(order.toString());
    }

    private void resetForm() {
        radioHamAndCheese.setChecked(false);
        radioHawaiian.setChecked(false);
        radioSmall.setChecked(false);
        radioMedium.setChecked(false);
        radioLarge.setChecked(false);
        radioThin.setChecked(false);
        radioThick.setChecked(false);

        checkCheese.setChecked(false);
        checkMushrooms.setChecked(false);
        checkOnions.setChecked(false);
        checkTomatoe.setChecked(false);
        checkPineapple.setChecked(false);

        textOutput.setText("");
    }
}
