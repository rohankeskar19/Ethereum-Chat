import React, { Component } from "react";
import { View, StyleSheet, Text } from "react-native";
import QRCode from "react-native-qrcode-svg";

export class QrShow extends Component {
  state = {
    accountData: {}
  };

  componentDidMount() {
    const accountData = this.props.navigation.getParam(
      "accountData",
      "no data found"
    );
    this.setState({
      accountData
    });
  }

  render() {
    const { accountData } = this.state;
    return (
      <View style={styles.qr}>
        <QRCode
          value={JSON.stringify(accountData)}
          size={250}
          color="black"
          backgroundColor="white"
        />
        <Text style={{ marginTop: 30, fontSize: 25 }}>Scan this QR code</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  qr: {
    alignContent: "center",
    alignItems: "center",
    marginTop: 120
  }
});

export default QrShow;
