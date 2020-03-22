import React, { Component } from "react";
import {
  View,
  Dimensions,
  NativeModules,
  Platform,
  StyleSheet,
  Clipboard
} from "react-native";
import styled from "styled-components";
import { IonIcons } from "@expo/vector-icons";

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
    keyPairCopied: false
  };

  componentDidMount() {
    EthereumChatAccountModule.checkAccountCreated(
      err => {},
      created => {
        this.props.navigation.navigate("Login");
      }
    );
  }

  handleCreateAccount = () => {
    const { name, password } = this.state;
    console.log(this.state);
    if (name.trim() == "" || password.trim() == "") {
      this.setState({
        errorOccured: true
      });
    } else {
      console.log("Creating account", this.state);
      this.setState(
        {
          creatingAccount: true
        },
        () => {
          EthereumChatAccountModule.createAccount(
            name.toString(),
            password.toString(),
            err => console.log(err),
            key => {
              this.setState({
                keyPair: key,
                creatingAccount: false
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
        keyPairCopied: true
      },
      () => {
        Clipboard.setString(keyPair);
      }
    );
  };

  navigateToChatList = () => {
    this.props.navigation.navigate("ChatList");
  };

  render() {
    const {
      errorOccured,
      keyPair,
      creatingAccount,
      keyPairCopied
    } = this.state;
    return (
      <View>
        <Container>
          {keyPair == "" ? (
            <View>
              <Header>Create your free account</Header>
              <FormContainer>
                <Input
                  placeholder="Enter your name"
                  onChangeText={name => this.setState({ name: name })}
                  style={errorOccured == true ? styles.errorOutline : null}
                />
                <Input
                  placeholder="Enter your password"
                  onChangeText={password =>
                    this.setState({ password: password })
                  }
                  secureTextEntry={true}
                  style={errorOccured == true ? styles.errorOutline : null}
                />
                <CreateAccountButton>
                  <ButtonText onPress={this.handleCreateAccount}>
                    Create account
                  </ButtonText>
                </CreateAccountButton>
              </FormContainer>
            </View>
          ) : (
            <Container
              style={{
                display: "flex",
                flexDirection: "column",
                alignContent: "space-between",
                padding: 25
              }}
            >
              <KeyHeader>
                This is your account key, Please store is securely somewhere,
                You won't be able to recover it later.
              </KeyHeader>
              <KeyText>{keyPair}</KeyText>
              <CopyClipboardButton onPress={this.copyToClipboard}>
                <CopyText>Copy</CopyText>
              </CopyClipboardButton>
              {keyPairCopied == true ? (
                <TakeMeToAppButton onPress={this.navigateToChatList}>
                  <TakeMeToAppText>Take me to the app</TakeMeToAppText>
                </TakeMeToAppButton>
              ) : (
                <View></View>
              )}
            </Container>
          )}
        </Container>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  errorOutline: {
    borderColor: "#ba2525",
    borderWidth: 1,
    borderRadius: 5,
    padding: 10
  }
});

const Container = styled.View`
  display: flex;
  flex: 1;
  flex-direction: column;
  width: ${width}px;
  height: ${height}px;
  position: absolute;
  padding-top: 30px;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
`;

const FormContainer = styled.View`
  width: 100%;
  height: 70%;
  padding: 20px;
`;

const Header = styled.Text`
  color: #000;
  font-size: 40px;
  padding: 10px;
  font-weight: 400;
  margin-left: 10px;
`;

const Input = styled.TextInput`
  width: 100%;
  height: 70px;
  color: #383d3a;
  font-size: 20px;
  padding: 10px;

  margin-top: 10px;
  margin-bottom: 15px;
`;

const CreateAccountButton = styled.TouchableOpacity`
  width: 100%;
  height: 55px;
  margin-top: 30px;
  background: #000;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 5px;
  shadow-color: #000;
  shadow-opacity: 0.4;
  shadow-offset: 0px 20px;
`;

const ButtonText = styled.Text`
  color: #fff;
  font-size: 20px;
`;

const KeyHeader = styled.Text`
  color: #000;
  font-size: 20px;
`;

const KeyText = styled.Text`
  color: #000;
  font-size: 35px;
  margin-top: 80px;
  height: 30%;
`;

const CopyClipboardButton = styled.TouchableOpacity`
  width: 100%;
  height: 55px;
  margin-top: 30px;

  background: #000;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 5px;
  shadow-color: #000;
  shadow-opacity: 0.4;
  shadow-offset: 0px 20px;
`;

const CopyText = styled.Text`
  color: #fff;
  font-size: 20px;
`;

const TakeMeToAppButton = styled.TouchableOpacity`
  width: 100%;
  height: 55px;
  margin-top: 55px;
`;

const TakeMeToAppText = styled.Text`
  color: #000;
  font-size: 22px;
  text-align: center;
`;

export default CreateAccount;
