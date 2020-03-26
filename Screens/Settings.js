import React, { Component } from "react";
import { View, Text, StyleSheet, Image, TouchableNativeFeedback,FlatList, SafeAreaView } from "react-native";
import { TextInput, TouchableOpacity } from "react-native-gesture-handler";
import ImagePicker from "react-native-image-picker";
import Constants from 'expo-constants';


const DATA = [
  {
    id: '1',
    title: 'Username',
    press:'a',
  },
  {
    id: '2',
    title: 'Change Password',
    press:'b',
  },
  {
    id: '3',
    title: 'Scan QR Code',
    press:'navigateToQrScan',
  },
  {
    id: '4',
    title: 'Show QR Code',
    press:'navigateToQrShow',
  },
];

function Item({ title, press }) {
  return (
    <View style={styles.item}>
      <TouchableOpacity>
      <Text style={styles.title}>{title}</Text>
      </TouchableOpacity>
    </View>
  );
}



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
  handleSelectImageClick = () => {
    ImagePicker.launchImageLibrary(options, response => {});
  };
  
  render() {
    return (
      <SafeAreaView style={styles.container}>
        <View style={styles.SettingsHeader}>
          <Text style={styles.SettingsText}>Settings</Text>
          <TouchableNativeFeedback onPress={this.navigateToChatlist}>
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
        
          <FlatList
            data={DATA}
            renderItem={({ item }) => <Item title={item.title} />}
            keyExtractor={item => item.id}
          />
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
  },
  container: {
    flex: 1,
    marginTop: Constants.statusBarHeight,
  },
  item: {
    backgroundColor: '#fff',
    padding: 20,
    marginHorizontal: 5,
    borderBottomWidth: 1,
    borderBottomColor:'#c2c5cc'
  },
  title: {
    fontSize: 20,
  },
});

export default Settings;
