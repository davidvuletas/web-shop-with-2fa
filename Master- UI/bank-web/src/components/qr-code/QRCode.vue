<template>
  <div class="qrcodebody">
    <img :src="this.image" />
    <BaseTimer />
  </div>
</template>

<script>
import BaseTimer from "../timer/Timer.vue";
import { eventBus } from "../../main";

export default {
  created() {
    eventBus.$on("timerFinished", val => {
      this.generateQRCode();
    });
    this.email = this.$route.params.name;
    this.generateQRCode();
  },
  data() {
    return {
      email: "",
      image: ""
    };
  },
  methods: {
    generateQRCode() {
      this.$http.post("qr-code/" + atob(this.email)).then(response => {
        this.image = response.bodyText;
        console.log(response);
      });
    }
  },
  components: {
    BaseTimer
  }
};
</script>
<style scoped>
.qrcodebody {
  background-color: white;
  width: 40%;
  margin: 10% auto;
  align-items: center;
}
</style>
