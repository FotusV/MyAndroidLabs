package ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import algonquin.cst2335.sinc0141.databinding.ActivityMainBinding;
import data.MainViewModel;

public class MainActivity extends AppCompatActivity {
 private ActivityMainBinding variableBinding;
 private MainViewModel model;
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);

  model = new ViewModelProvider(this).get(MainViewModel.class);

  variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
  setContentView(variableBinding.getRoot());




 }
}