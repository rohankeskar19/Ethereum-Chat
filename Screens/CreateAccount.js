import React, { Component } from "react";
import {
  View,
  Dimensions,
  NativeModules,
  StyleSheet,
  Clipboard,
  Animated,
  TouchableOpacity,
  Text,
  TextInput,
} from "react-native";

const width = Dimensions.get("window").width;
const height = Dimensions.get("window").height;

const EthereumChatAccountModule = NativeModules.EthereumChatAccountModule;

class CreateAccount extends Component {
  state = {
    name: "",
    password: "",
    keyPair: "",
    errorOccured: false,
    creatingAccount: false,
    keyPairCopied: false,
    progressXPos: new Animated.Value(0),
  };

  handleCreateAccount = () => {
    const { name, password } = this.state;
    console.log(this.state);
    if (name.trim() == "" || password.trim() == "") {
      this.setState({
        errorOccured: true,
      });
    } else {
      console.log("Creating account", this.state);
      Animated.loop(
        Animated.sequence([
          Animated.timing(this.state.progressXPos, {
            toValue: 241,
            duration: 400,
          }),
          Animated.timing(this.state.progressXPos, {
            toValue: 0,
            duration: 400,
          }),
        ])
      ).start();
      this.setState(
        {
          creatingAccount: true,
        },
        () => {
          EthereumChatAccountModule.createAccount(
            name.toString(),
            password.toString(),
            (err) => console.log(err),
            (key) => {
              this.setState({
                keyPair: key,
                creatingAccount: false,
              });
            }
          );
        }
      );
    }
  };

  copyToClipboard = () => {
    const { keyPair } = this.state;
    this.setState(
      {
        keyPairCopied: true,
      },
      () => {
        Clipboard.setString(keyPair);
      }
    );
  };

  navigateToSelectProfileImage = () => {
    this.props.navigation.navigate("SelectProfileImage");
  };

  render() {
    const {
      errorOccured,
      keyPair,
      creatingAccount,
      keyPairCopied,
    } = this.state;
    const animtedStyle = {
      transform: [{ translateX: this.state.progressXPos }],
    };

    return (
      <View style={styles.container}>
        {creatingAccount == true ? (
          <View style={styles.progressBar}>
            <Animated.View
              style={[styles.progress, animtedStyle]}
            ></Animated.View>
          </View>
        ) : (
          <View style={styles.Container}>
            {keyPair == "" ? (
              <View>
                <Text style={styles.Header}>Create your free account</Text>
                <View style={styles.FormContainer}>
                  <TextInput
                    placeholder="Enter your name"
                    onChangeText={(name) => this.setState({ name: name })}
                    style={[
                      errorOccured == true ? styles.errorOutline : null,
                      styles.Input,
                    ]}
                  />
                  <TextInput
                    placeholder="Enter your password"
                    onChangeText={(password) =>
                      this.setState({ password: password })
                    }
                    secureTextEntry={true}
                    style={[
                      errorOccured == true ? styles.errorOutline : null,
                      styles.Input,
                    ]}
                  />
                  <TouchableOpacity
                    style={styles.CreateAccountButton}
                    onPress={this.handleCreateAccount}
                  >
                    <Text style={styles.ButtonText}>Create account</Text>
                  </TouchableOpacity>
                </View>
              </View>
            ) : (
              <View
                style={[
                  {
                    display: "flex",
                    flexDirection: "column",
                    alignContent: "space-between",
                    padding: 25,
                  },
                  styles.Container,
                ]}
              >
                <Text style={styles.KeyHeader}>
                  This is your account key, Please store it securely somewhere,
                  You won't be able to recover it later.
                </Text>
                <Text style={styles.KeyText}>{keyPair}</Text>
                <TouchableOpacity
                  style={styles.CopyToClipboardButton}
                  onPress={this.copyToClipboard}
                >
                  <Text style={styles.ButtonText}>Copy</Text>
                </TouchableOpacity>
                {keyPairCopied == true ? (
                  <TouchableOpacity
                    onPress={this.navigateToSelectProfileImage}
                    style={styles.TakeMeToAppButton}
                  >
                    <Text style={styles.TakeMeToAppText}>
                      Take me to the app
                    </Text>
                  </TouchableOpacity>
                ) : (
                  <View></View>
                )}
              </View>
            )}
          </View>
        )}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  errorOutline: {
    borderColor: "#ba2525",
    borderWidth: 1,
    borderRadius: 5,
    padding: 10,
  },
  progressBar: {
    width: "60%",
    height: 5,
    backgroundColor: "#bababa",
    borderRadius: 5,
  },
  progress: {
    width: 5,
    height: 5,
    backgroundColor: "#000",
    borderRadius: 5,
  },

  container: {
    display: "flex",
    flex: 1,
    flexDirection: "column",
    alignContent: "center",
    justifyContent: "center",
    alignItems: "center",
  },
  Container: {
    display: "flex",
    flex: 1,
    flexDirection: "column",
    position: "absolute",
    paddingTop: 30,
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    width: width,
    height: height,
  },
  FormContainer: {
    width: "100%",
    height: "70%",
    padding: 20,
  },
  Header: {
    color: "#000",
    fontSize: 40,
    padding: 10,
    marginLeft: 10,
  },
  Input: {
    width: "100%",
    height: 50,
    color: "#383d3a",
    fontSize: 20,
    padding: 10,
    marginTop: 10,
    marginBottom: 15,
  },
  CreateAccountButton: {
    width: "100%",
    height: 55,
    marginTop: 30,
    backgroundColor: "#000",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 5,
    shadowColor: "#000",
    shadowOpacity: 0.4,
    textShadowOffset: {
      width: 0,
      height: 20,
    },
  },
  ButtonText: {
    color: "#fff",
    fontSize: 20,
  },
  TakeMeToAppButton: {
    width: "100%",
    height: 55,
    marginTop: 55,
  },
  TakeMeToAppText: {
    color: "#000",
    fontSize: 22,
    textAlign: "center",
  },
  KeyHeader: {
    color: "#000",
    fontSize: 20,
  },
  KeyText: {
    color: "#000",
    fontSize: 35,
    marginTop: 80,
    height: "30%",
  },
  CopyText: {
    color: "#fff",
    fontSize: 20,
  },
  CopyToClipboardButton: {
    width: "100%",
    height: 55,
    marginTop: 30,
    backgroundColor: "#000",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 5,
    shadowColor: "#000",
    shadowOpacity: 0.4,
    textShadowOffset: { width: 0, height: 20 },
  },
});

export default CreateAccount;
