<template>
  <nb-list-item thumbnail>
    <nb-left>
      <nb-thumbnail square :source="correctLogo()" />
    </nb-left>
    <nb-body>
      <nb-text :style="{fontSize: 21}">{{ account.email }}</nb-text>
      <nb-right></nb-right>
      <nb-text :style="{fontSize: 16}" note>Last activity: {{ account.lastActivity }}</nb-text>
    </nb-body>
    <nb-right>
      <nb-button transparent iconLeft :onPress="(event) => goToQRCodeScreenVerify(account.email)">
        <nb-icon active type="MaterialCommunityIcons" ios="qrcode-scan" android="qrcode-scan" />
      </nb-button>
    </nb-right>
  </nb-list-item>
</template>

<script>
import webshop from "../../assets/webshop.png";
import finance from "../../assets/finance.png";
import { eventBus } from "../shared";

export default {
  data() {
    return {
      navigation: {
        type: Object
      }
    };
  },
  props: {
    account: Object
  },
  methods: {
    goToQRCodeScreenVerify(mail) {
      eventBus.$emit("qrCodeRead", mail);
    },
    correctLogo() {
      let role = this.account.application;
      if(role == 'webshop') {
        return webshop;
      } else {
        return finance;
      }
    }
  }
};
</script>
