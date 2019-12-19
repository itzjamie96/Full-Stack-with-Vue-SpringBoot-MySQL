<template>
<v-app>
  <v-row justify="center">
     <v-col>
    <vue-good-table
    :columns="columns"
    :rows="rows"
    @on-row-click="onRowClick"
    max-height="500px"
    :line-numbers="false"
    :search-options="{
    enabled: true,
    }"
>  </vue-good-table>
<v-btn @click="$router.push({path: '/write'})">글쓰기</v-btn>
<v-dialog v-model="dialog" persistent max-width="600px">
      <template v-slot:activator="{ on }">
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">상세정보</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field label="name" v-model="editedItem.name" required></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="name" v-model="editedItem.name" required></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="name" v-model="editedItem.name" required></v-text-field>
              </v-col>                            
              <v-col cols="12">
                <v-text-field label="age"  v-model="editedItem.age" required></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="city" v-model="editedItem.city" required></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">확인</v-btn>
          <v-btn color="blue darken-1" text @click="dele(editedItem)">삭제</v-btn>
          <v-btn color="blue darken-1" text @click="save(editedItem) ">저장</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-col>
  </v-row>
  </v-app>
</template>
<script>
import 'vue-good-table/dist/vue-good-table.css'
import { VueGoodTable } from 'vue-good-table'
import axios from 'axios'

export default {
  components: {
      VueGoodTable
  },
  data(){
    return {

       dialog: false,

      // V-MODEL로 입력된 값을 여기다가 담아서 엑시오스로 보냄
       editedItem:{
           boardNo: '',
           userNo:'',
           title: 'lsm',
           user: 'lsm',
           content:'',
           boardDate: '',
           hits: '',
       },

     columns: [
       {
           label: '글번호',
           field: 'boardNo',
           type: 'number'
       },
       {
           label: '제목',
           field: 'title'
       },
       {
           label: '작성자',
           field: 'writer'
       },       
       {
           label: '조회수',
           field: 'hits',
           type: 'number',
       },
       {
           label: '작성일',
           field: 'regDate',
          //  type: 'date',
          //  dateInputFormat: 'yyyy-MM-dd',
          //  dateOutputFormat: 'yy-MM-dd',
       } // MMM Do yy
     ],
     rows: [
        {
           boardNo: '',
           userNo:'',
           title: 'lsm',
           user: 'lsm',
           content:'',
           boardDate: '',
           hits: '',
        },
       // {
       //   id:1, name:"John",
       //   age: 20,
       //   createdAt: '',
       //   score: 0.03343
       // },
       // { id:2, name:"Jane", age: 24, createdAt: '2011-10-31', score: 0.03343 },
       // { id:3, name:"Susan", age: 16, createdAt: '2011-10-30', score: 0.03343 },
       // { id:4, name:"Chris", age: 55, createdAt: '2011-10-11', score: 0.03343 },
       // { id:5, name:"Dan", age: 40, createdAt: '2011-10-21', score: 0.03343 },
       // { id:6, name:"John", age: 20, createdAt: '2011-10-31', score: 0.03343 },
 
     ],

    }; // return } 
  },//data }
  created(){ //현재 컴포넌트가 생성되자 마자 initialize를 수행하라는 의미
        this.initialize()
    },
  methods: {
    initialize(){//DB와 연동해서 게시판 목록을 전부 가져옴
           axios.get('http://localhost:1234/showAllBoard')
          .then( response =>(
          //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@//
           this.rows=response.data //table row로 보여질 객체에 DB에서 받은 데이터를 넣어줌   
          ))  
          console.log(response.data)
  // ??? 
    },
    onRowClick(params) {
        this.dialog=true
     // 비어있는 editedItem에다가 백_셀렉트한 DB값을 넣어줘야!
     
    //  this.editedItem.id = params.row.id
    //  this.editedItem.name = params.row.name
    //  this.editedItem.age = params.row.age
    //  this.editedItem.city = params.row.city
     // console.log(this.editedItem)
      
    // params.row - row object 
    // params.pageIndex - index of this row on the current page.
    // params.selected - if selection is enabled this argument 
    // indicates selected or not
    // params.event - click event
  },

  save(editedItem){
     this.dialog=false
     const Id = editedItem.id;

      for(let i=0 ; i<this.rows.length;i++){
         if(this.rows[i].id==Id){
         this.rows[i].name=editedItem.name
         this.rows[i].age=editedItem.age
         this.rows[i].city=editedItem.city
         break;
         }
      }
     
  },
  dele(editedItem){
     this.dialog=false
     this.rows.splice(editedItem.id , 1)
/* for(let i=0 ; i<this.rows.length;i++){
         if(this.rows[i].id==Id){
         break;
         }
      } */

  }

}
}
</script>