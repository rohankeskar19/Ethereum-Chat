import React, { Component } from "react";
import { View, Text, StyleSheet, Image, TouchableNativeFeedback, SafeAreaView } from "react-native";
import { TextInput, TouchableOpacity } from "react-native-gesture-handler";
import ImagePicker from "react-native-image-picker";



export class Settings extends Component {
  navigateToQrScan = () => {
    this.props.navigation.navigate("QrScan");
  };
  navigateToChatlist = () => {
    this.props.navigation.navigate("ChatList");
  };
  navigateToQrShow = () => {
    this.props.navigation.navigate("QrShow");
  };
  navigateToChangePassword = () => {
    this.props.navigation.navigate("ChangePassword");
  };
  handleSelectImageClick = () => {
    ImagePicker.launchImageLibrary(options, response => {});
  };
  onSubmitEdit = () => {
    // whatever you want to do on submit
  };
  
  render() {
    this.state = {
      inputValue:'Atharva Pingale'
    };
    
    return (
      <SafeAreaView style={styles.container}>
        <View style={styles.SettingsHeader}>
          <Text style={styles.SettingsText}>Settings</Text>
          <TouchableNativeFeedback onPress={this.navigateToChatlist}>
            <Text>Done</Text>
          </TouchableNativeFeedback>
        </View>
        <View style={{alignSelf:"center", minWidth:150, minHeight: 150}}>
          <Image 
            source={require("../assets/default_profile.jpg")} 
            style={styles.profileImage}>
          </Image>
          <View style={styles.newPic}>
          <TouchableOpacity
            style={styles.add}
            onPress={this.handleSelectImageClick}>
            <Text style={{color:'#fff', fontSize: 25}} >+</Text>
          </TouchableOpacity>
          </View>
        </View>
        <View style={styles.item}>
          <TextInput
          controlled={true}
          onChangeText={(text) => this.setState({ inputValue: text })}
          value={this.state.inputValue}
          onSubmitEditing={this.onSubmitEdit}
          style={styles.ButtonText}></TextInput>
        </View>
        <View style={styles.item}>
        <TouchableOpacity
          onPress={this.navigateToChangePassword}
        >
          <Text style={styles.ButtonText}>Change Password</Text>
        </TouchableOpacity>
        </View>
        <View style={styles.item}>
        <TouchableOpacity
          onPress={this.navigateToQrShow}
        >
          <Text style={styles.ButtonText}>Show QR Code</Text>
        </TouchableOpacity>
        </View>
        <View style={styles.item}>
        <TouchableOpacity
          onPress={this.navigateToQrScan}
        >
          <Text style={styles.ButtonText}>Scan QR Code</Text>
        </TouchableOpacity>
        </View>
        
        </SafeAreaView>
        
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
    width: 150,
    height: 150,
    marginTop: 30,
    marginBottom: 30,
    borderRadius: 120
  },
  ButtonText: {
    color: "#000",
    fontSize: 20
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
  },
  container: {
    flex: 1,
  },
  item: {
    backgroundColor: '#fff',
    padding: 20,
    marginHorizontal: 5,
    borderBottomWidth: 1,
    borderBottomColor:'#c2c5cc'
  },
});

export default Settings;
