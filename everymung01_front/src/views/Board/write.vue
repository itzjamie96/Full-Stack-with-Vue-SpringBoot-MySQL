<template>
  <div><h1>write.vue~~!!</h1><hr>
   
        <v-col cols="12" sm="6" md="3">
          <v-text-field
            label="제목을 입력해주세요"
            outlined
            v-model="detail.title"
          ></v-text-field>
        </v-col>
       
        <v-textarea
          label="내용을 입력해주세요"
          outlined
          name="input-7-4"
          v-model="detail.content"
        ></v-textarea><br>
      <v-btn @click="add()">제출</v-btn>
      
  </div>

</template>

<script>
import axios from "axios"

export default {
    data(){
        return{
            //에러 해결인줄 알았으나 실패 => v-model값이 안담기는게 문제였는데, 왼쪽에 title은 변수명, 오른쪽 'title'은 v-model로 입력된 값으로 인식이 돼서, 
                     //DB에도 title컬럼에 'title'에 해당하는 v-model입력값으로 처리되네
                     //BUT 그냥 깡으로 'title'이란 고정값이 입력된거였음... 
            //### 에러해결_ v-model=""에 입력해주는 값을 detail.title이라고 쳐야지, data에서 입력한 detail:{title,}이 인식되늰거였는데, v-model="title"로 쓰니까 인식을 못하지.
            
            detail: { // 여기에 v-model값이 담기는구나. 신기하다!
                boardNo:'',
                title:'', 
                content:'',
                userNo:'',
                boardDate:'',
                depth:'',
                groupNo:'',
                hits:''
            },
            
            default:[]
          
        }
    },
    methods:{
        add(){
            console.log("test")
            console.log(this.detail)
            axios.post ('http://localhost:1234/add',this.detail) 
           .then( res =>(
             console.log(res),
               console.log(detail.title + detail.content),
               console.log("insert 성공")
          ))  
          // 입력칸 제출후 깨끗하게 만드는 기능코드 
          this.detail = this.default 

          window.location.href="http://localhost:8081/board"

        },
    }

}
</script>

<style>

</style>