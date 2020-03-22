import { createStackNavigator } from "react-navigation-stack";
import { createAppContainer } from "react-navigation";

import CreateAccount from "../Screens/CreateAccount";
import ChatList from "../Screens/ChatList";
import Chat from "../Screens/Chat";
import QrScan from "../Screens/QrScan";
import QrShow from "../Screens/QrShow";
import Login from "../Screens/Login";

const screens = {
  CreateAccount: {
    screen: CreateAccount
  },
  ChatList: {
    screen: ChatList
  },
  Chat: {
    screen: Chat
  },
  QrScan: {
    screen: QrScan
  },
  QrShow: {
    screen: QrShow
  },
  Login: {
    screen: Login
  }
};

const stackNavigatorConfig = {
  headerMode: "none"
};

const HomeStack = createStackNavigator(screens, stackNavigatorConfig);

export default createAppContainer(HomeStack);
