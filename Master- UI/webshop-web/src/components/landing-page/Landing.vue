<template>
  <div>
    <b-jumbotron class="landingBody" align="center" border-variant="dark" header-level="5">
      <template v-slot:header>Webshop system</template>
      <hr />
      <template v-slot:lead>
        Welcome to Webshop system,
        <br />if you want to proceed you need to be a logged in or registered.
      </template>
      <div>
        <b-row style="margin-top:10%">
          <b-col align-self="center">
            <b-button variant="primary" to="/login">Login</b-button>
          </b-col>
        </b-row>
        <b-row style="margin-top:10%">
          <b-col align-self="center">
            <b-button variant="primary" to="/register">Registration</b-button>
          </b-col>
        </b-row>
      </div>
    </b-jumbotron>
  </div>
</template>

<script>
export default {
  
};
</script>

<style scoped>
.landingBody {
  width: 40%;
  margin: 10% auto;
  align-items: center;
}
</style>