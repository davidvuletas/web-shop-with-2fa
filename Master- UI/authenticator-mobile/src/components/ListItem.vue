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
import operator from "../../assets/operator-text.png";
import admin from "../../assets/admin-text.png";
import chief from "../../assets/manager-text.png";
import user from "../../assets/user-text.png";
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
      let role = this.account.role;
      console.log(role);
      if (role === "Admin") {
        return admin;
      } else if (role === "Operator") {
        return operator;
      } else if (role === "Chief") {
        return chief;
      } else {
        return user;
      }
    }
  }
};
</script>
