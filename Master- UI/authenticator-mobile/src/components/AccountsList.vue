<template>
  <nb-container>
    <nb-content>
      <nb-list v-for="account in data" :key="account.id">
        <list-item :account="account"></list-item>
      </nb-list>
    </nb-content>
  </nb-container>
</template>

<script>
import ListItem from "./ListItem";
import { RefreshControl } from "react-native";

export default {
  components: {
    "list-item": ListItem
  },
  props: {
    data: Array
  },
  data() {
    return {
    };
  },
  methods: {
  },
};
</script>