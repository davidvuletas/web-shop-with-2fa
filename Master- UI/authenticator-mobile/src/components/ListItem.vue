<template>
  <nb-list-item thumbnail>
    <nb-left>
      <nb-thumbnail small :source="logo" />
    </nb-left>
    <nb-body>
      <nb-text>{{ account.email }}</nb-text>
      <nb-text note :numberOfLines="1">{{ account.application }}</nb-text>
    </nb-body>
    <nb-right>
      <nb-button transparent iconLeft :onPress="scan">
        <nb-icon active type="MaterialCommunityIcons" ios="qrcode-scan" android="qrcode-scan" />
      </nb-button>
    </nb-right>
  </nb-list-item>
</template>

<script>
import logo from "../../assets/list-security-logo.png";
export default {
  data() {
    return {
      logo: logo
    };
  },
  props: {
    account: Object
  },
  methods: {
    scan() {
      alert("Usao");
    }
  }
};
</script>
