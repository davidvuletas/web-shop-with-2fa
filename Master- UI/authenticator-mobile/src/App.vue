<template>
  <root>
    <app-navigation></app-navigation>
  </root>
</template>

<script>
import {
  createAppContainer,
  createStackNavigator,
  createDrawerNavigator
} from "vue-native-router";
import { Root } from "native-base";
import QRReader from "./screens/QRReader"
import PinCodeScreen from "./screens/PinCodeScreen"
import Home from "./screens/Home"
import Vue from "vue-native-core";

export const eventBus = new Vue();

const AppNavigation = createAppContainer(
  createStackNavigator({
    Home: { screen: Home },
    QRReader: { screen: QRReader},
    PinCodeScreen: {screen: PinCodeScreen}
  },
  {
      initialRouteName: "Home",
      headerMode: "none"
    }
    )
);

export default {
  components: { Root, AppNavigation }
};
</script>