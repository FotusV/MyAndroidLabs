package algonquin.cst2335.sinc0141;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @Author: Kyle Sincennes Winch
 * @Version 1.0
 */
public class MainActivity extends AppCompatActivity {
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);

  TextView textView = findViewById(R.id.textView);
  EditText myEditText = findViewById(R.id.myEditText);
  Button loginBtn = findViewById(R.id.loginBtn);

  loginBtn.setOnClickListener(clk -> {
   String password = myEditText.getText().toString();

   if (checkPasswordComplexity(password)) {
    textView.setText("Your password meets the requirements");
   } else {
    textView.setText("You shall not pass!");
   }
  });
 }

 /**
  * Checks the complexity of the password.
  *
  * @param password The String object that we are checking
  * @return true if the password is complex, false otherwise
  */
 boolean checkPasswordComplexity(String password) {
  boolean foundUpperCase = false;
  boolean foundLowerCase = false;
  boolean foundNumber = false;
  boolean foundSpecial = false;

  for (char c : password.toCharArray()) {
   if (Character.isUpperCase(c)) {
    foundUpperCase = true;
   } else if (Character.isLowerCase(c)) {
    foundLowerCase = true;
   } else if (Character.isDigit(c)) {
    foundNumber = true;
   } else {
    foundSpecial = true;
   }
  }

  /**
   * Returns the following toasts if password doesn't meet requirements
   */
  if (!foundUpperCase) {
   Toast.makeText(MainActivity.this, "Missing uppercase letter", Toast.LENGTH_SHORT).show();
   return false;
  } else if (!foundLowerCase) {
   Toast.makeText(MainActivity.this, "Missing lowercase letter", Toast.LENGTH_SHORT).show();
   return false;
  } else if (!foundNumber) {
   Toast.makeText(MainActivity.this, "Missing number", Toast.LENGTH_SHORT).show();
   return false;
  } else if (!foundSpecial) {
   Toast.makeText(MainActivity.this, "Missing special character", Toast.LENGTH_SHORT).show();
   return false;
  } else {
   return true; // Password is complex
  }
 }
}
