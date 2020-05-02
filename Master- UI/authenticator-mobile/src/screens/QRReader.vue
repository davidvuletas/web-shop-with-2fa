<template>
  <view style="flex: 1">
    <bar-code-scanner
      :onBarCodeScanned="handleBarCodeScanned"
      :barCodeTypes="[BarCodeScanner.Constants.BarCodeType.qr]"
      :style="StyleSheet.absoluteFillObject"
    />
  </view>
</template>
<script>
import * as React from 'react';
import { Text, View, StyleSheet, Button } from 'react-native';
import Constants from 'expo-constants';
import * as Permissions from 'expo-permissions';
import { BarCodeScanner } from 'expo-barcode-scanner';

export default {
  components: {
    BarCodeScanner
  },
  mounted () {
    Permissions.askAsync(Permissions.CAMERA)
  },
  data: function() {
    return {
      StyleSheet: StyleSheet,
      BarCodeScanner: BarCodeScanner,
      qrCode: null
    };
  },
  methods: {
    handleBarCodeScanned (e) {
        if(!this.qrCode) {
            this.qrCode = e.data;
            console.log(this.qrCode);
        }
    }
  }
};
</script>