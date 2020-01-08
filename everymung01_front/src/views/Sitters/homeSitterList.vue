<template>
<v-container>
  <v-col cols="12" sm="6">
    <v-row justify="center">
      <v-col cols="12" sm="10">
        <v-select 
            v-model="area"
            v-bind:items="areaList"
            item-text="name"
            item-value="name"
            attach
            label="지역을 선택해주세요"
            prepend-icon="place"
            id="area"
        ></v-select>
      </v-col>
    </v-row>
  </v-col>

 <div v-infinite-scroll="loadMore" infinite-scroll-disabled="busy" infinite-scroll-distance="5">
  <v-card
    class="mb-4 mt-2 mx-auto"
    max-width="85%"
    outlined
    v-for="sitter in sitterList"
    v-show="sitter.sitterAddress.includes(area)"
    :key="sitter.id"
  >
    <v-list-item three-line>
      <v-list-item-avatar
        tile
        size="150"
      >
        <v-img
          :src="'http://localhost:1234/download/' + sitter.sitterImg1"
        ></v-img>
      </v-list-item-avatar>

      <v-list-item-content class="mx-auto">
        <p class="body-2">{{sitter.sittingType}} SITTER / {{sitter.sitterNo}}</p>
        <p class="font-weight-bold ">{{sitter.sitterName}}</p> 
        <p>{{sitter.sitterAddress.slice(0,7)}}</p>
        <p class="headline">{{sitter.profileTitle}}</p>
      </v-list-item-content>

      <v-card-actions>
        <v-btn 
          x-large 
          color
          @click="$router.push({path: '/homesitters/' + sitter.sitterNo})"
          >시터 상세 보기</v-btn>
      </v-card-actions>
    </v-list-item>
 </v-card>
 </div>
</v-container>

</template>

<script>
import axios from "axios" 
import {mapState,mapActions} from "vuex"

export default {
    data () {
      return {
        sitterList: [ //데이터베이스에서 받은 객체들이 들어갈 객체배열
        ],
        area: '',
        busy: false, //
        limit: 5, //
        resultList: [] //
      }
    },
    created(){ //현재 컴포넌트가 생성되자 마자 initialize를 수행하라는 의미
      // this.initialize(),
      this.loadMore();
    },

    computed: {
      ...mapState(["areaList"])
    },

    methods:{
      initialize(){//DB와 연동
        axios.get('http://localhost:1234/showHomeSitters')
          .then(res => {
            this.sitterList=res.data //객체에 DB에서 받은 데이터를 넣어줌
            console.log(res);
          })
          .catch(err => {
            // handle error
            console.log(err);
          })
      },
      showDetail(item){
        const sitterNo = item.sitterNo
        console.log('detail : ' + sitterNo);

        this.$router.push({path: '/homesitters/' + sitterNo})
      },
      searchAddress(area){
        console.log(area)
        axios.get(`http://localhost:1234/showSitterByAddress/homeSitter/${area}`)
          .then(res => {
            this.sitterList = res.data
            console.log(res);
          }).catch(err => {
            console.log(err);
          })
      },
      loadMore() {
        if(! this.busy){
        this.busy = true;
        axios.get('http://localhost:1234/showHomeSitters')
          .then(res => {
            const append = res.data.slice(this.sitterList.length,this.sitterList.length + this.limit )         
            this.sitterList = this.sitterList.concat(append);
            this.busy=false;
          })
          .catch(err => {
            console.log(err);
            this.busy=false;
          })
        }
      }

    }
}

</script>