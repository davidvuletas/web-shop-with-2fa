<template>
  <nb-list-item thumbnail>
    <nb-left>
      <nb-thumbnail small :source="correctLogo()" />
    </nb-left>
    <nb-body>
      <nb-text :style="{fontSize: 22}">{{ account.email }}</nb-text>
      <nb-right></nb-right>
      <nb-text :style="{fontSize: 18}" note>Registered at: {{ account.dateOfCreation }}</nb-text>
    </nb-body>
    <nb-right>
      <nb-button transparent iconLeft :onPress="scan">
        <nb-icon active type="MaterialCommunityIcons" ios="qrcode-scan" android="qrcode-scan" />
      </nb-button>
    </nb-right>
  </nb-list-item>
</template>

<script>
import operator from "../../assets/operator.png";
import admin from "../../assets/admin.png";
import chief from "../../assets/manager.png";
import user from "../../assets/regular-user.png";
export default {
  data() {
    return {
    };
  },
  props: {
    account: Object
  },
  methods: {
    scan() {
    },
    correctLogo() {
      let role = this.account.role;
      console.log(role);
      if(role === 'Admin') {
        return admin;
      } else if(role === 'Operator') {
        return operator;
      } else if(role === 'Chief') {
        return chief;
      } else {
        return user;
      }
    }
  }
};
</script>
