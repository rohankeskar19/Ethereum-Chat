import React, { Component } from "react";
import { View, Image, StyleSheet, NativeModules } from "react-native";
import { StackActions, NavigationActions } from "react-navigation";

const EthereumChatAccountModule = NativeModules.EthereumChatAccountModule;

class SplashScreen extends Component {
  componentDidMount() {
    this.timeoutHandle = setTimeout(() => {
      EthereumChatAccountModule.checkAccountCreated(
        (err) => {
          this.props.navigation.dispatch({
            ...StackActions.reset({
              index: 0,
              actions: [
                NavigationActions.navigate({ routeName: "CreateAccount" }),
              ],
            }),
          });
        },
        (created) => {
          EthereumChatAccountModule.checkImageSet(
            (err) => {
              this.props.navigation.dispatch({
                ...StackActions.reset({
                  index: 0,
                  actions: [
                    NavigationActions.navigate({
                      routeName: "SelectProfileImage",
                    }),
                  ],
                }),
              });
            },
            (success) => {
              this.props.navigation.dispatch({
                ...StackActions.reset({
                  index: 0,
                  actions: [
                    NavigationActions.navigate({ routeName: "ChatList" }),
                  ],
                }),
              });
            }
          );
        }
      );
    }, 2000);
  }

  render() {
    return (
      <View style={styles.container}>
        <Image
          style={{ height: 200, width: 200 }}
          source={require("../assets/ethereum-icon.png")}
          resizeMode="contain"
          resizeMethod="resize"
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#000",
    justifyContent: "center",
    alignItems: "center",
  },
});

export default SplashScreen;
