import React, { Component } from "react";
import {
  View,
  Image,
  Text,
  TouchableOpacity,
  StyleSheet,
  NativeModules
} from "react-native";
import ImagePicker from "react-native-image-picker";
import { StackActions, NavigationActions } from "react-navigation";

const EthereumChatAccountModule = NativeModules.EthereumChatAccountModule;

const options = {
  storageOptions: {
    skipBackup: true,
    path: "images"
  }
};

class SelectProfileImage extends Component {
  componentDidMount() {
    EthereumChatAccountModule.checkImageSet(
      err => {},
      success => {
        this.props.navigation.dispatch({
          ...StackActions.reset({
            index: 0,
            actions: [NavigationActions.navigate({ routeName: "ChatList" })]
          })
        });
      }
    );
  }

  handleSelectImageClick = () => {
    ImagePicker.launchImageLibrary(options, response => {
      if (response.didCancel || response.error || response.customButton) {
      } else {
        EthereumChatAccountModule.saveImage(
          response.data.toString(),
          err => {},
          success => {
            this.props.navigation.dispatch({
              ...StackActions.reset({
                index: 0,
                actions: [NavigationActions.navigate({ routeName: "ChatList" })]
              })
            });
          }
        );
      }
    });
  };

  handleOpenCameraClick = () => {
    ImagePicker.launchCamera(options, response => {
      if (response.didCancel || response.error || response.customButton) {
      } else {
        EthereumChatAccountModule.saveImage(
          response.data.toString(),
          err => {},
          success => {
            this.props.navigation.dispatch({
              ...StackActions.reset({
                index: 0,
                actions: [NavigationActions.navigate({ routeName: "ChatList" })]
              })
            });
          }
        );
      }
    });
  };

  selectDefault = () => {
    const image = "default_image";
    EthereumChatAccountModule.saveImage(
      image,
      err => {},
      success => {
        this.props.navigation.dispatch({
          ...StackActions.reset({
            index: 0,
            actions: [NavigationActions.navigate({ routeName: "ChatList" })]
          })
        });
      }
    );
  };

  render() {
    return (
      <View>
        <View style={styles.Container}>
          <Text style={styles.Header}>
            But, before that let's setup a profile image!
          </Text>
          <Image
            source={require("../assets/default_profile.jpg")}
            style={styles.DefaultImage}
          />
          <View style={styles.ButtonsContainer}>
            <TouchableOpacity
              style={styles.CircularButtonLeft}
              onPress={this.handleSelectImageClick}
            >
              <Image
                source={require("../assets/icons/image-size-select-actual.png")}
              />
            </TouchableOpacity>
            <TouchableOpacity
              style={styles.CircularButtonRight}
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
  CircularButtonLeft: {
    backgroundColor: "#000",
    borderTopLeftRadius: 8,
    borderBottomLeftRadius: 8,
    width: "50%",
    height: 80,
    paddingTop: 15,
    paddingBottom: 15,
    paddingLeft: "17%",
    paddingRight: "25%"
  },
  CircularButtonRight: {
    backgroundColor: "#000",
    borderTopRightRadius: 8,
    borderBottomRightRadius: 8,
    width: "50%",
    height: 80,
    paddingTop: 15,
    paddingBottom: 15,
    paddingLeft: "17%",
    paddingRight: "25%"
  },
  Header: {
    color: "#000",
    fontSize: 20,
    textAlign: "center",
    width: "100%"
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
