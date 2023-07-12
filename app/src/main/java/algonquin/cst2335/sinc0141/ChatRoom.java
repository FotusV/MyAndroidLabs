package algonquin.cst2335.sinc0141;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import algonquin.cst2335.sinc0141.databinding.ActivityChatRoomBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}