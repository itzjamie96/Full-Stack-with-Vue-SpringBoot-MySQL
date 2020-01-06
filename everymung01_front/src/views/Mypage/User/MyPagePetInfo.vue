<template>
   <v-container fluid="" class="fill-height">
       <v-row class="fill-height">
        <v-col cols="3">
          <side-bar/>   
        </v-col>

        <v-col cols="9">
            <v-row>
                <v-card
                width="100%"
                outlined
                v-for="(userPet,index) in userPetList"
                :key="index"
                >
                    <v-list-item three-line>
                    <v-list-item-avatar
                        tile
                        size="100"
                    >

                        <v-img
                        :src="tempImg[index]"
                            > </v-img>
                         
                          
                    </v-list-item-avatar>
                    <h1>{{userPet.petNo}}</h1>
                    <v-list-item-content>
                        <p class="body-2">{{userPet.petName}}</p>
                        <p class="font-weigth-bold"> {{userPet.dogBreed}},{{userPet.petGender}},{{userPet.petAge}} </p>
                        <v-btn id="">상세보기</v-btn>
                    </v-list-item-content>

                    <v-spacer></v-spacer>

                    <v-btn> 수정 </v-btn>
                    <v-btn> 삭제 </v-btn>
                </v-list-item>
                </v-card>

            </v-row>
            <v-row>

            <v-btn router :to="{name:'myPetDetail'}"> 반려견 추가하기 </v-btn>
            </v-row>
          
        </v-col>
      </v-row>
   </v-container>
</template>


<script>
import axios from 'axios'
import NavBar from '@/components/userNavigation.vue'
import {mapState} from 'vuex'

export default {
   data(){
       return{
           tempImg:[],
        userPetList:[
         
        ],
       }
   },
     components: {
    'side-bar' : NavBar
  },

   created(){
       console.log("created()")
       this.init()
   },

   computed: {
       ...mapState(["userInfo"]),
   },
   methods:{

       init(){
           
           axios.post(`http://localhost:1234/showAllpets/${this.userInfo.userNo}`) // 2=> 로그인 하면서 userNo 가 들어오면 동적으로 변수 바인딩 해줘야 한다. 
           .then(response => {
               this.userPetList = response.data
               for(let img in this.userPetList){
                   if(response.data[img].petImg === null){
                       this.tempImg[img]= "";
                   }else{
                   this.tempImg[img]='http://localhost:1234/download/'+response.data[img].petImg
                   }
               }
               console.log(response.data)
                   
           })
           .catch(error =>{
               console.log(error)
           })
       }
   }

    
}
</script>