import React, { Component } from "react";
import { View } from "react-native";
import {
  Text,
  StyleSheet,
  ScrollView,
  KeyboardAvoidingView,
  TextInput,
  TouchableHighlight,
  Keyboard
} from "react-native";
import KeyboardSpacer from "react-native-keyboard-spacer";
import AutogrowInput from "react-native-autogrow-input";
import { white } from "color-name";

function getRandomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

export default class Chat extends Component {
  constructor(props) {
    super(props);

    var messages = [];

    this.state = {
      messages: messages,
      inputBarText: ""
    };
  }

  static navigationOptions = {
    title: "Chat"
  };

  componentWillMount() {
    this.keyboardDidShowListener = Keyboard.addListener(
      "keyboardDidShow",
      this.keyboardDidShow.bind(this)
    );
    this.keyboardDidHideListener = Keyboard.addListener(
      "keyboardDidHide",
      this.keyboardDidHide.bind(this)
    );
  }

  componentWillUnmount() {
    this.keyboardDidShowListener.remove();
    this.keyboardDidHideListener.remove();
  }

  keyboardDidShow(e) {
    this.scrollView.scrollToEnd();
  }

  keyboardDidHide(e) {
    this.scrollView.scrollToEnd();
  }

  componentDidMount() {
    setTimeout(
      function() {
        this.scrollView.scrollToEnd();
      }.bind(this)
    );
  }

  componentDidUpdate() {
    setTimeout(
      function() {
        this.scrollView.scrollToEnd();
      }.bind(this)
    );
  }

  _sendMessage() {
    this.state.messages.push({
      direction: "right",
      text: this.state.inputBarText
    });

    this.setState({
      messages: this.state.messages,
      inputBarText: ""
    });
  }

  _onChangeInputBarText(text) {
    this.setState({
      inputBarText: text
    });
  }

  _onInputSizeChange() {
    setTimeout(
      function() {
        this.scrollView.scrollToEnd({ animated: false });
      }.bind(this)
    );
  }

  render() {
    var messages = [];

    this.state.messages.forEach(function(message, index) {
      messages.push(
        <MessageBubble
          key={index}
          direction={message.direction}
          text={message.text}
        />
      );
    });

    return (
      <View style={styles.outer}>
        <View style={styles.Header}>
          <Text style={{ color: "white" }}>Photo & Name</Text>
        </View>
        <ScrollView
          ref={ref => {
            this.scrollView = ref;
          }}
          style={styles.messages}
        >
          {messages}
        </ScrollView>
        <InputBar
          onSendPressed={() => this._sendMessage()}
          onSizeChange={() => this._onInputSizeChange()}
          onChangeText={text => this._onChangeInputBarText(text)}
          text={this.state.inputBarText}
        />
        <KeyboardSpacer />
      </View>
    );
  }
}

class MessageBubble extends Component {
  render() {
    var leftSpacer =
      this.props.direction === "left" ? null : <View style={{ width: 70 }} />;
    var rightSpacer =
      this.props.direction === "left" ? <View style={{ width: 70 }} /> : null;

    var bubbleStyles =
      this.props.direction === "left"
        ? [styles.messageBubble, styles.messageBubbleLeft]
        : [styles.messageBubble, styles.messageBubbleRight];

    var bubbleTextStyle =
      this.props.direction === "left"
        ? styles.messageBubbleTextLeft
        : styles.messageBubbleTextRight;

    return (
      <View style={{ justifyContent: "space-between", flexDirection: "row" }}>
        {leftSpacer}
        <View style={bubbleStyles}>
          <Text style={bubbleTextStyle}>{this.props.text}</Text>
        </View>
        {rightSpacer}
      </View>
    );
  }
}

class InputBar extends Component {
  componentWillReceiveProps(nextProps) {
    if (nextProps.text === "") {
      this.autogrowInput.resetInputText();
    }
  }

  render() {
    return (
      <View style={styles.inputBar}>
        <AutogrowInput
          style={styles.textBox}
          ref={ref => {
            this.autogrowInput = ref;
          }}
          multiline={true}
          defaultHeight={30}
          onChangeText={text => this.props.onChangeText(text)}
          onContentSizeChange={this.props.onSizeChange}
          value={this.props.text}
        />
        <TouchableHighlight
          style={styles.sendButton}
          onPress={() => this.props.onSendPressed()}
        >
          <Text style={{ color: "white" }}>Send</Text>
        </TouchableHighlight>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  //ChatView

  outer: {
    flex: 1,
    flexDirection: "column",
    justifyContent: "space-between",
    backgroundColor: "white"
  },

  Header: {
    display: "flex",
    flexDirection: "row",
    height: 75,
    color: "white",
    justifyContent: "space-between",
    alignItems: "center",
    backgroundColor: "black",
    padding: 15
  },
  messages: {
    flex: 1
  },

  //InputBar

  inputBar: {
    flexDirection: "row",
    justifyContent: "space-between",
    paddingHorizontal: 5,
    paddingVertical: 3
  },

  textBox: {
    borderRadius: 15,
    borderWidth: 1,
    borderColor: "gray",
    flex: 1,
    fontSize: 16,
    paddingHorizontal: 10
  },

  sendButton: {
    justifyContent: "center",
    alignItems: "center",
    paddingLeft: 15,
    marginLeft: 5,
    paddingRight: 15,
    borderRadius: 15,
    backgroundColor: "green"
  },

  //MessageBubble

  messageBubble: {
    alignItems: "flex-start",
    borderRadius: 15,
    marginTop: 8,
    marginRight: 10,
    marginLeft: 10,
    maxWidth: 150,
    paddingHorizontal: 10,
    paddingVertical: 5,
    justifyContent: "flex-start",
    flexDirection: "row",
    flex: 1
  },

  messageBubbleLeft: {
    backgroundColor: "grey"
  },

  messageBubbleTextLeft: {
    color: "black"
  },

  messageBubbleRight: {
    backgroundColor: "black"
  },

  messageBubbleTextRight: {
    color: "white"
  }
});
