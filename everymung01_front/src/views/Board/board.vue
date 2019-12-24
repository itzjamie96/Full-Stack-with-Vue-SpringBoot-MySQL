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
    :pagination-options="{
        enabled: true,
        mode: 'records',
        perPage: 9,
        position: 'bottom',
        perPageDropdown: [3, 7, 9],
        dropdownAllowAll: false,
        setCurrentPage: 2,
        nextLabel: 'next',
        prevLabel: 'prev',
        rowsPerPageLabel: 'Rows per page',
        ofLabel: 'of',
        pageLabel: 'page', // for 'pages' mode
        allLabel: 'All',
  }"
>  </vue-good-table>

    <!--  v-on="greet()" 이렇게 하면 새로고침만 해도 greet()가 실행되고, 클릭만 해도 실행되고, 뭘 해도 다 실행되는 느낌?;;  -->

<v-btn @click="$router.push({path: '/write'})">글쓰기 {{editidItemIndex}}</v-btn>
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
                <v-text-field  label="제목" v-model="editedItem.title" required ></v-text-field>
                <v-text-field  label="작성자" v-model="editedItem.userName" required ></v-text-field>
                <v-text-field  label="작성일" v-model="editedItem.boardDate" required ></v-text-field>
              </v-col>
                        
              <v-textarea
                label="내용을 입력해주세요"
                outlined
                name="input-7-4"
                v-model="editedItem.content"
                required
                class="attrRemove"
            
              ></v-textarea>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">확인</v-btn>
          <v-btn color="blue darken-1" text @click="dele()">삭제</v-btn>
          <v-btn color="blue darken-1" text @click="update()">수정</v-btn>
        </v-card-actions> <!-- @click="$router.push({path: '/update'}) -->
      </v-card>
    </v-dialog>
    <!-- ??????????? dele()에 매개변수 안넣어줘도... this.으로 가져다 쓰면 돼서, 에러가 안난다고?;;;; -->
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

     editidItemIndex: '', // 테이블 행마다의 index번호
  
     updatedItem:{
          boardNo: '',
          userNo:'',
          title: 'lsm',
          user: 'lsm',
          content:'',
          boardDate: '',
          hits: '',
     },
     // 전체조회 테이블의 컬럼
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
           field: 'userName'
       },       
       {
           label: '조회수',
           field: 'hits',
           type: 'number',
       },
       {
           label: '작성일',
           field: 'boardDate',
          //  type: 'date',
          //  dateInputFormat: 'yyyy-MM-dd',
          //  dateOutputFormat: 'yy-MM-dd',
       }, // MMM Do yy

     ],
     // 전체조회 테이블의 행
     rows: [
        {
           boardNo: '',
           userNo:'',
           title: '',
           userName: '',
           content:'',
           boardDate: '',
           hits: '',
           depth: '', 
           groupNo: '',
        },
     ],

    }; 
  },

  created(){ //현재 컴포넌트가 생성되자 마자 initialize를 수행하라는 의미
        this.initialize()
    },
  methods: {
    //### 전체목록 조회(R) ###
    initialize(){//DB와 연동해서 게시판 목록을 전부 가져옴
           axios.get('http://localhost:1234/showAllBoards')
          .then( response =>(
          //@@@@@@@@@@@@@@@@@@@@@@@
          this.rows = response.data //table row로 보여질 객체에 DB에서 받은 데이터를 넣어줌   
          //@@@@@@@@@@@@@@@@@@@@@@@
          ))           

    },
    //### 상세보기(R) ### 
    onRowClick(params) {
        this.dialog=true
        // 비어있는 editedItem에다가 DB에서 SELECT한 결과값을 넣어줘야!
        this.editedItem.boardNo = params.row.boardNo
        this.editedItem.userNo = params.row.userNo
        this.editedItem.title = params.row.title
        this.editedItem.userName = params.row.userName
        console.log(this.editedItem.userName)
        this.editedItem.boardDate = params.row.boardDate
        this.editedItem.content = params.row.content
        this.editedItem.depth = params.row.depth
        this.editedItem.groupNo = params.row.groupNo // 여기까진 상세보기 기능.

      // params.row - row object 
      // params.pageIndex - index of this row on the current page.
      // params.selected - if selection is enabled this argument 
      // indicates selected or not
      // params.event - click event

      console.log(params.pageIndex+"=== params.pageIndex")
      //#####################################
      this.editidItemIndex = params.pageIndex  // params.pageIndex를 알아채기 전까지 엄청 뻘짓했음.. ㅂㄷㅂㄷ;
     //#####################################

      console.log(this.editidItemIndex+"=== editidItemIndex")
  },

  // 수정하기(U)
  update(editedItem){
      this.updatedItem.content = this.editedItem.content
      console.log(this.boardNo)

      //const a = document.getElementsByClassName("attrRemove")
      //a.readonly=false
      //a.removeAttribute("readonly=readonly")
      
  },
  // 수정하기_저장(U)
  save(editedItem){
     this.dialog=false

  },

  // 삭제하기(D)
  dele(){
     this.dialog=false

     console.log(this.editidItemIndex + "=== dele()")
     console.log(this.editedItem.boardNo + "=== boardNo")
     
     axios.get('http://localhost:1234/delete/'+this.editedItem.boardNo).then(response => this.rows.splice(this.editidItemIndex , 1))
          //(console.log("스프링부트_delete_성공"))

 

/* for(let i=0 ; i<this.rows.length;i++){
         if(this.rows[i].id==Id){
         break;
         }
      } */

  }

}
}
</script>