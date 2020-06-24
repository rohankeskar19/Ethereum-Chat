package com.ethereumchat.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ethereumchat.Database.ChatContract.*;
import com.ethereumchat.Models.*;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;


public class ChatDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "chat.db";
    public static final int DATABASE_VERSION = 1;



    private static final String TAG = "ChatDBHelper";

    public ChatDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQLITE_CREATE_CONVERSATIONS_TABLE = "CREATE TABLE " + ConversationEntry.TABLE_NAME + " (" + ConversationEntry.COLUMN_NAME + " VARCHAR(40) NOT NULL, " +  ConversationEntry.COLUMN_PUBLIC_KEY + " VARCHAR(500) NOT NULL, " +  ConversationEntry.COLUMN_PROFILE_IN_STRING + " VARCHAR(65535) NOT NULL, " + ConversationEntry.COLUMN_LAST_MESSAGE + " VARCHAR(65535) NOT NULL, " + ConversationEntry.COLUMN_LAST_MESSAGE_TIMESTAMP + " VARCHAR(25) NOT NULL, " + ConversationEntry.COLUMN_READ + " VARCHAR(10) NOT NULL);";
        final String SQLITE_CREATE_MESSAGES_TABLE = "CREATE TABLE " + MessageEntry.TABLE_NAME + " ("  + MessageEntry.COLUMN_NAME + " VARCHAR(30) NOT NULL, " + MessageEntry.COLUMN_TIMESTAMP + " VARCHAR(25) NOT NULL, " + MessageEntry.COLUMN_IS_IMAGE + " VARCHAR(20) NOT NULL, " + MessageEntry.COLUMN_MESSAGE + " VARCHAR(65535) NOT NULL, " + MessageEntry.COLUMN_FROM_PUBLIC_KEY + " VARCHAR(500) NOT NULL, "  + MessageEntry.COLUMN_TO_PUBLIC_KEY + " VARCHAR(30) NOT NULL);";
        final String SQLITE_CREATE_CONTACTS_TABLE = "CREATE TABLE " + ContactEntry.TABLE_NAME + " (" + ContactEntry.COLUMN_NAME + " VARCHAR(40) NOT NULL, " +  ContactEntry.COLUMN_PUBLIC_KEY + " VARCHAR(500) NOT NULL, " +  ContactEntry.COLUMN_PROFILE_IN_STRING + " VARCHAR(65535) NOT NULL);";
        //final String SQLITE_CREATE_ACCOUNT_TABLE = "CREATE TABLE " + AccountEntry.TABLE_NAME + " ("  + AccountEntry.COLUMN_NAME + " VARCHAR(30) NOT NULL, " + AccountEntry.COLUMN_PUBLIC_KEY + " VARCHAR(25) NOT NULL, " + AccountEntry.COLUMN_PRIVATE_KEY + " VARCHAR(20) NOT NULL, " + AccountEntry.COLUMN_PASSWORD + " VARCHAR(20) NOT NULL, " + AccountEntry.COLUMN_KEYPAIR + " VARCHAR(65535) NOT NULL);";
        db.execSQL(SQLITE_CREATE_CONVERSATIONS_TABLE);
        db.execSQL(SQLITE_CREATE_MESSAGES_TABLE);
        db.execSQL(SQLITE_CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ConversationEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MessageEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ContactEntry.TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + AccountEntry.TABLE_NAME);

        onCreate(db);
    }

    public void addConversation(Conversation conversation){
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "addConversation: " + conversation.toString());
        ContentValues contentValues = new ContentValues();
        contentValues.put(ChatContract.ConversationEntry.COLUMN_NAME,conversation.getName());
        contentValues.put(ChatContract.ConversationEntry.COLUMN_PUBLIC_KEY,conversation.getPublicKey());
        contentValues.put(ChatContract.ConversationEntry.COLUMN_PROFILE_IN_STRING, conversation.getProfileInString());
        contentValues.put(ChatContract.ConversationEntry.COLUMN_LAST_MESSAGE,conversation.getLastMessage());
        contentValues.put(ChatContract.ConversationEntry.COLUMN_LAST_MESSAGE_TIMESTAMP,conversation.getLastMessageTimestamp());
        contentValues.put(ChatContract.ConversationEntry.COLUMN_READ,conversation.getRead());


        db.insert(ChatContract.ConversationEntry.TABLE_NAME,null,contentValues);

        db.close();

    }


    public Conversation checkIfConversationExists(String public_key){
        Log.d(TAG, "checkIfConversationExists: Called");
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ConversationEntry.TABLE_NAME, new String[]
                        {ConversationEntry.COLUMN_NAME, ConversationEntry.COLUMN_PUBLIC_KEY, ConversationEntry.COLUMN_PROFILE_IN_STRING, ConversationEntry.COLUMN_LAST_MESSAGE, ConversationEntry.COLUMN_LAST_MESSAGE_TIMESTAMP,ConversationEntry.COLUMN_READ},
                ConversationEntry.COLUMN_PUBLIC_KEY + "=?", new String[] {public_key},null, null, null, null);
        if (cursor != null && cursor.moveToFirst()){
            cursor.moveToFirst();
            Log.d(TAG, "checkIfConversationExists: " + cursor.getString(0));
            Log.d(TAG, "checkIfConversationExists: " + cursor.getString(1));
            Log.d(TAG, "checkIfConversationExists: " + cursor.getString(2));
            Log.d(TAG, "checkIfConversationExists: " + cursor.getString(3));
            Log.d(TAG, "checkIfConversationExists: " + cursor.getString(4));
            Log.d(TAG, "checkIfConversationExists: " + cursor.getString(5));

            Conversation conversation = new Conversation(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
            cursor.close();
            return conversation;
        }

        return null;

    }

    public String getAllContacts(String name){
        JSONArray contacts = new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + ChatContract.ContactEntry.TABLE_NAME + " WHERE " + ChatContract.ContactEntry.COLUMN_NAME + " LIKE '" + name + "%'";
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do{
                try{
                    JSONObject contact = new JSONObject();

                    contact.put("name",cursor.getString(0));
                    contact.put("public_key",cursor.getString(1));
                    contact.put("profile_in_string",cursor.getString(2));

                    contacts.put(contact);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return "error";
                }

            }while (cursor.moveToNext());
            cursor.close();
            return contacts.toString();
        }
        else{
            cursor.close();

            return contacts.toString();
        }


    }

    public String getAllConversations(){
        JSONArray conversations = new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + ConversationEntry.TABLE_NAME + " ORDER BY " + ConversationEntry.COLUMN_LAST_MESSAGE_TIMESTAMP + " DESC";
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do{
                try{
                    JSONObject conversation = new JSONObject();


                    conversation.put("name",cursor.getString(0));
                    conversation.put("public_key",cursor.getString(1));
                    conversation.put("profile_in_string",cursor.getString(2));
                    conversation.put("lastMessage",cursor.getString(3));
                    conversation.put("lastMessageTimestamp",cursor.getString(4));
                    conversation.put("read",cursor.getString(5));

                    conversations.put(conversation);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return "error";
                }

            }while (cursor.moveToNext());
            cursor.close();
            return conversations.toString();
        }
        else{
            cursor.close();

            return conversations.toString();
        }


    }


    // public List<Conversation> getAllConversation(){
    //     Log.d(TAG, "getAllConversation: Called");
    //     List<Conversation> conversationList = new ArrayList<>();

    //     String query = "SELECT * FROM " + ConversationEntry.TABLE_NAME + " ORDER BY " + ConversationEntry.COLUMN_LAST_MESSAGE_TIMESTAMP + " DESC";

    //     SQLiteDatabase db = this.getWritableDatabase();

    //     Cursor cursor = db.rawQuery(query,null);

    //     if (cursor.moveToFirst()){
    //         do{
    //             Log.d(TAG, "checkIfConversationExists: " + cursor.getString(0));
    //             Log.d(TAG, "checkIfConversationExists: " + cursor.getString(1));
    //             Log.d(TAG, "checkIfConversationExists: " + cursor.getString(2));
    //             Log.d(TAG, "checkIfConversationExists: " + cursor.getString(3));
    //             Log.d(TAG, "checkIfConversationExists: " + cursor.getString(4));
    //             Log.d(TAG, "checkIfConversationExists: " + cursor.getString(5));

    //             Conversation conversation = new Conversation(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));

    //             conversationList.add(conversation);
    //         }
    //         while (cursor.moveToNext());
    //     }

    //     cursor.close();
    //     return conversationList;
    // }

    public void updateConversation(String public_key,String lastMessage,String lastMessageTimeStamp,String read){
        Log.d(TAG, "updateConversation: Called");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ConversationEntry.COLUMN_LAST_MESSAGE,lastMessage);
        contentValues.put(ConversationEntry.COLUMN_LAST_MESSAGE_TIMESTAMP,lastMessageTimeStamp);
        contentValues.put(ConversationEntry.COLUMN_READ,read);

        db.update(ConversationEntry.TABLE_NAME,contentValues,ConversationEntry.COLUMN_PUBLIC_KEY + "=?",new String[]{public_key});

    }

    public void setConversationOpened(String public_key,String read){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConversationEntry.COLUMN_READ,read);

        db.update(ConversationEntry.TABLE_NAME,contentValues,ConversationEntry.COLUMN_PUBLIC_KEY + "=?",new String[]{public_key});
    }


    public Message checkIfMessageExists(String id){
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(MessageEntry.TABLE_NAME,new String[] {MessageEntry.COLUMN_NAME, MessageEntry.COLUMN_TIMESTAMP, MessageEntry.COLUMN_IS_IMAGE, MessageEntry.COLUMN_MESSAGE, MessageEntry.COLUMN_FROM_PUBLIC_KEY, MessageEntry.COLUMN_TO_PUBLIC_KEY},MessageEntry.COLUMN_FROM_PUBLIC_KEY + "=?",new String[]{id},null, null, null, null);

        if (cursor != null && cursor.moveToFirst()){
            cursor.moveToFirst();

            Message message = new Message(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
            cursor.close();
            return message;
        }

        return null;
    }


    public void addMessage(Message message, String selfPubKey){
        Log.d(TAG, "addMessage: Called");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(MessageEntry.COLUMN_TIMESTAMP,message.getTimestamp());

        contentValues.put(MessageEntry.COLUMN_IS_IMAGE,message.getImage());
        contentValues.put(MessageEntry.COLUMN_MESSAGE,message.getMessage());
        contentValues.put(MessageEntry.COLUMN_FROM_PUBLIC_KEY,message.getFromPublicKey());
        contentValues.put(MessageEntry.COLUMN_TO_PUBLIC_KEY,message.getToPublicKey());

        Conversation conversation = checkIfConversationExists(message.getFromPublicKey());
        message_conversation("none",message.getFromPublicKey(),"default_image",message.getMessage(),message.getTimestamp(),"false");

        Log.d(TAG, "addMessage: " + selfPubKey.equals(message.getFromPublicKey()));
        if(!selfPubKey.equals(message.getFromPublicKey())){
            if(conversation == null){
                Log.d(TAG, "addMessage: conversation null" );
                contentValues.put(MessageEntry.COLUMN_NAME,message.getFromPublicKey());
                Conversation conversation1 = new Conversation("Unknown",message.getFromPublicKey(),"default_image",message.getMessage(),message.getTimestamp(),"false");
                addConversation(conversation1);
            }
            else{
                Log.d(TAG, "addMessage: conversation not null");
                contentValues.put(MessageEntry.COLUMN_NAME,"none");
            }


        }
        else{
            contentValues.put(MessageEntry.COLUMN_NAME,"none");
        }



        db.insert(MessageEntry.TABLE_NAME,null,contentValues);

        db.close();
    }

    public String getAllMessages(String conversationID){
        Log.d(TAG, "getAllMessages: Called");
        ArrayList<Message> messageList = new ArrayList<>();

        String query = "SELECT * FROM " + MessageEntry.TABLE_NAME + " WHERE " + MessageEntry.COLUMN_FROM_PUBLIC_KEY + "= '" + conversationID + "' OR " + MessageEntry.COLUMN_TO_PUBLIC_KEY + "= '" + conversationID + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query,null);
        int index = 0;
        JSONArray messages = new JSONArray();
        if (cursor.moveToFirst()){
            do{
                try{
                    JSONObject message = new JSONObject();
                    message.put("name",cursor.getString(0));
                    message.put("timestamp",cursor.getString(1));

                    message.put("is_image",cursor.getString(2));
                    message.put("text",cursor.getString(3));
                    message.put("from",cursor.getString(4));
                    message.put("to",cursor.getString(5));

                    messages.put(index,message);
                    index++;
                }
                catch (Exception e){
                    e.printStackTrace();
                }



            }
            while (cursor.moveToNext());
        }

        cursor.close();


        return messages.toString();
    }


    public void message_conversation(String name, String publicKey, String profileInString, String lastMessage, String lastMessageTimestamp, String read){
        Conversation conversation = new Conversation(name, publicKey, profileInString, lastMessage, lastMessageTimestamp, read);
        Conversation con = checkIfConversationExists(publicKey);
        if (con != null){
            updateConversation(publicKey,lastMessage,lastMessageTimestamp,read);
        }
        else {
            addConversation(conversation);
        }
    }

    public boolean addContact(Contact contact){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            Log.d(TAG, "addContact: " + contact.toString());
            ContentValues contentValues1 = new ContentValues();
            ContentValues contentValues2 = new ContentValues();

            contentValues1.put(ContactEntry.COLUMN_NAME,contact.getName());
            contentValues1.put(ContactEntry.COLUMN_PUBLIC_KEY,contact.getPublicKey());
            contentValues1.put(ContactEntry.COLUMN_PROFILE_IN_STRING, contact.getProfileInString());

            contentValues2.put(ConversationEntry.COLUMN_NAME,contact.getName());
            contentValues2.put(ConversationEntry.COLUMN_PUBLIC_KEY,contact.getPublicKey());
            contentValues2.put(ConversationEntry.COLUMN_PROFILE_IN_STRING, contact.getProfileInString());
            contentValues2.put(ConversationEntry.COLUMN_LAST_MESSAGE,"");
            contentValues2.put(ConversationEntry.COLUMN_LAST_MESSAGE_TIMESTAMP,"0");
            contentValues2.put(ConversationEntry.COLUMN_READ, "true");

            String checkExists = "SELECT count(*) FROM " + ContactEntry.TABLE_NAME + " WHERE " + ContactEntry.COLUMN_PUBLIC_KEY + " ='" + contact.getPublicKey() + "'";


            db.insert(ContactEntry.TABLE_NAME,null,contentValues1);
            db.insert(ConversationEntry.TABLE_NAME,null,contentValues2);

            db.close();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }



//    public List<Contact> getAllContact(){
//        Log.d(TAG, "getAllContact: Called");
//        List<Contact> contactList = new ArrayList<>();
//
//        String query = "SELECT * FROM " + ContactEntry.TABLE_NAME + " ORDER BY " + ContactEntry.COLUMN_NAME + " ASC";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor cursor = db.rawQuery(query,null);
//
//        if (cursor.moveToFirst()){
//            do{
//                Log.d(TAG, "checkIfContactExists: " + cursor.getString(0));
//                Log.d(TAG, "checkIfContactExists: " + cursor.getString(1));
//                Log.d(TAG, "checkIfContactExists: " + cursor.getString(2));
//
//
//                Contact contact = new Contact(cursor.getString(0),cursor.getString(1),cursor.getString(2));
//
//                contactList.add(contact);
//            }
//            while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return contactList;
//    }

    public Contact getContact(String publicKey){
        Log.d(TAG, "getContact: Called");
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + ContactEntry.TABLE_NAME + " WHERE " + ContactEntry.COLUMN_PUBLIC_KEY + "= '" + publicKey + "'";
        Cursor cursor = db.rawQuery(query,null);
        Contact contact = new Contact(cursor.getString(0),cursor.getString(1),cursor.getString(2));
        cursor.close();
        return contact;
    }


    public void dropTables(){
        Log.d(TAG, "dropTables: Called");
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + ConversationEntry.TABLE_NAME);
        db.execSQL("DELETE FROM " + MessageEntry.TABLE_NAME);
        db.execSQL("DELETE FROM " + ContactEntry.TABLE_NAME);

        //db.execSQL("DELETE FROM " + AccountEntry.TABLE_NAME);
    }

}
