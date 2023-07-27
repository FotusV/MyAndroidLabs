package data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import algonquin.cst2335.sinc0141.ChatRoom;

@Database(entities = {ChatRoom.ChatMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {
    public abstract ChatMessageDAO cmDAO();
}