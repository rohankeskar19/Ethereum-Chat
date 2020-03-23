import React, { Component } from "react";
import { View, Image, Text, TouchableOpacity, StyleSheet } from "react-native";
import ImagePicker from "react-native-image-picker";
const defaultImage = require("../assets/default_profile.jpg");

const options = {
  storageOptions: {
    skipBackup: true,
    path: "images"
  }
};

class SelectProfileImage extends Component {
  handleSelectImageClick = () => {
    ImagePicker.launchImageLibrary(options, response => {});
  };

  handleOpenCameraClick = () => {
    ImagePicker.launchCamera(options, response => {});
  };

  selectDefault = () => {
    console.log(defaultImage);
  };

  render() {
    return (
      <View>
        <View style={styles.Container}>
          <Text style={styles.Header}>
            But, before that let's setup an profile image!
          </Text>
          <Image
            source={require("../assets/default_profile.jpg")}
            style={styles.DefaultImage}
          />
          <View style={styles.ButtonsContainer}>
            <TouchableOpacity
              style={styles.CircularButton}
              onPress={this.handleSelectImageClick}
            >
              <Image
                source={require("../assets/icons/image-size-select-actual.png")}
              />
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.CircularButton}
              onPress={this.handleOpenCameraClick}
            >
              <Image source={require("../assets/icons/camera.png")} />
            </TouchableOpacity>
          </View>
          <TouchableOpacity
            style={styles.UseDefaultButton}
            onPress={this.selectDefault}
          >
            <Text style={{ color: "#000", fontSize: 15 }}>Use default</Text>
          </TouchableOpacity>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  Container: {
    width: "100%",
    height: "100%",
    display: "flex",

    flexDirection: "column",
    paddingTop: 20,
    alignContent: "center",
    justifyContent: "center",
    alignItems: "center"
  },
  CircularButton: {
    backgroundColor: "#000",
    borderRadius: 8,
    width: "50%",
    height: 80,
    paddingTop: 15,
    paddingBottom: 15,
    paddingLeft: "17%",
    paddingRight: "25%"
  },
  Header: {
    color: "#000",
    fontSize: 20
  },
  ButtonsContainer: {
    width: "80%",

    display: "flex",
    flexDirection: "row",

    padding: 20,
    alignItems: "center",
    justifyContent: "flex-start"
  },
  DefaultImage: {
    marginTop: 60,
    borderRadius: 120,
    marginBottom: 30
  },
  UseDefaultButton: {
    marginTop: 25
  }
});

export default SelectProfileImage;
