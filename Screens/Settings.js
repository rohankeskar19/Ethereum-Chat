import React, { Component } from "react";
import { View, Text, StyleSheet, Image, TouchableNativeFeedback } from "react-native";
import { TextInput, TouchableOpacity } from "react-native-gesture-handler";
import ImagePicker from "react-native-image-picker";

const options = {
  storageOptions: {
    skipBackup: true,
    path: "images"
  }
};

export class Settings extends Component {
  navigateToQrScan = () => {
    this.props.navigation.navigate("QrScan");
  };
  navigateToQrShow = () => {
    this.props.navigation.navigate("QrShow");
  };
  handleSelectImageClick = () => {
    ImagePicker.launchImageLibrary(options, response => {});
  };
  render() {
    return (
      <View>
        <View style={styles.SettingsHeader}>
          <Text style={styles.SettingsText}>Settings</Text>
          <TouchableNativeFeedback >
            <Text>Done</Text>
          </TouchableNativeFeedback>
        </View>
        <View style={{alignSelf:"center", minWidth:200, minHeight: 200}}>
          <Image 
            source={require("../assets/default_profile.jpg")} 
            style={styles.profileImage}>
          </Image>
          <View style={styles.newPic}>
          <TouchableOpacity
            style={styles.add}
            onPress={this.handleSelectImageClick}>
            <Text style={styles.ButtonText} >+</Text>
          </TouchableOpacity>
          </View>
        </View>
        <View style={{alignItems:"center"}}>
          <Text style={{ color: "#000", fontSize: 25}} >Atharva Pingale</Text>
          <TextInput placeholder="Enter New Password"
          onChangeText={password =>
            this.setState({ password: password })
          }
          secureTextEntry={true}/>
        </View>
        <TouchableOpacity
          style={styles.QRButton}
          onPress={this.navigateToQrShow}
        >
          <Text style={styles.ButtonText} >Show QR Code</Text>
        </TouchableOpacity>
        <TouchableOpacity
          style={styles.QRButton}
          onPress={this.navigateToQrScan}
        >
          <Text style={styles.ButtonText} >Scan QR Code</Text>
        </TouchableOpacity>
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
  },
  profileImage: {
    width: 200,
    height: 200,
    marginTop: 30,
    marginBottom: 30,
    borderRadius: 120
  },
  ButtonText: {
    color: "#fff",
    fontSize: 20
  },
  QRButton: {
    width: "50%",
    height: 55,
    marginTop: 30,
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
  },
  add:{
    borderRadius:30,
    backgroundColor: "#000",
    height: 50,
    width:50,
    justifyContent: "center",
    alignItems: "center"
  },
  newPic:{
    position: "absolute",
    bottom:10,
    right:10
  }
});

export default Settings;
