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
import Home from "./screens/Home"
import Vue from "vue-native-core";
import axios from "axios";

export const eventBus = new Vue();
export const api = axios.create({
        baseURL: "https://192.168.0.12:8081/api/"
      });
const AppNavigation = createAppContainer(
  createStackNavigator({
    Home: { screen: Home },
    QRReader: { screen: QRReader}
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