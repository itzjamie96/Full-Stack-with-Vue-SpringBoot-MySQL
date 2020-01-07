<template>
<v-container>

  {{paylist[0]}}
  <full-calendar :events="fcEvents" locale="ko"
      @eventClick="eventClick"
  >
    <v-dialog v-model="dialog">
      <v-card>
        <p>testing</p>
      </v-card>
    </v-dialog>
  </full-calendar>

</v-container>
</template>

<script>
import {mapState,mapActions} from "vuex"
import axios from "axios"

export default {

  data () {
	return {
    fcEvents : [
      {
      title : 'hihi',
      start : '2020-01-05',
      end : '2020-01-07'
      }
    ],
    paylist: [  ]
	}
  },
  components : {
	'full-calendar': require('vue-fullcalendar')	
  },

      created(){ //현재 컴포넌트가 생성되자 마자 initialize를 수행하라는 의미
    
      this.initialize()
      
    },

        computed: {
       ...mapState(["isLogin","userInfo"]),



    },

    methods : {

    'eventClick' (event, jsEvent, pos) {
       console.log('eventClick', event, jsEvent, pos)
        this.$router.push({path: '/'})

    },
    initialize(){//DB와 연동해서 게시판 목록을 전부 가져옴
        console.log(this.userInfo.sitterNo)
        axios.get(`http://localhost:1234/showSitterPayment/${this.userInfo.sitterNo}`)
          .then(res => {
            this.paylist=res.data //table row로 보여질 객체에 DB에서 받은 데이터를 넣어줌
            console.log(res);
          })
          .catch(err => {
            console.log(err);
          })
      },

  },
}
</script>