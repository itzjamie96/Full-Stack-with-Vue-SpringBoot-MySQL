<template>
    <div>
        <v-row>
            <v-col
            v-for="reviews in reviews.slice(0,4)"
            :key="reviews.reviewNo">
            
                <v-card
                    outlined>
                <v-list-item three-line>
                <v-list-item-content class="text-center">
                    <p class="font-weight-bold">{{reviews.userName[0]}} * {{reviews.userName[2]}} 님</p>
                    <v-rating
                      v-model="reviews.stars"
                      background-color="orange lighten-3"
                      color="orange"
                      size="9"
                      readonly
                      dense=""
                    ></v-rating>
                    <p class="body-2">{{reviews.reviewContent}}</p>
                </v-list-item-content>
            </v-list-item>
        </v-card>    
            </v-col>
        </v-row>
       
    </div>
</template>

<script>
import axios from 'axios'

export default {
    data() {
        return {
        reviews: [] ,
    }
  },
      created(){ //현재 컴포넌트가 생성되자 마자 initialize를 수행하라는 의미
      this.initialize()
    },
    computed: {

    },

  methods: {
    initialize(){//DB와 연동

    //리뷰 긁어와야함
        axios.get('http://localhost:1234/showFeaturedReview')
          .then(res => {
            this.reviews=res.data //객체에 DB에서 받은 데이터를 넣어줌
            //console.log(res);
          })
          .catch(err => {
            // handle error
            console.log(err);
          })
  }
  }
}
</script>