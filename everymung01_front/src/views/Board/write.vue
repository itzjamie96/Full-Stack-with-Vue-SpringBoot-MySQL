<template>
  <div><h1>write.vue~~!!</h1><hr>
   
        <v-col cols="12" sm="6" md="3">
          <v-text-field
            label="제목을 입력해주세요"
            outlined
            v-model="BoardVO.title"
          ></v-text-field>
        </v-col>
       
        <v-textarea
          label="내용을 입력해주세요"
          outlined
          name="input-7-4"
          v-model="BoardVO.content"
        ></v-textarea><br>
      <v-btn @click="add()">제출</v-btn>
      
  </div>
</template>
<script>
import axios from "axios"
import router from '../../router/index'
//### ?? 이걸 가져다 써야 로그인 연동이 된다 
import {mapState,mapActions} from "vuex"
export default {
    data(){
        return{
            
            BoardVO: {   
                boardNo:'',             
                title:'', 
                content:'',
                userName:'',
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
        // 저장하기(DB_INSERT)(C)
        add(){
          //### 
          this.BoardVO.boardNo= 
          console.log("add()_this.userInfo.userNo="+this.userInfo.userNo)
         // console.log("add()_this.userInfo.userNo="+this.userInfo.userNo)
          this.BoardVO.userNo = this.userInfo.userNo
          this.BoardVO.boardDate = new Date() // DB와 데이터타입을 맞춰줘야돼서 Date() 
          this.BoardVO.userName = this.userInfo.userName                     
          this.BoardVO.hits=0  
          //사용자 글쓰기(문의사항)        
          this.BoardVO.depth=0
          this.BoardVO.groupNo=this.BoardVO.bo
         
          // if(this.BoardVO.depth==0){
          //   this.BoardVO.groupNo=0
          // }
          /*
          else {
            this.BoardVO.groupNo=boardNo가 넘어와야함
          }
          */
            //BoardNo값을 안넣어서 보내면 스프링쪽에선 boardNo=0이라고 찍히더라. 
            
            axios.post ('http://localhost:1234/add',this.BoardVO) // 여기선 객체 던져주는 식이네 
           .then( res =>{
             this.BoardVO = res.data             
           }), 
          // // 입력칸 제출후 깨끗하게 만드는 기능코드 
          // this.BoardVO = this.default 
          //### 리로딩 
          router.push({name:'Board'}) //가끔씩 왜 그런진 모르겠는데, 리로딩이 안되는 경우가 있음. 
          
        },
    },//### userInfo를 받아와서 
      computed: {
        ...mapState(["isLogin","userInfo","isLoginError","role"])
      
    },
   
    
}
</script>
<style>
</style>