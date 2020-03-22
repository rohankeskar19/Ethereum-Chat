import React, { Component } from "react";
import { View, Text } from "react-native";

export class Login extends Component {
  render() {
    return (
      <View>
        <View>
          <Text>You need to login before using the app</Text>
        </View>
      </View>
    );
  }
}

export default Login;
