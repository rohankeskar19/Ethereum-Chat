import React, { Component } from "react";
import AutogrowInput from "react-native-autogrow-input";
import { View, Text, TouchableHighlight, StyleSheet } from "react-native";
class InputBar extends Component {
  componentDidUpdate(prevPros, prevState, snap) {
    if (this.props.text == "") {
      this.autogrowInput.resetInputText();
    }
  }

  render() {
    return (
      <View style={styles.inputBar}>
        <AutogrowInput
          style={styles.textBox}
          ref={(ref) => {
            this.autogrowInput = ref;
          }}
          multiline={true}
          defaultHeight={40}
          onChangeText={(text) => this.props.onChangeText(text)}
          onContentSizeChange={this.props.onSizeChange}
          value={this.props.text}
        />
        <View>
          <TouchableHighlight
            style={styles.sendButton}
            onPress={() => this.props.onSendPressed()}
          >
            <Text style={{ color: "white" }}>Send</Text>
          </TouchableHighlight>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  sendButton: {
    height: 40,
    justifyContent: "center",
    alignItems: "center",
    paddingLeft: 15,
    marginLeft: 5,
    paddingRight: 15,
    borderRadius: 15,
    backgroundColor: "#000",
  },
  textBox: {
    overflow: "hidden",
    borderRadius: 15,
    borderWidth: 1,
    borderColor: "gray",
    flex: 1,
    fontSize: 16,
    paddingHorizontal: 10,
  },
  inputBar: {
    flexDirection: "row",
    justifyContent: "space-between",
    paddingHorizontal: 6,
    paddingVertical: 5,
  },
});

export default InputBar;
