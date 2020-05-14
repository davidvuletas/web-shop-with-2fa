<template>
  <nb-container>
    <nb-header noLeft :style="{marginTop:'3%', marginBottom: '2%'}">
      <nb-body>
        <nb-title>Authenticator</nb-title>
      </nb-body>
    </nb-header>
    <nb-text
      :style="{marginTop: '80%', marginLeft:'20%'}"
      v-if="accounts.length < 1"
    >Account are not registered yet!</nb-text>
    <accounts-list :data="accounts"></accounts-list>
    <nb-footer>
      <nb-footer-tab>
        <nb-button :active="true" :onPress="getAllRegisteredAccounts">
          <nb-icon type="MaterialCommunityIcons" name="account-card-details" :active="true" />
          <nb-text>Accounts</nb-text>
        </nb-button>
        <nb-button :onPress="goToQRCodeScreenRegister">
          <nb-icon type="MaterialCommunityIcons" name="two-factor-authentication" />
          <nb-text>Add account</nb-text>
        </nb-button>
      </nb-footer-tab>
    </nb-footer>
  </nb-container>
</template>
<script>
import AccountList from "../components/AccountsList";
import { eventBus } from "../shared";

import RNFetchBlob from "rn-fetch-blob";
import { host } from "../shared";

export default {
  components: { "accounts-list": AccountList },
  data() {
    return {
      accounts: []
    };
  },
  props: {
    navigation: {
      type: Object
    }
  },
  methods: {
    goToQRCodeScreenRegister() {
      this.navigation.navigate("QRReader", { type: "register" });
    },
    getAllRegisteredAccounts() {
      RNFetchBlob.config({
        trusty: true
      })
        .fetch("GET", "https://" + host + ":8084/api/account")
        .then(res => (this.accounts = JSON.parse(res.data)))
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
    this.getAllRegisteredAccounts();
    eventBus.$on("backToHome", val => {
      this.getAllRegisteredAccounts();
    });
    eventBus.$on("qrCodeRead", mail => {
      this.navigation.navigate("QRReader", { type: "login", mail: mail });
    });
  }
};
</script>