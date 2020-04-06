import React, { Component } from "react";

import { View, Text, StyleSheet } from "react-native";

class MessageBubble extends Component {
  render() {
    const leftSpacer =
      this.props.direction === "left" ? null : <View style={{ width: 70 }} />;
    const rightSpacer =
      this.props.direction === "left" ? <View style={{ width: 70 }} /> : null;

    const bubbleStyles =
      this.props.direction === "left"
        ? [styles.messageBubble, styles.messageBubbleLeft]
        : [styles.messageBubble, styles.messageBubbleRight];

    const bubbleTextStyle =
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

const styles = StyleSheet.create({
  messageBubble: {
    alignItems: "flex-start",
    borderRadius: 15,
    marginTop: 8,
    marginRight: 10,
    marginLeft: 10,
    paddingHorizontal: 10,
    paddingVertical: 10,
    justifyContent: "flex-start",
    flexDirection: "row",
    flex: 1,
  },

  messageBubbleLeft: {
    backgroundColor: "grey",
  },

  messageBubbleTextLeft: {
    color: "black",
  },

  messageBubbleRight: {
    backgroundColor: "black",
  },

  messageBubbleTextRight: {
    color: "white",
  },
});

export default MessageBubble;
