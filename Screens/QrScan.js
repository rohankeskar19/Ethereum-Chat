import React, { Component } from "react";
import { View, StyleSheet, Text, TouchableOpacity } from "react-native";
import QRCodeScanner from "react-native-qrcode-scanner";
import { RNCamera as Camera } from "react-native-camera";

export class QrScan extends Component {
  onSuccess = data => {
    console.log(data);
  };

  render() {
    return (
      <View>
        <QRCodeScanner
          onRead={this.onSuccess}
          cameraProps={{ flashMode: Camera.Constants.FlashMode.auto }}
          bottomContent={
            <TouchableOpacity style={styles.buttonTouchable}>
              <Text style={styles.buttonText}>OK. Got it!</Text>
            </TouchableOpacity>
          }
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  centerText: {
    flex: 1,
    fontSize: 18,
    padding: 32,
    color: "#777"
  },
  textBold: {
    fontWeight: "500",
    color: "#000"
  },
  buttonText: {
    fontSize: 21,
    color: "rgb(0,122,255)"
  },
  buttonTouchable: {
    padding: 16
  }
});

export default QrScan;
