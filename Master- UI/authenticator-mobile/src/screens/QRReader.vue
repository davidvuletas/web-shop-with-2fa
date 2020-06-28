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

import { eventBus } from "../shared";
import qrcodelogo from "../../assets/qr-code.png";
import RNFetchBlob from "rn-fetch-blob";
import { host } from "../shared";

export default {
  components: {
    BarCodeScanner
  },
  mounted() {
    Permissions.askAsync(Permissions.CAMERA);
  },
  data: function() {
    return {
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
      registeredMail: ''
    };
  },
  methods: {
    handleBarCodeScanned(e) {
      if (!this.qrCode) {
        this.qrCode = e.data;
        if (this.navigation.state.params.type === "login") {
          const mail = this.navigation.state.params.mail;
          this.validateAccountForQRCode(mail);
        } else {
          this.registerAccountForQRCode();
        }
      }
    },
    goBack() {
      this.navigation.navigate("Home");
      eventBus.$emit("backToHome", true);
    },
    goToPinCodeScreen(email, applicationName) {
      this.navigation.navigate("PinCodeScreen", {email: email, applicationName: applicationName});
    },
    resetQRCode() {
      this.qrCode = null;
    },
    registerAccountForQRCode() {
      let scopedResetQRCode = this.resetQRCode;
      let scopedBack = this.goBack;
      let scopedPinCode = this.goToPinCodeScreen;
      RNFetchBlob.config({
        trusty: true
      })
        .fetch(
          "POST",
          "https://" + host + ":8084/api/account/register",
          {
            "Content-Type": "application/json"
          },
          this.qrCode
        )
        .then(function(data) {
          if ([401, 404, 400, 409, 500].indexOf(data.respInfo.status) > -1) {
            Alert.alert(
              "Failed",
              JSON.parse(data.data).message,
              [{ text: "Cancel", onPress: () => scopedResetQRCode() }],
              { cancelable: false }
            );
          } else {
            let parsedData = JSON.parse(data.data);
            Alert.alert(
              "Success",
              "QR code scanned successfully!",
              [{ text: "Ok", onPress: () => scopedPinCode(parsedData.email, parsedData.applicationName) }],
              { cancelable: false }
            );
          }
        })
        .catch(function(error) {
          console.log(error.response.message);
          Alert.alert(
            "Failed",
            "Connection with server is not working",
            [{ text: "Cancel", onPress: () => scopedResetQRCode() }],
            { cancelable: false }
          );
        });
    },
    validateAccountForQRCode(mail) {
      let scopedResetQRCode = this.resetQRCode;
      let scopedBack = this.goBack;
      RNFetchBlob.config({
        trusty: true
      })
        .fetch(
          "POST",
          "https://" + host + ":8084/api/account/checkAccount/" + mail,
          {
            "Content-Type": "application/json"
          },
          this.qrCode
        )
        .then(function(data) {
          if ([401, 404, 400, 409, 500].indexOf(data.respInfo.status) > -1) {
            Alert.alert(
              "Failed",
              JSON.parse(data.data).message,
              [{ text: "Cancel", onPress: () => scopedResetQRCode() }],
              { cancelable: false }
            );
          } else {
            Alert.alert(
              "Success",
              "QR code scanned successfully!",
              [{ text: "Ok", onPress: () => scopedBack() }],
              { cancelable: false }
            );
          }
        })
        .catch(function(error) {
          console.log(error.response.message);
          Alert.alert(
            "Failed",
            "Connection with server is not working",
            [{ text: "Cancel", onPress: () => scopedResetQRCode() }],
            { cancelable: false }
          );
        });
    }
  },
  props: {
    navigation: {
      type: Object
    }
  }
};
</script>

