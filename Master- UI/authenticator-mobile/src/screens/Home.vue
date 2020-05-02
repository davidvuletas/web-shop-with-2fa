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
    <accounts-list :data=accounts></accounts-list>
    <nb-footer>
      <nb-footer-tab>
        <nb-button :active="true">
          <nb-icon name="apps" :active="true"/>
          <nb-text>Apps</nb-text>
        </nb-button>
        <nb-button :onPress="goToHomeScreen">
          <nb-icon type="MaterialCommunityIcons" name="two-factor-authentication"/>
          <nb-text>Add account</nb-text>
        </nb-button>
      </nb-footer-tab>
    </nb-footer>
  </nb-container>
</template>
<script>
import AccountList from "../components/AccountsList";

export default {
  components: { "accounts-list": AccountList },
  data() {
    return {
        accounts: [
        {
          id: "1234",
          email: "davidvuletas@gmail.com",
          application: "finance",
          time: "1234"
        },
        {
          id: "1235",
          email: "davidvuletas@yahoo.com",
          application: "shoping",
          time: "1234"
        }
      ]
    };
  },
  props: {
    navigation: {
      type: Object
    }
  },
  methods: {
    goToHomeScreen() {
      this.navigation.navigate("QRReader");
    }
  }
};
</script>