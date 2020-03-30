import React, { Component } from "react";
import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  TextInput
} from "react-native";

export class ChangePassword extends Component {
  render() {
    return (
      <View>
        <View style={styles.ChangePasswordHeader}>
          <Text style={styles.ChangePasswordText}>Change Password</Text>
        </View>
        <View style={styles.item}>
          <TextInput
            placeholder="Enter Current Password"
            style={styles.ButtonText}
          ></TextInput>
        </View>
        <View style={styles.item}>
          <TextInput
            placeholder="Enter New Password"
            style={styles.ButtonText}
          ></TextInput>
        </View>
        <View style={styles.item}>
          <TextInput
            placeholder="Confirm Password"
            style={styles.ButtonText}
          ></TextInput>
        </View>
        <View>
          <TouchableOpacity style={styles.DoneButton}>
            <Text style={{ color: "#fff", fontSize: 25 }}>Done</Text>
          </TouchableOpacity>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  ChangePasswordHeader: {
    display: "flex",
    flexDirection: "row",
    height: 75,
    justifyContent: "space-between",
    alignItems: "center",
    backgroundColor: "#ededed",
    padding: 15
  },
  ChangePasswordText: {
    color: "#000",
    fontSize: 30
  },
  item: {
    backgroundColor: "#fff",
    padding: 15,
    marginHorizontal: 5,
    borderBottomWidth: 1,
    borderBottomColor: "#c2c5cc"
  },
  ButtonText: {
    color: "#000",
    fontSize: 15
  },
  DoneButton: {
    width: "50%",
    height: 55,
    marginTop: 50,
    alignSelf: "center",
    backgroundColor: "#000",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 5,
    shadowColor: "#000",
    shadowOpacity: 0.4,
    textShadowOffset: {
      width: 0,
      height: 20
    }
  }
});

export default ChangePassword;
