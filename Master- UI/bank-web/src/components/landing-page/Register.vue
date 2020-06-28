<template>
  <b-jumbotron class="registerBody" border-variant="dark" header-level="5">
    <b-form validated>
      <b-form-group
        id="input-group-mail"
        label="Email address"
        label-for="input-mail"
        description="Input your email with which you will be authenticated"
      >
        <b-form-input
          id="input-mail"
          v-model="registrationForm.email"
          type="email"
          required
          placeholder="Enter email"
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-password" label="Password" label-for="input-password">
        <b-form-input
          id="input-password"
          v-model="registrationForm.password"
          type="password"
          required
          placeholder="Enter password"
        ></b-form-input>
      </b-form-group>
      <b-form-group id="input-group-name" label="Name" label-for="input-name">
        <b-form-input
          id="input-name"
          v-model="registrationForm.name"
          type="text"
          required
          placeholder="Enter name"
        ></b-form-input>
      </b-form-group>
      <b-form-group id="input-group-lastname" label="Lastname" label-for="input-lastname">
        <b-form-input
          id="input-lastname"
          v-model="registrationForm.lastName"
          type="text"
          required
          placeholder="Enter lastname"
        ></b-form-input>
      </b-form-group>
      <b-form-group
        id="input-group-2fa"
        label="Two factor authentication"
        label-for="input-2fa"
        description="Click on 'Use' if you want to enable two factor authethication"
      >
        <b-form-radio-group
          v-model="registrationForm.twoFactorAuth"
          id="radio-group-2"
          name="radio-sub-component"
        >
          <b-form-radio name="yes-factor" value="true">Use</b-form-radio>
          <b-form-radio name="no-factor" value="false">Don't use</b-form-radio>
        </b-form-radio-group>
      </b-form-group>
      <b-row>
        <b-col>
          <b-button variant="primary" type="submit" @click.prevent="register">Register</b-button>
        </b-col>
        <b-col>
          <p>
            Or if you already have account
            <b-link to="/login">Login</b-link>
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
      registrationForm: {
        email: "",
        password: "",
        name: "",
        lastName: "",
        twoFactorAuth: "false"
      }
    };
  },
  methods: {
    register() {
      this.$http.post("users/register", this.registrationForm).then(
        response => {
          let mailEncrypted = btoa(this.registrationForm.email);
          if (this.registrationForm.twoFactorAuth === "true") {
            this.$router.push({
              name: "qr-code",
              params: { name: mailEncrypted, type: "register" }
            });
          } else {
            this.displayToast(
              "You are registered successfully",
              "Success",
              "success"
            );
            this.$router.push({ name: "landing-page" });
            
          }
        },
        error => {
          console.log(error);
        }
      );
    },
    displayToast(text, title, type) {
      this.$root.$bvToast.toast(text, {
        title: title,
        variant: type,
        solid: true,
        appendToast: false
      });
    }
  }
};
</script>
<style scoped>
.registerBody {
  width: 40%;
  margin: 10% auto;
  align-items: center;
}
</style>