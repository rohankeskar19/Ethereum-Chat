import React, { Component } from "react";
import {
  View,
  Text,
  StyleSheet,
  Image,
  TouchableNativeFeedback,
  BackHandler,
  NativeModules
} from "react-native";

const EthereumChatAccountModule = NativeModules.EthereumChatAccountModule;

export class ChatList extends Component {
  openSettings = () => {
    this.props.navigation.navigate("Settings");
  };

  handleBackButtonClick = () => {
    console.log("object");
    EthereumChatAccountModule.checkAccountCreated(
      err => {
        console.log("not created");
        this.props.navigation.navigate("CreateAccount");
        return true;
      },
      created => {
        console.log("created");
        return null;
      }
    );
  };

  componentDidMount() {
    BackHandler.addEventListener(
      "hardwareBackPress",
      this.handleBackButtonClick
    );
  }

  componentWillUnmount() {
    BackHandler.removeEventListener(
      "hardwareBackPress",
      this.handleBackButtonClick
    );
  }

  render() {
    return (
      <View>
        <View style={styles.Header}>
          <Text style={styles.ChatsHeading}>Chats</Text>
          <TouchableNativeFeedback onPress={this.openSettings}>
            <Image
              source={require("../assets/icons/cog-outline.png")}
              style={styles.SettingsButton}
              onPress={this.openSettings}
            />
          </TouchableNativeFeedback>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  SettingsButton: {
    width: 30,
    height: 30
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
    padding: 15
  },
  ChatsHeading: {
    color: "#000",
    fontSize: 30
    // marginLeft: 20,
    // marginTop: 15
  }
});

export default ChatList;
