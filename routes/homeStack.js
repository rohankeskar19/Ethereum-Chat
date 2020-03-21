import {createStackNavigator} from 'react-navigation-stack';
import {createAppContainer} from 'react-navigation';

import CreateAccount from '../screens/CreateAccount'; 
import ChatList from '../screens/ChatList';
import Chat from '../screens/Chat';
import QrScan from '../screens/QrScan';
import QrShow from '../screens/QrShow';

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
    }
}

const HomeStack = createStackNavigator(screens);

export default createAppContainer(HomeStack); 

