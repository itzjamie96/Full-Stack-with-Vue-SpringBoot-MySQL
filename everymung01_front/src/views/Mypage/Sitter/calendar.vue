<template>
<v-container>

  {{paylist[0]}}
  <full-calendar :events="fcEvents" locale="ko"
      @eventClick="eventClick"
  >
    <!-- v-dialog v-model="dialog">
      <v-card>
        <p>testing</p>
      </v-card>
    </<!-->
  </full-calendar> 
</v-container>
</template>

<script>
import {mapState,mapActions} from "vuex"
import axios from "axios"

export default {

  data () {
	return {
    fcEvents : [ ],
    paylist: []
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
            console.log(res.data);
            for(let i in res.data){
              let lsm = {title:'',start:'',end:''}
              lsm.title=res.data[i].sitterName
              lsm.start=res.data[i].startTime.split(" ")[0]
              lsm.end=res.data[i].endTime.split(" ")[0]
              this.fcEvents.push(lsm)
            }
          })
          .catch(err => {
            console.log(err);
          })
      },

  },
}
</script>