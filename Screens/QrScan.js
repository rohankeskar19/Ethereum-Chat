import React, { Component } from "react";
import {
  View,
  StyleSheet,
  Text,
  TouchableOpacity,
  NativeModules
} from "react-native";
import QRCodeScanner from "react-native-qrcode-scanner";
import { RNCamera as Camera } from "react-native-camera";

const EthereumChatAccountModule = NativeModules.EthereumChatAccountModule;

export class QrScan extends Component {
  onSuccess = e => {
    const data = JSON.parse(e.data);

    const name = data.name;
    const pubKey = data.public_key;
    const image = "default_image";

    if (!name || !pubKey || !image) {
    } else {
      EthereumChatAccountModule.saveAccount(
        name,
        pubKey,
        image,
        err => {},
        success => {
          console.log("Saved account");
          this.props.navigation.goBack(null);
        }
      );
    }
  };

  closeScanner = () => {
    this.props.navigation.pop(null);
  };

  render() {
    return (
      <View>
        <QRCodeScanner
          onRead={this.onSuccess}
          cameraProps={{ flashMode: Camera.Constants.FlashMode.auto }}
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
    color: "#000",

    width: "100%",
    textAlign: "center"
  },
  buttonTouchable: {
    width: "90%",
    height: 65,
    padding: 16,
    backgroundColor: "#fff",
    borderRadius: 5,
    marginLeft: 10,
    marginRight: 10
  }
});

export default QrScan;
