import React, { Component } from "react";
import { View } from "react-native";
import { Text, FlatList, StyleSheet, NativeModules, DeviceEventEmitter } from "react-native";
import InputBar from "../components/InputBar";
import MessageBubble from "../components/MessageBubble";

const EthereumChatMessagingModule = NativeModules.EthereumChatMessagingModule

export default class Chat extends Component {
  state = {
    messages: [],
    inputBarText: "",
    contact: {}
  };

  onNewMessage = (event) => {
    console.log("event123",JSON.parse(event.message))
    this.setState({
      messages: [...this.state.messages,JSON.parse(event.message)]
    }) 
  };

  componentDidMount() {
    DeviceEventEmitter.addListener('newMessage', this.onNewMessage);

    const contact = this.props.navigation.getParam("contact", "null");
    this.setState({
      contact
    },() => {
      
      EthereumChatMessagingModule.getMessages(contact.public_key,messages => {
        
        this.setState({
          messages: JSON.parse(messages)
        })
      },err => {
        console.log(err)
      });

    })
    this.refs.FlatList.scrollToEnd({ animated: true });


  }


  sendMessage() {
    const message = {
      direction: "right",
      text: this.state.inputBarText,
    };

    const { contact } = this.state;

    EthereumChatMessagingModule.postMessage(message.text, contact.public_key)

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
    var message = messageItem.message.item;
    
   
    if(message.from == this.state.contact.public_key){
      message.direction = "left";
    }
    else{
      message.direction = "right";
    }

    return <MessageBubble direction={message.direction} text={message.text} />;
  };

  render() {
    const { messages } = this.state;
   
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
