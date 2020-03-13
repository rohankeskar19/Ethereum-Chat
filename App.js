import React, { Component } from "react";
import { StyleSheet, Text, View } from "react-native";
import { NativeModules } from "react-native";

var EthereumChatAccountModule = NativeModules.EthereumChatAccountModule;

const nodeConfig = {
  NetworkID: 1,
  DataDir: "/tmp/status-go-data",
  NodeKey: "123qwe123qwe123qwe123",
  Rendezvous: true,
  NoDiscovery: false,
  ListenAddr: "0.0.0.0:30303",
  AdvertiseAddr: "12.34.56.78",
  RegisterTopics: ["whispermail"]
};

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
        <Text>Hello, My name is:</Text>
        <Text>Rohan Keskar</Text>
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
