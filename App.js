import React, { Component } from "react";
import { StyleSheet, Text, View } from "react-native";
import { NativeModules } from "react-native";
const nodeConfig = require("./node_config.json");

var EthereumChatAccountModule = NativeModules.EthereumChatAccountModule;

export default class App extends Component {
  state = {
    account: {}
  };

  componentDidMount() {
    EthereumChatAccountModule.createAccount(
      err => console.log(err),
      account => {
        account = JSON.parse(account);
        const accountsArr = account.accounts;
        const newAccount = accountsArr[0];

        const accountObject = {
          address: newAccount.address,
          wallet: false,
          chat: true,
          type: "",
          storage: "",
          path: "",
          "public-key": newAccount.pubkey,
          name: "Rohan Keskar",
          color: "#fff"
        };
        const accountToSave = [accountObject];

        console.log(accountObject);
        EthereumChatAccountModule.saveAccountAndLogin(
          JSON.stringify(accountObject),
          "password",
          JSON.stringify(nodeConfig),
          "[]"
        );
      }
    );
  }

  render() {
    return (
      <View style={styles.container}>
        <Text>Ethereum Chat</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#F5FCFF"
  },
  welcome: {
    fontSize: 20,
    textAlign: "center",
    margin: 10
  },
  instructions: {
    textAlign: "center",
    color: "#333333",
    marginBottom: 5
  }
});
