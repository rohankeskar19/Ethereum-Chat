import React, { Component } from "react";
import { View, StyleSheet, Text} from "react-native";
import QRCode from 'react-native-qrcode-svg';

export class QrShow extends Component {
  render() {
    return (
      <View style={styles.qr}>
        <QRCode
          value="random string for testing"
          size={250}
          color="black"
          backgroundColor="white"
        />
        <Text style={{marginTop:30, fontSize: 25}}>Scan this QR code</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  qr:{
    alignContent:"center",
    alignItems:"center",
    marginTop:120
  }
});

export default QrShow;