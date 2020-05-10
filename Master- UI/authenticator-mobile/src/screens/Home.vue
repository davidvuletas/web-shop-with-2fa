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

import RNFetchBlob from "rn-fetch-blob";
import {NetworkInfo} from 'react-native-network-info';

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
    goToQRCodeScreen() {
      this.navigation.navigate("QRReader");
    },
    getAllRegisteredAccounts() {
      RNFetchBlob.config({
        trusty: true
      })
        .fetch("GET", "https://192.168.0.13:8084/api/account")
        .then(res => this.accounts = JSON.parse(res.data))
        .catch(err => {
          console.log(err); //`Whoopsy doodle! Error - ${JSON.stringify(err)}`);
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