<template>
   <v-container>
     <v-card
       class="mb-4 mt-2 mx-auto"
       max-width="50%"
       outlined
       v-for="userPet in userPetList"
       :key="userPet.userNo"
     >
     <v-list-item three-line>
       <v-list-item-avatar
        tile
        size="100"
       >
       <v-img
       :src="userPet.petImg"
        > {{userPet.petImg}}</v-img>
       </v-list-item-avatar>

       <v-list-item-content class="mx-auto">
        <p class="body-2">{{userPet.petName}}</p>
        <p class="font-weigth-bold">( {{userPet.dogBreed}},{{userPet.petGender}},{{userPet.petAge}} )</p>
        <v-btn>상세보기</v-btn>
       </v-list-item-content>
       <v-spacer></v-spacer>
       <v-btn> 수정 </v-btn>
       <v-btn> 삭제 </v-btn>
     </v-list-item>
     </v-card>
    <v-btn router :to="{name:'myPetDetail'}"> 반려견 추가하기 </v-btn>


   </v-container>
</template>


<script>
import{ mapState } from 'vuex'
import axios from 'axios'


export default {
   data(){
       return{
        userPetList:[

        ],
        
       }
   },

   created(){
       console.log("created()")
       this.init()
   },
   methods:{
       ...mapState(['userInfo']),

       init(){
           axios.get(`http://localhost:1234/showAllpets/${this.userInfo.userNo}`) // 2=> 로그인 하면서 userNo 가 들어오면 동적으로 변수 바인딩 해줘야 한다. 
           .then(response => {
               this.userPetList = response.data
               console.log(response.data)
           })
           .catch(error =>{
               console.log(error)
           })
       }
   }

    
}
</script>