<template>
  <view :style="{flex: 1,
        flexDirection: 'column',
        justifyContent: 'flex-end'}">
    <bar-code-scanner
      :onBarCodeScanned="handleBarCodeScanned"
      :barCodeTypes="[BarCodeScanner.Constants.BarCodeType.qr]"
      :style="[StyleSheet.absoluteFill, qrStyle.container]"
    >
      <nb-text :style="qrStyle.description">Scan your QR code</nb-text>
      <image :style="qrStyle.qr" :source="qrcodeLogo" />
      <nb-button transparent :onPress="goBack">
        <nb-text :style="qrStyle.cancel">Cancel</nb-text>
      </nb-button>
    </bar-code-scanner>
  </view>
</template>
<script>
import * as React from "react";
import {
  Text,
  View,
  StyleSheet,
  Button,
  Dimensions,
  Alert
} from "react-native";

import Constants from "expo-constants";
import * as Permissions from "expo-permissions";
import { BarCodeScanner } from "expo-barcode-scanner";

import axios from "axios";
import {eventBus} from "../App"
import qrcodelogo from "../../assets/qr-code.png";

export default {
  components: {
    BarCodeScanner
  },
  mounted() {
    Permissions.askAsync(Permissions.CAMERA);
  },
  data: function() {
    return {
      api: axios.create({
        baseURL: "http://192.168.0.12:8081/api/"
      }),
      StyleSheet: StyleSheet,
      BarCodeScanner: BarCodeScanner,
      qrCode: null,
      qrStyle: {
        container: {
          flex: 1,
          justifyContent: "center",
          alignItems: "center",
          paddingTop: Constants.statusBarHeight,
          backgroundColor: "#ecf0f1",
          padding: 8
        },
        qr: {
          marginTop: "20%",
          marginBottom: "20%",
          width: Dimensions.get("window").width * 0.8,
          height: Dimensions.get("window").height * 0.5
        },
        description: {
          fontSize: 25,
          marginTop: "10%",
          textAlign: "center",
          width: "70%",
          color: "white"
        },
        cancel: {
          fontSize: 20,
          textAlign: "center",
          width: "50%",
          marginBottom: "5%",
          color: "white"
        }
      },
      qrcodeLogo: qrcodelogo,
    };
  },
  methods: {
    handleBarCodeScanned(e) {
      if (!this.qrCode) {
        this.qrCode = e.data;
        let scopedBack = this.goBack;
        let scopedResetQRCode = this.resetQRCode;
        this.api
          .post("account/register", this.qrCode)
          .then(function(data) {
            Alert.alert(
              "Success",
              "QR code scanned successfully!",
              [{ text: "Ok", onPress:() => scopedBack() }],
              { cancelable: false }
            );
          })
          .catch(function(error) {
            console.log(error.response.message)
            Alert.alert(
              "Failed",
              error.response.data.message,
              [{ text: "Cancel", onPress:() => scopedResetQRCode()}],
              { cancelable: false }
            );
          });
      }
    },
    goBack() {
      this.navigation.navigate("Home");
      //this.$socket.send('test')
      eventBus.$emit('backToHome', true);
    },
    resetQRCode() {
      this.qrCode = null;
    }
  },
  props: {
    navigation: {
      type: Object
    }
  }
};
</script>

