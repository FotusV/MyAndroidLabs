package algonquin.cst2335.sinc0141;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.sinc0141.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.sinc0141.databinding.ReceiveMessageBinding;
import algonquin.cst2335.sinc0141.databinding.SentMessageBinding;
import data.ChatMessageDAO;
import data.ChatRoomViewModel;
import data.MessageDatabase;
import data.MessageDetailsFragment;


/**
 * Activity for displaying a chat room and handling user interactions.
 */
public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages = new ArrayList<>();

    ChatRoomViewModel chatModel;

    /**
     * Represents a chat message.
     */

    @Entity
    public static class ChatMessage {
        @ColumnInfo(name = "message")
        protected String message;

        @ColumnInfo(name = "timeSent")
        protected String timeSent;

        @ColumnInfo(name = "SendOrReceive")
        protected boolean isSentButton;

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        public int id;

        public ChatMessage(String message, String timeSent, boolean isSentButton) {
            this.message = message;
            this.timeSent = timeSent;
            this.isSentButton = isSentButton;
        }

        public String getMessage() {
            return message;
        }

        public String getTimeSent() {
            return timeSent;
        }

        public boolean isSentButton() {
            return isSentButton;
        }
    }

    private RecyclerView.Adapter<MyRowHolder> myAdapter;

    private ChatMessageDAO mDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel.selectedMessage.observe(this, (newMessageValue) -> {
            MessageDetailsFragment chatFragment = new MessageDetailsFragment(newMessageValue);

            // Get the FragmentManager
            FragmentManager fMgr = getSupportFragmentManager();

            // Begin the FragmentTransaction
            FragmentTransaction tx = fMgr.beginTransaction();

            // Replace the existing fragment (if any) with the new one
            tx.replace(R.id.fragmentLocation, chatFragment);

            // Commit the transaction to apply the changes
            tx.commit();
        });

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        ArrayList<ChatMessage> chatMessages = chatModel.messages.getValue();
        if (messages == null) {
            chatModel.messages.setValue(messages = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll(mDAO.getAllMessages()); //Once you get the data from database


                runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter)); //You can then load the RecyclerView
            });
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recycleView.setLayoutManager(layoutManager);

        myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(inflater, parent, false);
                    return new MyRowHolder(binding.getRoot());
                } else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(inflater, parent, false);
                    return new MyRowHolder(binding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage chatMessage = messages.get(position);
                holder.bind(chatMessage, mDAO);
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                ChatMessage chatMessage = messages.get(position);
                if (chatMessage.isSentButton()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        };

        binding.recycleView.setAdapter(myAdapter);

        binding.sendButton.setOnClickListener(click -> {
            String message = binding.textInput.getText().toString();
            String timeSent = getCurrentTime();
            boolean isSentButton = true;
            ChatMessage chatMessage = new ChatMessage(message, timeSent, isSentButton);
            messages.add(chatMessage);

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                mDAO.insertMessage(chatMessage); // Insert the message into the database

                List<ChatMessage> allMessages = mDAO.getAllMessages(); // Retrieve all messages from the database
                messages.clear();
                messages.addAll(allMessages);

                runOnUiThread(() -> {
                    myAdapter.notifyItemInserted(messages.size() - 1);
                    binding.textInput.setText("");
                    chatModel.messages.postValue(messages);
                });
            });
        });
        binding.receiveButton.setOnClickListener(click -> {
            String message = binding.textInput.getText().toString();
            String timeSent = getCurrentTime();
            boolean isSentButton = false;
            ChatMessage chatMessage = new ChatMessage(message, timeSent, isSentButton);
            messages.add(chatMessage);
            myAdapter.notifyItemInserted(messages.size() - 1);
            binding.textInput.setText("");

            chatModel.messages.postValue(messages); // Update ViewModel with the latest messages
        });
    }

    /**
     * Get the current time in the specified format.
     *
     * @return The current time as a formatted string.
     */
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy \nhh:mm:ss a");
        return sdf.format(new Date());
    }

    /**
     * ViewHolder class for holding the views of each row in the RecyclerView.
     */
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }

        public void bind(ChatMessage chatMessage, ChatMessageDAO mDAO) {
            messageText.setText(chatMessage.getMessage());
            timeText.setText(chatMessage.getTimeSent());

            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                ChatMessage removeMessage = messages.get(position);



//                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
//                builder.setMessage("Do you want to delete this message: " + chatMessage.getMessage());
//                builder.setTitle("Question");
//                builder.setNegativeButton("No", (dialog, cl) -> {
//                });
//                builder.setPositiveButton("Yes", (dialog, cl) -> {
//
//                    ChatMessage removedMessage = messages.get(position);
//                    messages.remove(position);
//                    myAdapter.notifyItemRemoved(position);
//
//                    Snackbar.make(itemView, "You deleted message #" + position, Snackbar.LENGTH_LONG)
//                            .setAction("Undo", undoclk -> {
//                                // Undo logic here
//                                messages.add(position, removedMessage);
//                                myAdapter.notifyItemInserted(position);
//                            })
//                            .show();
//                });
//                builder.create().show();
            });
        }
    }


    /**
     * Bind the chat message data to the views.
     *
     * @param chatMessage The chat message to bind.
     */

    /**
     * Remove a message from the ArrayList at the specified position.
     *
     * @param position The position of the message to remove.
     */
    public void removeMessage(int position) {
        messages.remove(position);
        myAdapter.notifyItemRemoved(position);
    }



    /**
     * Update the entire ArrayList of messages with the new messages.
     *
     * @param newMessages The new ArrayList of messages.
     */
    public void updateMessages(ArrayList<ChatMessage> newMessages) {
        messages.clear();
        messages.addAll(newMessages);
        myAdapter.notifyDataSetChanged();
    }
}