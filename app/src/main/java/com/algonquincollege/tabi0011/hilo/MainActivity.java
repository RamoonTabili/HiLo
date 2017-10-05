/**
 * The purpose of this application is for the user to be able to correctly guess the randomly generated number. Only positive integer values between 1 and 1000 are accepted. The user
 * has 10 total guesses per game and will get 1 of 2 different win messages when the number is guessed correctly. The user has an option of revealing the randomly generated number by
 * long-clicking the reset button.
 *
 * @author Ramon Tabilin (tabi0011@algonquinlive.com)
 */

package com.algonquincollege.tabi0011.hilo;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {

    Random rand = new Random();
    int theNumber = rand.nextInt(1000) + 1;

    private final int maxNum = 1000;
    private final int minNum = 1;
    private final int maxGuess = 10;
    private final int greatGuess = 5;
    int guessCount = 0;
    int userNum = 0;

    private static final String ABOUT_DIALOG_TAG = "About Dialog";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private EditText userGuess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userGuess = findViewById(R.id.inputGuess);

        final Button tryGuess = findViewById(R.id.buttonGuess);
        Button resetGuess = findViewById(R.id.buttonReset);

        tryGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value = userGuess.getText().toString();

                if (value.isEmpty()) {
                    userGuess.setError("Please enter a number.");
                    userGuess.requestFocus();
                    return;
                }

                try {
                    userNum = Integer.parseInt(value);
                } catch (Exception e) {
                    userGuess.setError("Value has to be an number.");
                    userGuess.requestFocus();
                    userGuess.setText("");
                    return;
                }

                if (guessCount < maxGuess) {
                    if (userNum > maxNum || userNum < minNum) {
                        userGuess.setError("Value has to be in between 1 and 1000");
                        userGuess.requestFocus();
                        userGuess.setText("");
                        return;
                    }
                    if (userNum != theNumber) {
                        if (userNum > theNumber) {
                            guessCount++;
                            userGuess.setError("Too high dude.");
                            userGuess.requestFocus();
                            userGuess.setText("");
                            return;
                        } else {
                            guessCount++;
                            userGuess.setError("Too low man.");
                            userGuess.requestFocus();
                            userGuess.setText("");
                            return;
                        }
                    }
                    if (userNum == theNumber) {
                        if (guessCount <= greatGuess) {
                            Toast.makeText(getApplicationContext(), "Superior win.", Toast.LENGTH_SHORT).show();
                            userGuess.setText("");
                            theNumber = rand.nextInt(1000) + 1;
                            guessCount = 0;
                            return;
                        } else {
                            Toast.makeText(getApplicationContext(), "Excellent win.", Toast.LENGTH_SHORT).show();
                            userGuess.setText("");
                            theNumber = rand.nextInt(1000) + 1;
                            guessCount = 0;
                            return;
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please reset.", Toast.LENGTH_SHORT).show();
                    userGuess.setText("");
                    return;
                }


            }
        });

        resetGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Reseting numbers", Toast.LENGTH_SHORT).show();
                theNumber = rand.nextInt(1000) + 1;
                guessCount = 0;
                userGuess.setText("");
                return;
            }
        });
        resetGuess.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getApplicationContext(), "The number is: " + theNumber, Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

}
