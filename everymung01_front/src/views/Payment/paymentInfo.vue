<template>
  <v-container fluid>
      <v-row>
      
        <v-col cols="6">
          <payment-detail/>
        </v-col>

        <v-col cols="6">
          <payment-receipt/>
        </v-col>

      </v-row>
  </v-container>
</template>

<script>

import PaymentDetail from '@/components/PaymentDetail/paymentDetail.vue'
import PaymentReceipt from '@/components/PaymentDetail/paymentReceipt.vue'

export default {
  data() {
    return {
      paymentVO : []
    }
  },

  components : {
    'payment-detail' : PaymentDetail,
    'payment-receipt' : PaymentReceipt
  },

  created() {
    this.paymentVO = this.$route.params.paymentVO
    console.log("aaaaaa"+this.paymentVO.sitterNo)
  }
}
</script>