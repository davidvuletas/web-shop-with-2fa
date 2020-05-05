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

//import { fetchApi } from "react-native-ssl-pinning";
 import RNFetchBlob from 'rn-fetch-blob'

export default {
  components: { "accounts-list": AccountList },
  data() {
    return {
      api: axios.create({
        baseURL: "https://192.168.0.12:8081/api/",
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
      /*this.api
        .get("info")
        .then(response => console.log(response)) //(this.accounts = response.data))
        .catch(function(e) {
          let errorObject = JSON.parse(JSON.stringify(e));
          console.log(errorObject);
        });*/

      /*fetchApi("https://192.168.0.12:8081/api/account", {
        method: "GET",
        timeoutInterval: 1000, // milliseconds
        // your certificates array (needed only in android) ios will pick it automatically
        sslPinning: {
          certs: ['ca_root'] // your certificates name (without extension), for example cert1.cer, cert2.cer
        },
        headers: {
          Accept: "application/json; charset=utf-8",
          "Access-Control-Allow-Origin": "*"
        }
      })
        .then(response => {
          console.log(response);
        })
        .catch(err => {
          console.log(err);
        });*/
      /*RNFetchBlob.config({
        trusty: true
      }).fetch('GET', 'https://192.168.0.12:8081/api/account')
      .then(response => {
          console.log(response);
        })
        .catch(err => {
          console.log(err);
        })*/
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