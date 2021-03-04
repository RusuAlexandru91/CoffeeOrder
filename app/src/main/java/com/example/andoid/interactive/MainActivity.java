package com.example.andoid.interactive;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    @SuppressLint("QueryPermissionsNeeded")
    public void submitOrder(View view) {
        // This is to see if the checkbox are clicked or not .
        CheckBox whippeadcream = findViewById(R.id.whipead_cream_checkbox);
        boolean hasWippedCream = whippeadcream.isChecked();
        CheckBox chocolate = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        // This is to get the name
        EditText nameField = findViewById(R.id.name);
        String addName = nameField.getText().toString();

        // This is to calculate price
        int price = calculatePrice(hasChocolate, hasWippedCream);
        String priceMessage = createOrderSummary(price, hasWippedCream, hasChocolate, addName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "Kiwibv@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You can not order more then 100 cups", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity + 1;
        }
        display(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You can not order less then 1 cup", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity - 1;
        }
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price
     */
    private int calculatePrice(boolean hasChocolate, boolean hasWippedCream) {
        int basePrice = 5;
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }
        if (hasWippedCream) {
            basePrice = basePrice + 1;
        }
        return quantity * basePrice;
    }

    /**
     * Method to crate a summary
     **/

    private String createOrderSummary(int price, boolean addWippeadCream, boolean addChocolate, String name) {
        String priceMessage = "Name : " + name;
        priceMessage += "\n With Wippead Cream :" + addWippeadCream;
        priceMessage += "\n With Chocolate :" + addChocolate;
        priceMessage += "\nQuantity : " + quantity;
        priceMessage += "\nTotal : " + price;
        priceMessage += "\nThank You !";
        return priceMessage;
    }
}
