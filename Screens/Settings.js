import React, { Component } from "react";
import { View, Text, StyleSheet } from "react-native";

export class Settings extends Component {
  render() {
    return (
      <View>
        <View style={styles.SettingsHeader}>
          <Text style={styles.SettingsText}>Settings</Text>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  SettingsHeader: {
    display: "flex",
    flexDirection: "row",
    height: 75,
    justifyContent: "space-between",
    alignItems: "center",
    backgroundColor: "#ededed",
    padding: 15
  },
  SettingsText: {
    color: "#000",
    fontSize: 30
  }
});

export default Settings;
