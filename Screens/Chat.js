import React, { Component } from "react";
import { View } from "react-native";
import { Text, FlatList, StyleSheet, NativeModules, DeviceEventEmitter, Image } from "react-native";
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
    EthereumChatMessagingModule.markAsRead(contact.public_key)
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

    EthereumChatMessagingModule.postMessage(contact.name, message.text, contact.public_key)

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
    const { messages,contact } = this.state;
   
    return (
      <View style={styles.outer}>
        <View style={styles.Header}>
          <View style={styles.imgCol}>
            {contact.profile_in_string == "default_image" ? (
              <Image
                source={require("../assets/default_profile.jpg")}
                style={styles.ContactImage}
              />
            ) : (
              <Image
                source={{
                  uri: `data:image/gif;base64,${contact.profile_in_string}`,
                }}
                style={styles.ContactImage}
              />
            )}
          </View>
          <View style={styles.msgCol}>
            <Text style={styles.ContactName}>{contact.name}</Text>
          </View>
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
  imgCol:{
    flex:1,
    justifyContent:"flex-start",
  },
  ContactImage: {
    width: 50,
    height: 50,
    borderRadius: 100,
  },
  ContactName: {
    color: "#fff",
    marginLeft: 0,
    fontSize: 20,
  },
  msgCol:{
    flex:5,
    justifyContent:"flex-end",
  },
});
