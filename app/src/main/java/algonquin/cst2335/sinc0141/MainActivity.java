package algonquin.cst2335.sinc0141;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);

  TextView textView = findViewById(R.id.textView);
  EditText myEditText = findViewById(R.id.myEditText);
  Button loginBtn = findViewById(R.id.loginBtn);

  loginBtn.setOnClickListener( clk ->{
   String password = myEditText.getText().toString();

   checkPasswordComplexity( password );

  });
 }

 /**
  *
  * @param password The String object that we are checking
  */
  boolean checkPasswordComplexity(String password) {

   boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;

   foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

   if(!foundUpperCase)
   {

    Toast.makeText( "Missing Upper case letter" ) ;// Say that they are missing an upper case letter;

    return false;

   }

   else if( ! foundLowerCase)
   {
    Toast.makeText( "Missing lower case letter" ); // Say that they are missing a lower case letter;

    return false;

   }

   else if( ! foundNumber) { "Missing number"
   Toast.makeText()
   }


   else if(! foundSpecial) { "Missing special character" }

   else

    return true; //only get here if they're all true

   return foundUpperCase;
  }
}