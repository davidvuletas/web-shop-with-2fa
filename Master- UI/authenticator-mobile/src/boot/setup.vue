<template>
  <view class="container">
    <!-- <app-loading v-if="!isAppReady"></app-loading> -->
    <app v-if="isAppReady"></app>
  </view>
</template>

<script>
import Vue from "vue-native-core";
import { VueNativeBase } from "native-base";
import * as Font from "expo-font";
import VueNativeSock from "vue-native-websocket";
import { host } from "../shared";
import App from "../App.vue";

// registering all native-base components to the global scope of the Vue
Vue.use(VueNativeBase);
Vue.use(VueNativeSock, "ws://" + host + ":9090");
export default {
  components: { App },
  data() {
    return {
      isAppReady: false
    };
  },
  created() {
    this.loadFonts();
  },
  methods: {
    async loadFonts() {
      try {
        this.isAppReady = false;
        await Font.loadAsync({
          Roboto: require("../../node_modules/native-base/Fonts/Roboto.ttf"),
          Roboto_medium: require("../../node_modules/native-base/Fonts/Roboto_medium.ttf"),
          Ionicons: require("../../node_modules/@expo/vector-icons/build/vendor/react-native-vector-icons/Fonts/Ionicons.ttf")
        });
        this.isAppReady = true;
      } catch (error) {
        console.log("some error occured", error);
        this.isAppReady = true;
      }
    }
  }
};
</script>

<style>
.container {
  flex: 1;
}
</style>
