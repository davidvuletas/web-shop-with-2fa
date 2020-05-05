<template>
  <div class="qrcodebody">
    <img :src="this.image" />
    <b-button
      variant="primary"
      v-if="this.finishedTime === true"
      :style="{marginLeft: '24%'}"
      @click.prevent="regenerateQRCode"
    >Regenerate QRCode</b-button>
    <BaseTimer :style="{marginLeft : '20%', marginTop: '10%'}" />
  </div>
</template>

<script>
import BaseTimer from "../timer/Timer.vue";
import { eventBus } from "../../main";

export default {
  created() {
    eventBus.$on("timerFinished", val => {
      this.finishedTime = true;
    });
    this.email = this.$route.params.name;
    this.generateQRCode();
    this.$options.sockets.onmessage = (data) => console.log(data)

  },
  data() {
    return {
      email: "",
      image: "",
      finishedTime: false,
      wsConnection: null
    };
  },
  methods: {
    generateQRCode() {
      this.$http.post("qr-code/" + atob(this.email)).then(response => {
        this.image = response.bodyText;
        console.log(response);
      });
    },
    regenerateQRCode() {
      this.generateQRCode();
      eventBus.$emit('regenerate', true);
      this.finishedTime = false;
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
  width: 25%;
  margin: 4% auto;
  align-items: center;
}
</style>
