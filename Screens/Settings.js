import React, { Component } from "react";
import {
  View,
  Text,
  StyleSheet,
  Image,
  TouchableNativeFeedback,
  NativeModules,
  TextInput,
  TouchableOpacity
} from "react-native";
import ImagePicker from "react-native-image-picker";
import SafeAreaView from "react-native-safe-area-view";
import { StackActions, NavigationActions } from "react-navigation";

const EthereumChatAccountModule = NativeModules.EthereumChatAccountModule;

export class Settings extends Component {
  state = {
    madeChanges: false,
    accountData: {}
  };

  componentDidMount() {
    EthereumChatAccountModule.getAccountData(
      err => {},
      accountData => {
        const accountDataToShow = JSON.parse(accountData);
        console.log(accountDataToShow.name);
        this.setState({
          accountData: accountDataToShow
        });
      }
    );
  }

  navigateToQrScan = () => {
    this.props.navigation.navigate("QrScan");
  };
  navigateToChatlist = () => {
    this.props.navigation.navigate("ChatList");
  };
  navigateToQrShow = () => {
    const { madeChanges, accountData } = this.state;
    if (madeChanges) {
      EthereumChatAccountModule.getAccountData(
        err => {},
        accountData => {
          const accountDataToPass = JSON.parse(accountData);
          this.props.navigation.navigate("QrShow", {
            accountData: accountDataToPass
          });
        }
      );
    } else {
      this.props.navigation.navigate("QrShow", { accountData: accountData });
    }
  };
  navigateToChangePassword = () => {
    this.props.navigation.navigate("ChangePassword");
  };
  handleSelectImageClick = () => {
    ImagePicker.launchImageLibrary({}, response => {
      if (response.didCancel || response.error || response.customButton) {
      } else {
        this.setState(
          {
            madeChanges: true
          },
          () => {
            this.setState({
              accountData: {
                ...this.state.accountData,
                profile_image: response.data.toString()
              }
            });
          }
        );
      }
    });
  };
  onSubmitEdit = () => {
    const { accountData } = this.state;

    console.log(accountData);

    EthereumChatAccountModule.changeAccountData(
      JSON.stringify(accountData),
      err => {},
      success => {
        console.log("Changed accountData");
        this.props.navigation.dispatch({
          ...StackActions.reset({
            index: 0,
            actions: [NavigationActions.navigate({ routeName: "ChatList" })]
          })
        });
      }
    );
  };

  changeName = name => {
    if (name == this.state.accountData.name) {
    } else {
      this.setState({
        accountData: {
          ...this.state.accountData,
          name
        },
        madeChanges: true
      });
    }

    console.log(this.state.accountData.name);
  };

  render() {
    const { accountData, madeChanges } = this.state;

    return (
      <SafeAreaView style={styles.container}>
        <View style={styles.SettingsHeader}>
          <Text style={styles.SettingsText}>Settings</Text>
          <TouchableNativeFeedback
            onPress={madeChanges ? this.onSubmitEdit : null}
          >
            <Text
              style={[
                madeChanges ? { color: "#000" } : { color: "#bababa" },
                { fontSize: 20 }
              ]}
            >
              Done
            </Text>
          </TouchableNativeFeedback>
        </View>
        <View style={{ alignSelf: "center", minWidth: 150, minHeight: 150 }}>
          {accountData.profile_image == "default_image" ? (
            <Image
              source={require("../assets/default_profile.jpg")}
              style={styles.profileImage}
            />
          ) : (
            <Image
              source={{
                uri: `data:image/gif;base64,${accountData.profile_image}`
              }}
              style={styles.profileImage}
            ></Image>
          )}

          <View style={styles.newPic}>
            <TouchableOpacity
              style={styles.add}
              onPress={this.handleSelectImageClick}
            >
              <Text style={{ color: "#fff", fontSize: 25 }}>+</Text>
            </TouchableOpacity>
          </View>
        </View>
        <View style={styles.item}>
          <TextInput
            controlled={true}
            onChangeText={this.changeName}
            value={accountData.name}
            style={styles.ButtonText}
          ></TextInput>
        </View>
        <View style={styles.item}>
          <TouchableOpacity onPress={this.navigateToChangePassword}>
            <Text style={styles.ButtonText}>Change Password</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.item}>
          <TouchableOpacity onPress={this.navigateToQrShow}>
            <Text style={styles.ButtonText}>Show QR Code</Text>
          </TouchableOpacity>
        </View>
        <View style={styles.item}>
          <TouchableOpacity onPress={this.navigateToQrScan}>
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
    fontSize: 15
  },
  add: {
    borderRadius: 30,
    backgroundColor: "#000",
    height: 50,
    width: 50,
    justifyContent: "center",
    alignItems: "center"
  },
  newPic: {
    position: "absolute",
    bottom: 10,
    right: 10
  },
  container: {
    flex: 1
  },
  item: {
    backgroundColor: "#fff",
    padding: 15,
    marginHorizontal: 5,
    borderBottomWidth: 1,
    borderBottomColor: "#c2c5cc"
  }
});

export default Settings;
