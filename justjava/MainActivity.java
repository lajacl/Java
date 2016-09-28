/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */
package tech.luv.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int cupsOfCoffee = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        if (cupsOfCoffee >= 100) {
            // Show an error message as a toast
            Toast.makeText(MainActivity.this, "You Can Only Order a Max of 100 Cups of Coffee ", Toast.LENGTH_SHORT).show();
            // Exit this method early because there is nothing left to do
            return;
        }
        cupsOfCoffee++;
        display(cupsOfCoffee);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (cupsOfCoffee <= 1) {
            // Show an error message as a toast
            Toast.makeText(MainActivity.this, "You Must Order at Least 1 Cup of Coffee ", Toast.LENGTH_SHORT).show();
            // Exit this method early because there is nothing left to do
            return;
        }
        cupsOfCoffee--;
        display(cupsOfCoffee);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText editTextName = (EditText) findViewById(R.id.edittext_name);
        String name = editTextName.getText().toString();

        CheckBox checkBoxWhippedCream = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
        boolean hasWhippedCream = checkBoxWhippedCream.isChecked();

        CheckBox checkBoxChocolate = (CheckBox) findViewById(R.id.checkbox_chocolate);
        boolean hasChocolate = checkBoxChocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        emailMessage(name, createOrderSummary(name, price, hasWhippedCream, hasChocolate));

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:0,0?q=510+Treehills+Parkway+Stone+Mountain+%2C+Georgia+30088"));
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants whipped cream
     * @return price is the cost for the number of cups of coffee ordered
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int pricePerCup = 5;
        if (addWhippedCream) {
            pricePerCup += 1;
        }
        if (addChocolate) {
            pricePerCup += 2;
        }
        return pricePerCup * cupsOfCoffee;
    }

    /**
     * Creates summary of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants whipped cream
     * @return text summary
     */
    private String createOrderSummary(String customer, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, customer);
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + cupsOfCoffee;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method send an email of the order.
     */
    private void emailMessage(String customer, String message) {

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + customer);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
