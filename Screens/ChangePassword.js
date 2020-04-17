import React, { Component } from "react";
import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  TextInput,
  NativeModules
} from "react-native";
import { StackActions, NavigationActions } from "react-navigation";

const EthereumChatAccountModule = NativeModules.EthereumChatAccountModule;

export class ChangePassword extends Component {

  state = {
    oldPassword: "",
    newPassword1: "",
    newPassword2: "",
    error: false,
    accountData: {}
  };
  
  componentDidMount() {
    EthereumChatAccountModule.getPassword(
      err => {},
      accountData => {
        const accountDataToShow = JSON.parse(accountData);
        console.log(accountDataToShow.password);
        this.setState({
          accountData: accountDataToShow
        });
      }
    );
  }
  
  onSubmitEdit = () => {
    const { accountData ,oldPassword, newPassword1, newPassword2} = this.state;

    console.log(accountData.password);
    if (oldPassword == this.state.accountData.password && newPassword1==newPassword2) {
      this.state.accountData.password=newPassword1;
      EthereumChatAccountModule.changePassword(
        JSON.stringify(accountData),
        err => {},
        success => {
          console.log("Changed Password");
          this.props.navigation.dispatch({
            ...StackActions.reset({
              index: 0,
              actions: [NavigationActions.navigate({ routeName: "Settings" })]
            })
          });
        }
      );
      console.log(this.state.accountData.password);
    }
    else{
      this.errorView
      this.setState({error: true})
      console.log("ERROR")
    }
  };


  render() {
    return (
      <View>
        <View style={styles.ChangePasswordHeader}>
          <Text style={styles.ChangePasswordText}>Change Password</Text>
        </View>
        <View style={styles.Container}>
          <View style={styles.item}>
            <TextInput
              placeholder="Enter Current Password"
              onChangeText={(oldPassword) => this.setState({ oldPassword: oldPassword })}
              style={[
                this.state.error == true ? styles.errorOutline : null,
                styles.ButtonText,
              ]}
              secureTextEntry={true}
              
            ></TextInput>
          </View>
          <View style={styles.item}>
            <TextInput
              placeholder="Enter New Password"
              onChangeText={(newPassword1) => this.setState({ newPassword1: newPassword1 })}
              style={[
                this.state.error == true ? styles.errorOutline : null,
                styles.ButtonText,
              ]}
              secureTextEntry={true}
            ></TextInput>
          </View>
          <View style={styles.item}>
            <TextInput
              placeholder="Confirm Password"
              onChangeText={(newPassword2) => this.setState({ newPassword2: newPassword2 })}
              style={[
                this.state.error == true ? styles.errorOutline : null,
                styles.ButtonText,
              ]}
              secureTextEntry={true}
            ></TextInput>
          </View>
          <View>
 
          {
              // Pass any View or Component inside the curly bracket.
              // Here the ? Question Mark represent the ternary operator.
    
            this.state.error ? <Text style= {{ fontSize: 25, color: "#000", textAlign: 'center' }}> ERROR</Text> : null
          }
          </View>
          <View>
            <TouchableOpacity style={styles.DoneButton}
            onPress={this.onSubmitEdit}>
              <Text style={{ color: "#fff", fontSize: 25 }}>Done</Text>
            </TouchableOpacity>
          </View>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  errorOutline: {
    borderColor: "#ba2525",
    borderWidth: 1,
    borderRadius: 5,
    padding: 10,
  },
  ChangePasswordHeader: {
    display: "flex",
    flexDirection: "row",
    height: 75,
    justifyContent: "space-between",
    alignItems: "center",
    backgroundColor: "#ededed",
    padding: 15
  },
  ChangePasswordText: {
    color: "#000",
    fontSize: 30
  },
  item: {
    backgroundColor: "#fff",
    padding: 15,
    marginHorizontal: 5,
    borderBottomWidth: 1,
    borderBottomColor: "#c2c5cc"
  },
  ButtonText: {
    color: "#000",
    fontSize: 15
  },
  DoneButton: {
    width: "50%",
    height: 55,
    marginTop: 50,
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
  Container: {
    width: "100%",
    height: "100%"
  }
});

export default ChangePassword;
