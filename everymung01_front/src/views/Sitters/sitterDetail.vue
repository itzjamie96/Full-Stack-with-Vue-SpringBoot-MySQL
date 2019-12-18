<template>

    <v-container>
        <v-row>
            <v-col cols="12">
                <v-card>
                    <v-card-title>
                        <h4>{{sitterObj.sitterNo}} - </h4>
                        <h4>{{sitterObj.sittingType}} sitter</h4>
                    </v-card-title>
                    <v-list-item-avatar
                      tile
                      width="100%"
                      height="10%"
                    >
                      <!-- <v-img
                        :src="sitter.sitterImg1"
                      ></v-img> -->
                    </v-list-item-avatar>
                    
                    <v-card-text>
                        <div class="info--text"> - </div>
                        <div>{{sitterObj.sitterName}}</div>
                        <div>{{sitterObj.sitterPhone}}</div>
                        <div>{{sitterObj.sitterProfile}}</div>
                    </v-card-text>
                    <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn class="primary">Register</v-btn>
                    </v-card-actions>
                        
                </v-card>
            </v-col>
        </v-row>
    
    </v-container>
</template>

<script>
import Axios from 'axios'

export default {
   data() {
       return {
           sitterObj: []
       }
   },
   created() {
       this.initialize()
       
   },
   computed: {
       sitterNo() {
           return this.$route.params.sitterNo
       }
   },
   methods: {
       initialize() {
           const sitterNo = this.$route.params.sitterNo
           
           Axios
            .get(`http://localhost:1234/showSitterDetail/${sitterNo}`)
            .then(res => {
                this.sitterObj = res.data
                console.log(res);
            })
            .catch(err => {
                console.log(err);
            })

       }
   }
}
</script>
