<template>
  <view :style="{marginTop:'30%'}">
    <nb-h2 :style="{textAlign: 'center'}">Input 4 digit pin-code which you received on your email</nb-h2>
    <view :style="{marginLeft:'10%',marginTop: '40%'}">
      <SmoothPinCodeInput
        :value="digitsCode"
        :onTextChange="setPinCode"
        :onFulfill="checkCode"
        :cellSize="80"
        :textStyle="{fontSize: 24}"
        :cellStyle="{borderRadius:24, borderWidth: 2}"
      />
    </view>
  </view>
</template>
<script>
import SmoothPinCodeInput from "react-native-smooth-pincode-input";
import RNFetchBlob from "rn-fetch-blob";
import { eventBus } from "../shared";
import { host } from "../shared";
import { Alert } from "react-native";
export default {
  components: {
    SmoothPinCodeInput
  },
  data() {
    return {
      digitsCode: ""
    };
  },
  methods: {
    setPinCode(code) {
      this.digitsCode = code;
    },
    goBack() {
      this.navigation.navigate("Home");
      eventBus.$emit("backToHome", true);
    },
    checkCode(code) {
      let mail = this.navigation.state.params.email;
      let applicationName = this.navigation.state.params.applicationName;
      let scopedBack = this.goBack;

      RNFetchBlob.config({
        trusty: true
      })
        .fetch(
          "POST",
          "https://" + host + ":8084/api/code/valid",
          {
            "Content-Type": "application/json"
          },
          JSON.stringify({
            code: this.digitsCode,
            email: mail.replace(/"([^"]+(?="))"/g, "$1"),
            applicationName: applicationName
          })
        )
        .then(function(data) {
          if (data.data === 'true') {
            Alert.alert(
              "Success",
              "Pin code are correct!",
              [
                {
                  text: "Ok",
                  onPress: () => scopedBack()
                }
              ],
              { cancelable: false }
            );
          } else if (data.data === 'false') {
            Alert.alert(
              "Failed",
              "Pin code is not correct!",
              [{ text: "Cancel" }],
              { cancelable: true }
            );
          }
        })
        .catch(function(error) {
          console.log(error);
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