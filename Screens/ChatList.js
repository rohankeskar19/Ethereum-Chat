import React, { Component } from "react";
import {
  View,
  Text,
  StyleSheet,
  Image,
  TouchableOpacity,
  NativeModules,
  FlatList,
} from "react-native";
import { TextInput } from "react-native-gesture-handler";

const EthereumChatAccountModule = NativeModules.EthereumChatAccountModule;
const EthereumChatMessagingModule = NativeModules.EthereumChatMessagingModule;

function Item(item) {
  console.log(item);
  var contact = {}
  if(item){
    contact = item.contact.item;
  }
  return (
    <View style={styles.ContactItem}>
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
    </View>
  );
}

export class ChatList extends Component {
  state = {
    contacts: null,
    recentChat: [],
  };

  openSettings = () => {
    this.props.navigation.navigate("Settings");
  };

  searchUsers = (text) => {
    if (text.trim() == "") {
      this.setState({
        contacts: [],
      });
    } else {
      EthereumChatMessagingModule.getContacts(
        text,
        (err) => {},
        (contacts) => {
          console.log(contacts);
          this.setState({
            contacts: JSON.parse(contacts),
          });
        }
      );
    }
  };

  componentDidMount() {
    // this.props.navigation.navigate("Chat");
  }

  render() {
    const { contacts } = this.state;
    // console.log(contacts);
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
            renderItem={(contact) => <Item contact={contact} />}
            keyExtractor={(contact, index) => index.toString()}
          />
          {/* {contacts &&
            contacts.map(contact => (
              <View style={styles.ContactItem}>
                {contact.profile_in_string == "default_image" ? (
                  <Image
                    source={require("../assets/default_profile.jpg")}
                    style={styles.ContactImage}
                  />
                ) : (
                  <Image
                    source={{
                      uri: `data:image/gif;base64,${contact.profile_in_string}`
                    }}
                    style={styles.ContactImage}
                  />
                )}
                <Text style={styles.ContactName}>{contact.name}</Text>
              </View>
            ))} */}
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
  ChatList: {
    display: "flex",
    flex: 1,
    width: "100%",
    height: "100%",
  },
});

export default ChatList;
