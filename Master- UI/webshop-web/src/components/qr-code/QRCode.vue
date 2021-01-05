<template>
  <div class="qrcodebody">
    <img :src="this.image" />
    <b-button
      variant="primary"
      v-if="this.finishedTime === true"
      :style="{marginLeft: '24%'}"
      @click.prevent="regenerateQRCode"
    >Regenerate QR code</b-button>
    <BaseTimer :style="{marginLeft : '20%', marginTop: '10%'}" />
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

import BaseTimer from "../timer/Timer.vue";
import { eventBus } from "../../main";
import { WEBSOCKET_URL, TOPIC_URL } from "../../urls";

export default {
  created() {
    eventBus.$on("timerFinished", val => {
      this.finishedTime = true;
    });
    this.email = this.$route.params.name;
    this.generateQRCode();
    this.connectToWS();
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
      eventBus.$emit("regenerate", true);
      this.finishedTime = false;
    },
    connectToWS() {
      this.socket = new SockJS(WEBSOCKET_URL);
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
        {},
        frame => {
          this.stompClient.subscribe(
            TOPIC_URL + atob(this.email) + "/register",
            tick => {
              this.displayToast();
              this.$http.put("users/verified/" + atob(this.email)).then(
                response => {
                  console.log(response);
                  this.$router.push({ name: "landing-page" });
                },
                error => console.log(error)
              );
            }
          );
          this.stompClient.subscribe(
            TOPIC_URL + atob(this.email) + "/login",
            tick => {
              this.displayToast();
              setTimeout(() => {
                this.$router.push({ name: "account-page" });
              }, 2000);
            }
          );
        },
        error => {
          console.log(error);
        }
      );
    },
    displayToast() {
      this.$root.$bvToast.toast(`QR code are successfully scanned!`, {
        title: `Success`,
        variant: "success",
        solid: true,
        appendToast: false
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
  width: 25%;
  margin: 4% auto;
  align-items: center;
}
</style>
