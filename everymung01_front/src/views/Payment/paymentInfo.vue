<template>
  <v-container fluid>
      <v-row>
      
        <v-col cols="6">
          <payment-detail/>
        </v-col>
        {{$store.state.reservationList[0]}}
        <v-col cols="6">
          <payment-receipt/>
        </v-col>

      </v-row>
  </v-container>
</template>

<script>
//import {mapState} from "vuex"
import PaymentDetail from '@/components/PaymentDetail/paymentDetailTry.vue'
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
  computed:{
    //...mapState([reservationList]),
  },

  /* created() {
    this.paymentVO = this.$route.params.paymentVO
    console.log("aaaaaa"+this.paymentVO.sitterNo)
  } */
}
</script>