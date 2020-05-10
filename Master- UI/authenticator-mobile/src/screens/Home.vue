<template>
  <nb-container>
    <nb-header noLeft>
      <nb-body>
        <nb-title>Authenticator</nb-title>
      </nb-body>
      <nb-right>
        <nb-button transparent>
          <nb-text>Cancel</nb-text>
        </nb-button>
      </nb-right>
    </nb-header>
    <nb-text
      :style="{marginTop: '80%', marginLeft:'20%'}"
      v-if="accounts.length < 1"
    >Account are not registered yet!</nb-text>
    <accounts-list :data="accounts"></accounts-list>
    <nb-footer>
      <nb-footer-tab>
        <nb-button :active="true">
          <nb-icon name="apps" :active="true" />
          <nb-text>Accounts</nb-text>
        </nb-button>
        <nb-button :onPress="goToQRCodeScreen">
          <nb-icon type="MaterialCommunityIcons" name="two-factor-authentication" />
          <nb-text>Add account</nb-text>
        </nb-button>
      </nb-footer-tab>
    </nb-footer>
  </nb-container>
</template>
<script>
import AccountList from "../components/AccountsList";
import axios from "axios";
import { eventBus } from "../App";
import { api } from "../App";

import { fetch } from "react-native-ssl-pinning";
import pinch from "react-native-pinch-new";

export default {
  components: { "accounts-list": AccountList },
  data() {
    return {
      api: axios.create({
        baseURL: "https://192.168.0.12:8084/api/",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          Accept: "application/json"
        },
        agentOptions: {
          rejectUnauthorized: false
        }
      }),
      accounts: []
    };
  },
  props: {
    navigation: {
      type: Object
    }
  },
  methods: {
    goToQRCodeScreen() {
      this.navigation.navigate("QRReader");
    },
    getAllRegisteredAccounts() {
      fetch("https://192.168.0.12:8084/api/account", {
        method: "get",
        timeoutInterval: 10000, // timeout after 10 seconds
        //disableAllSecurity: true,
        sslPinning: {
          certs: ["authenticator_ui"] // cert file name without the `.cer`
        }
      })
        .then(res => console.log(`We got your response! Response - ${res}`))
        .catch(err => {
          console.log(err);//`Whoopsy doodle! Error - ${JSON.stringify(err)}`);
        });
    }
  },
  created() {
    this.getAllRegisteredAccounts();
    eventBus.$on("backToHome", val => {
      this.getAllRegisteredAccounts();
    });
    console.log("create");
  }
};
</script>