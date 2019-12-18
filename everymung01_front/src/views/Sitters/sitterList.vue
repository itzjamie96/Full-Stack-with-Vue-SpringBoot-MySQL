<template>
<v-container>
  <v-card
    class="mb-3"
    max-width="xs10"
    outlined
    v-for="sitter in desserts"
    :key="sitter.id"
  >
    <v-list-item three-line>
      <v-list-item-avatar
        tile
        size="180"
      >
        <v-img
          :src="sitter.imageUrl"
        ></v-img>
      </v-list-item-avatar>

      <v-list-item-content>
        <div class="overline mb-4">{{sitter.sittingType}} SITTER </div>
        <v-list-item-title class="headline mb-1">{{sitter.sitterNo}}</v-list-item-title>
        <v-list-item-subtitle>{{sitter.sitterName}}</v-list-item-subtitle>
        <v-list-item-subtitle>{{sitter.sitterEmail}}</v-list-item-subtitle>
      </v-list-item-content>

      <v-card-actions>
        <v-btn 
          text 
          @click="$router.push({path: '/meetups/' + sitter.sitterNo})"
          >View Details</v-btn>
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
        desserts: [ //데이터베이스에서 받은 객체들이 들어갈 객체배열
        ],
      }
    },
    created(){ //현재 컴포넌트가 생성되자 마자 initialize를 수행하라는 의미
      this.initialize()
    },
    computed: {//무시
    },

    methods:{
      initialize(){//DB와 연동
        axios.get('http://localhost:1234/showAllSitters')
          .then(res => {
            this.desserts=res.data //객체에 DB에서 받은 데이터를 넣어줌
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

        this.$router.push({path: '/meetups/' + sitterNo})
      }

    }
}

</script>