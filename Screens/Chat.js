import React, { Component } from "react";
import { View } from "react-native";
import { Text, FlatList, StyleSheet, Keyboard } from "react-native";
import InputBar from "../components/InputBar";
import MessageBubble from "../components/MessageBubble";

export default class Chat extends Component {
  state = {
    messages: [],
    inputBarText: "",
  };

  componentDidMount() {
    this.refs.FlatList.scrollToEnd({ animated: true });
  }

  componentDidUpdate() {}

  sendMessage() {
    const message = {
      direction: "right",
      text: this.state.inputBarText,
    };

    this.setState({
      messages: [...this.state.messages, message],
      inputBarText: "",
    });
  }

  onChangeInputBarText(text) {
    this.setState({
      inputBarText: text,
    });
  }

  onInputSizeChange() {
    this.refs.FlatList.scrollToEnd({ animated: true });
  }

  MessageItem = (messageItem) => {
    const message = messageItem.message.item;
    return <MessageBubble direction={message.direction} text={message.text} />;
  };

  render() {
    const { messages } = this.state;
    console.log(messages);
    return (
      <View style={styles.outer}>
        <View style={styles.Header}>
          <Text style={{ color: "white" }}>Photo & Name</Text>
        </View>

        <FlatList
          data={messages}
          style={styles.messages}
          renderItem={(message) => <this.MessageItem message={message} />}
          keyExtractor={(message, index) => index.toString()}
          ref="FlatList"
          onContentSizeChange={() =>
            this.refs.FlatList.scrollToEnd({ animated: false })
          }
        />
        <InputBar
          onSendPressed={() => this.sendMessage()}
          onSizeChange={() => this.onInputSizeChange()}
          onChangeText={(text) => this.onChangeInputBarText(text)}
          text={this.state.inputBarText}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  outer: {
    flex: 1,
    flexDirection: "column",
    justifyContent: "space-between",
    backgroundColor: "white",
  },

  Header: {
    display: "flex",
    flexDirection: "row",
    height: 75,
    color: "white",
    justifyContent: "space-between",
    alignItems: "center",
    backgroundColor: "black",
    padding: 15,
  },
  messages: {
    flex: 1,
  },
});
