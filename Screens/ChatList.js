import React, { Component } from "react";
import {
  View,
  Text,
  StyleSheet,
  Image,
  TouchableOpacity,
  NativeModules,
  FlatList,
  DeviceEventEmitter
} from "react-native";
import { TextInput } from "react-native-gesture-handler";

const EthereumChatAccountModule = NativeModules.EthereumChatAccountModule;
const EthereumChatMessagingModule = NativeModules.EthereumChatMessagingModule;

export class ChatList extends Component {
  state = {
    contacts:[],
    conversation:null,
    chatlist:[],
    recentChat: [],
  };

  onNewMessage = (event) => {
    const message = JSON.parse(event.message);
    
    var chatlist =  this.state.contacts;
    for(var i = 0; i < chatlist.length; i++){
      if(chatlist[i].public_key == message.from){
        chatlist[i].lastMessage = message.text;
        chatlist[i].lastMessageTimestamp = message.time_stamp;
        this.setState({
          contacts : chatlist
        })
        console.log("Chatlist updated",chatlist)
      }
    }

  };

  componentDidMount() {
    DeviceEventEmitter.addListener('newMessage', this.onNewMessage);
    EthereumChatMessagingModule.subscribeMessages((newMessage) => {
    }, err => {
      console.log(err)
    });
    
    EthereumChatMessagingModule.getConversations(
      (err) => { },
      (chatlist) => {
        
        this.setState({
          contacts: JSON.parse(chatlist),
        });
      }
    );
  }

  Item = (item) => {
    var contact = {};
    var conversation = {};
    if (item) {
      contact = item.contact.item;
    }

    return (
      <TouchableOpacity
        style={styles.ContactItem}
        onPress={() => this.navigate(contact,conversation)}
      >
        <View>

        </View>
        {contact.profile_in_string == "default_image" ? (
          <Image
            source={require("../assets/default_profile.jpg")}
            style={styles.ContactImage}
          />
        ) : (
            <Image
              source={{
                uri: `data:image/gif;base64,${contact.profile_in_string}`,
              }}
              style={styles.ContactImage}
            />
          )}
        <Text style={styles.ContactName}>{contact.name}</Text>
        <Text style={styles.ConversationMessage}>{conversation.last_message}</Text>
        <Text style={styles.ConversationTime}>{conversation.last_message_timestamp}</Text>
        <Text style={styles.ConversationStatus}>{conversation.read}</Text>
      </TouchableOpacity>
    );
  };

  navigate = (contact,conversation) => {
    this.props.navigation.navigate("Chat", { contact: contact, conversation: conversation });
  };

  openSettings = () => {
    this.props.navigation.navigate("Settings");
  };

  searchUsers = (text) => {
    if (text.trim() == "") {
      EthereumChatMessagingModule.getConversations(
        (err) => { },
        (chatlist) => {

          this.setState({
            contacts: JSON.parse(chatlist),
          });
        }
      );
     
    } else {
      EthereumChatMessagingModule.getContacts(
        text,
        (err) => { },
        (contacts) => {

          this.setState({
            contacts: JSON.parse(contacts),
          });
        }
      );
    }
  };



  render() {
    const { contacts } = this.state;

    return (
      <View>
        <View style={styles.Header}>
          <Text style={styles.ChatsHeading}>Chats</Text>
          <TouchableOpacity onPress={this.openSettings}>
            <Image
              source={require("../assets/icons/cog-outline.png")}
              style={styles.SettingsButton}
              onPress={this.openSettings}
            />
          </TouchableOpacity>
        </View>
        <View style={styles.Container}>
          <TextInput
            placeholder="Search"
            style={styles.SearchBar}
            onChangeText={this.searchUsers}
          />
          <FlatList
            style={styles.ChatList}
            data={contacts}
            renderItem={(contact) => <this.Item contact={contact} />}
            keyExtractor={(contact, index) => index.toString()}
            
          />
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  SettingsButton: {
    width: 30,
    height: 30,
    // marginTop: 15,
    // marginRight: 20
  },
  Header: {
    display: "flex",
    flexDirection: "row",
    height: 75,
    justifyContent: "space-between",
    alignItems: "center",
    backgroundColor: "#ededed",
    padding: 15,
  },
  ChatsHeading: {
    color: "#000",
    fontSize: 30,
  },
  Container: {
    width: "100%",
    height: "100%",
  },
  SearchBar: {
    width: "100%",
    height: 60,
    backgroundColor: "#e0e0e0",

    paddingHorizontal: 20,
    fontSize: 15,
  },
  ContactItem: {
    width: "100%",
    height: 90,
    padding: 10,
    display: "flex",
    flexDirection: "row",
    alignContent: "center",
    alignItems: "center",
  },
  ContactImage: {
    width: 70,
    height: 70,
    borderRadius: 100,
  },
  ContactName: {
    color: "#000",
    marginLeft: 20,
    fontSize: 20,
  },
  ConversationMessage: {
    color: "#000",
    marginLeft: 20,
    fontSize: 20,
  },
  ConversationTime: {
    color: "#000",
    marginLeft: 20,
    fontSize: 15,
  },
  ConversationStatus: {
    color: "#000",
    marginLeft: 20,
    fontSize: 15,
  },
  ChatList: {
    display: "flex",
    flex: 1,
    width: "100%",
    height: "100%",
  },
});

export default ChatList;
