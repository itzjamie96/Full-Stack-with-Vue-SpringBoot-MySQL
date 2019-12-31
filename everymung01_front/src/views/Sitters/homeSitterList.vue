<template>
<v-container>
  <v-card
    class="mb-4 mt-2 mx-auto"
    max-width="85%"
    outlined
    v-for="sitter in sitterList"
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
        <p>{{sitter.sitterAddress}}</p>
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
</v-container>

</template>

<script>
import axios from "axios" 
export default {
    data () {
      return {
        sitterList: [ //데이터베이스에서 받은 객체들이 들어갈 객체배열
        ],
      }
    },
    created(){ //현재 컴포넌트가 생성되자 마자 initialize를 수행하라는 의미
      this.initialize()
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
      }

    }
}

</script>