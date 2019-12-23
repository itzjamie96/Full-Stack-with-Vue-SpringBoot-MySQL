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

    <!--  v-on="greet()"  이렇게 하면 새로고침만 해도 greet()가 실행되고, 클릭만 해도 실행되고, 뭘 해도 다 실행되는 느낌?;;  -->

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
                <v-text-field class="attrRemove" label="제목" v-model="editedItem.title" required readonly="readonly"></v-text-field>
                <v-text-field class="attrRemove" label="작성자" v-model="editedItem.userName" required readonly="readonly"></v-text-field>
                <v-text-field class="attrRemove" label="작성일" v-model="editedItem.boardDate" required readonly="readonly"></v-text-field>
              </v-col>
                        
              <v-textarea
                label="내용을 입력해주세요"
                outlined
                name="input-7-4"
                v-model="editedItem.content"
                required
                class="attrRemove"
                readonly="readonly"
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

     editidItemIndex: '',

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
     rows: [
        {
           boardNo: '',
           userNo:'',
           title: 'lsm',
           userName: 'lsm',
           content:'',
           boardDate: '',
           hits: '',
           depth: '', // column에 없어서 에러 뜰 줄 알았는데, 안뜨네. 
           groupNo: '',
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

    }; // return의 } 
  },//data의 }

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
          //console.log(response.data)
          ))  
          // 이렇게 해주면, 상세보기 화면이 나올줄 알았는데 안나오네;; rows에 있는 데이터를 deiteditem에 집어넣어줬는데 왜 그럴까? 
          //this.editedItem = this.rows          

    },
    //### 상세보기(R) ### 
    onRowClick(params) {
        this.dialog=true
        // 비어있는 editedItem에다가 DB에서 SELECT한 결과값을 넣어줘야!
        this.editedItem.boardNo = params.row.boardNo
        this.editedItem.userNo = params.row.userNo
        this.editedItem.title = params.row.title
        this.editedItem.userName = params.row.userName
        this.editedItem.boardDate = params.row.boardDate
        this.editedItem.content = params.row.content
        this.editedItem.depth = params.row.depth
        this.editedItem.groupNo = params.row.groupNo // 여기까진 상세보기 기능.
                      // params.row객체의 값을 테이블의 범위 밖에서는 사용 못한다는 잘못된 생각 때문에 아래와 같은 삽질을 함..
                        //console.log(this.editedItem.boardNo+"_onRowClick")

                        // this.dele(this.editedItem)  // 이 함수는 온클릭시에 작동하도록 설계된 함수니까, 삭제버튼 누르면 작동하지 않을까? row클릭했다고 바로 작동은 안하고?
                        // 실패.. 그냥 바로 삭제되버린다.  


                        // if(this.editedItem.boardNo===1){ // delete버튼을 누르면<을 표현하고 싶은데 
                        //   console.log("greet()되나?")
                        //   this.greet()  // this를 넣어주니까 greet is not defined가 안뜨네!! 오호~ this의 역할~
                        // }
       
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
      const a = document.getElementsByClassName("attrRemove")
      console.log(a)
      //a.readonly=false
      //a.removeAttribute("readonly=readonly")
      
      

      // for(let i=0 ; i<this.rows.length;i++){
      //    if(this.rows[i].id==Id){
      //    this.rows[i].name=editedItem.name
      //    this.rows[i].age=editedItem.age
      //    this.rows[i].city=editedItem.city
      //    break;
      //    }
      // }
  },
  // 수정하기_저장(U)
  save(editedItem){
     this.dialog=false
      //  const Id = editedItem.id;

      // for(let i=0 ; i<this.rows.length;i++){
      //    if(this.rows[i].id==Id){
      //    this.rows[i].name=editedItem.name
      //    this.rows[i].age=editedItem.age
      //    this.rows[i].city=editedItem.city
      //    break;
      //    }
      // }
  },

  // 삭제하기(D)
  dele(){
     this.dialog=false
                            // 인덱스로 정확한 삭제를 하기 위해 거쳤던 뻘짓..(boardNo로 삭제하려고 했으나, splice함수는 index로 삭제하는 방식이라서 아주 헤맴..)
                                  //console.log(this.rows.boardNo) => 에러> undefined   
                                  // console.log(this.editedItem.boardNo+"_dele(editedItem)")
                                  //const reverseIndex = this.rows[this.rows.length - this.editedItem.boardNo -1].boardNo
                                  //console.log(reverseIndex)
                                  //console.log(this.rows[this.rows.length - this.editedItem.boardNo -1].boardNo) // 맨위에서부터 row[0] (boardNo=5일 때)
                                  //console.log(reverseIndex+"___reverseIndex_dele소속")
                                  //3번일땐 게시글2번이 삭제되네. 2번일때 게시글3번이 삭제되고, 1번일 때 게시글 4번이 삭제되네?;
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