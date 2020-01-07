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
import PaymentDetail from '@/components/PaymentDetail/paymentDetailTry.vue'
import PaymentReceipt from '@/components/PaymentDetail/paymentReceipt.vue'
// {{$store.state.reservationList[0]}}
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
}
</script>