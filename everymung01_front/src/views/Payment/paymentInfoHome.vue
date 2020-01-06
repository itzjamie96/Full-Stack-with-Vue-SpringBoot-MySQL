<template>
  <v-container fluid>
      <v-row>
      
        <v-col cols="6">
          <payment-detail-home/>
        </v-col>
        
        <v-col cols="6">
          <payment-receipt/>
        </v-col>

      </v-row>
  </v-container>
</template>

<script>
import PaymentDetailHome from '@/components/PaymentDetail/homePaymentDetailTry.vue'
import PaymentReceipt from '@/components/PaymentDetail/paymentReceiptHome.vue'

// {{$store.state.reservationList[0]}}

export default {
  data() {
    return {
      paymentVO : []
    }
  },

  components : {
    'payment-detail-home' : PaymentDetailHome,
    'payment-receipt' : PaymentReceipt
  },

}
</script>