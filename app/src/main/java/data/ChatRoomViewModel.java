package data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.sinc0141.ChatRoom;

public class ChatRoomViewModel extends ViewModel {
    public MutableLiveData<ArrayList<ChatRoom.ChatMessage>> messages = new MutableLiveData<>();
    public MutableLiveData<ChatRoom.ChatMessage> selectedMessage = new MutableLiveData< >();

}