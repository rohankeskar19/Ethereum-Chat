import { createStackNavigator } from "react-navigation-stack";
import { createAppContainer } from "react-navigation";

import CreateAccount from "../Screens/CreateAccount";
import ChatList from "../Screens/ChatList";
import Chat from "../Screens/Chat";
import QrScan from "../Screens/QrScan";
import QrShow from "../Screens/QrShow";
import Login from "../Screens/Login";
import Settings from "../Screens/Settings";
import SelectProfileImage from "../Screens/SelectProfileImage";
import ChangePassword from "../Screens/ChangePassword";
import SplashScreen from "../Screens/SplashScreen";

const screens = {
  SplashScreen: {
    screen: SplashScreen
  },
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
  },
  Settings: {
    screen: Settings
  },
  SelectProfileImage: {
    screen: SelectProfileImage
  },
  ChangePassword: {
    screen: ChangePassword
  }
};

const stackNavigatorConfig = {
  headerMode: "none"
};

const HomeStack = createStackNavigator(screens, stackNavigatorConfig);

export default createAppContainer(HomeStack);
