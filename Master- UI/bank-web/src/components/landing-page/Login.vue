<template>
  <b-jumbotron class="loginBody" border-variant="dark" header-level="5">
    <b-form>
      <b-form-group id="input-group-mail" label="Email address" label-for="input-mail">
        <b-form-input
          id="input-mail"
          v-model="loginForm.email"
          type="email"
          placeholder="Enter email"
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-password" label="Password" label-for="input-password">
        <b-form-input
          id="input-password"
          v-model="loginForm.password"
          type="password"
          placeholder="Enter password"
        ></b-form-input>
      </b-form-group>
      <b-row>
        <b-col>
          <b-button
            variant="primary"
            type="submit"
            @click.prevent="login"
            :disabled="
          loginForm.password == '' || loginForm.email.indexOf('@') == -1 "
          >Login</b-button>
        </b-col>
        <b-col>
          <p>
            Or if you don't have account
            <b-link to="register">Register</b-link>
          </p>
        </b-col>
      </b-row>
    </b-form>
  </b-jumbotron>
</template>

<script>
export default {
  data() {
    return {
      loginForm: {
        email: "",
        password: ""
      }
    };
  },
  methods: {
    login() {
      localStorage.clear();
      this.$http.post("users/login", this.loginForm).then(
        response => {
          localStorage.setItem("role", response.data.role);
          localStorage.setItem("jwtToken", response.headers.get("JWT"));
          console.log(response.data);
          let mailEncrypted = btoa(response.data.email);
          if (response.data.twoFactorAuth) {
            this.$router.push({
              name: "qr-code",
              params: { name: mailEncrypted, type: "login" }
            });
          } else {
            this.$router.push({
              name: "account-page",
            });
          }
        },
        error => {
          this.badLogin();
        }
      );
    },
    badLogin() {
      this.$root.$bvToast.toast(`Your username or password are not correct!`, {
        title: `Error`,
        variant: "danger",
        solid: true,
        appendToast: false
      });
    }
  }
};
</script>
<style scoped>
.loginBody {
  width: 40%;
  margin: 10% auto;
  align-items: center;
}
</style>