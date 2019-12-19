<template>
    <div>
        마이페이지
        {{userPetList}}
    </div>
</template>


<script>
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
       
       init(){
           axios.get('http://localhost:1234/showAllpets/2')
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