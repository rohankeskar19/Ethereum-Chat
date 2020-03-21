import React, { Component } from "react";
import { StyleSheet, Text, View } from "react-native";
import { NativeModules } from "react-native";
import Navigator from './routes/homeStack';

var EthereumChatAccountModule = NativeModules.EthereumChatAccountModule;

export default class App extends Component {
  state = {
    keyPair: ""
  };

  componentDidMount() {
    EthereumChatAccountModule.createAccount(
      err => console.log(err),
      keyPair => {
        this.setState({
          keyPair
        });
        console.log(keyPair);
      }
    );
  }

  render() {
    const { keyPair } = this.state;
    return (
      <View style={styles.container}>
        <Text>Your account: {keyPair}</Text>
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
