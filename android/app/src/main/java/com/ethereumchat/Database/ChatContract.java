package com.ethereumchat.Database;

import android.provider.BaseColumns;

public class ChatContract implements BaseColumns {

    private ChatContract() {}

    public static final class MessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "messages_table";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_IS_IMAGE = "is_image";
        public static final String COLUMN_MESSAGE = "messsage";
        public static final String COLUMN_FROM_PUBLIC_KEY = "from_public_key";
        public static final String COLUMN_TO_PUBLIC_KEY = "to_public_key";
    }

    public static final class ConversationEntry implements BaseColumns {
        public static final String TABLE_NAME = "conversations_table";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PUBLIC_KEY = "public_key";
        public static final String COLUMN_PROFILE_IN_STRING = "profile_in_string";
        public static final String COLUMN_LAST_MESSAGE = "last_message";
        public static final String COLUMN_LAST_MESSAGE_TIMESTAMP = "last_message_timestamp";
        public static final String COLUMN_READ = "read";
    }

//    public static final class AccountEntry implements BaseColumns {
//        public static final String TABLE_NAME = "account_table";
//        public static final String COLUMN_NAME = "name";
//        public static final String COLUMN_PUBLIC_KEY = "public_key";
//        public static final String COLUMN_PRIVATE_KEY = "private_key";
//        public static final String COLUMN_PASSWORD = "password";
//        public static final String COLUMN_KEYPAIR = "key_pair";
//    }
}
