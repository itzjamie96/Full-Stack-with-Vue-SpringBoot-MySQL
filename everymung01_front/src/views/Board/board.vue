<template>
<v-app>
  <v-row justify="center">
     <v-col>
    <vue-good-table
    
    :columns="columns"
    :rows="rows"
    @on-row-click="onRowClick"
  
    max-height="500px"
    :line-numbers="true"
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
        setCurrentPage: 1,
        nextLabel: 'next',
        prevLabel: 'prev',
        rowsPerPageLabel: 'Rows per page',
        ofLabel: 'of',
        pageLabel: 'page', // for 'pages' mode
        allLabel: 'All',
  }"

>  
  <!-- <template slot="table-row" slot-scope="props">
    <span v-if="props.column.field == 'depth'">
      <span v-if="props.row.depth==1" >
        <span style="font-weight: bold; color: blue;"> {{props.row.depth}} </span>
      </span> 
    </span>
    <span v-else>
      {{props.formattedRow[props.column.field]}}
    </span>
  </template> -->
</vue-good-table>

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
                <v-text-field  label="제목" v-model=editedItem.title  required ></v-text-field>
                <v-text-field  label="작성자" v-model="editedItem.userName" required readonly="readonly"></v-text-field>
                <v-text-field  label="작성일" v-model="editedItem.boardDate" required readonly="readonly" ></v-text-field>
              </v-col>  <!-- v-vind:value= 우항에 오는 변수명에 따옴표를 써줘도 되고 안써줘도 됨. 둘 다 가능. -->
                        
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
      
          <v-btn color="blue darken-1" text @click="reply()">답글달기</v-btn>
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
import router from '../../router/index'
import {mapState,mapActions} from "vuex"
export default {
  components: {
      VueGoodTable
  },
  computed: {
        ...mapState(["isLogin","userInfo","isLoginError","role"])
  },
  data(){
    return {
      dialog: false,
      // 상세보기_테이블의 행에 들어있는 컬럼값들을 여기에 저장해서, 엑시오스로 컨트롤러에 보냄
      editedItem:{
           boardNo: '',
           userNo:'',
           title: 'lsm',
           user: 'lsm',
           content:'',
           boardDate: '',
           hits: '',
           
       },

     // 삭제하기_
     editidItemIndex: '', // 테이블 행마다의 index번호
  
     // 수정하기_
     updatedItem:{
          boardNo: '',
          title: 'lsm',
          user: 'lsm',
          content:'',
          boardDate: '',
          hits: '',
     },
     // 답글달기
     BoardVO:{
          boardNo: '',
          title:'',
          content:'',
          userNo: '',
          boardDate: '',
          userName: '',                  
          hits:'',       
          depth:'',
          groupNo:'',
     },
     // 전체조회 테이블의 컬럼
     columns: [
       /* {
           label: '글번호',
           field: 'boardNo',
           type: 'number'
       }, */
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
      //백단에서 select문을 통해 받아온 title값을 자바스크립트로 함수로 답글형 제목으로 변경시키기 위한 첫 단계가 depth값 hidden으로 가져오기!  
      //  { 
      //     label: 'depth',
      //     field: 'depth',
      //     hidden: false,
      //  },
     ],
     // 전체조회 테이블의 행
     rows: [
        {
           boardNo: '',
           userNo:'',
           title: 'asdf',
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
  // 첫 전체 조회 화면_자동으로 뜨게 하기!
  created(){ //현재 컴포넌트가 생성되자 마자 initialize를 수행하라는 의미
        this.initialize() 
  },
  methods: {
    //### 전체목록 조회(R) ###
      initialize(){//DB와 연동해서 게시판 목록을 전부 가져옴
            axios.get('http://localhost:1234/showAllBoards')
            .then( response =>(
            //@@@@@@@@@@@@@@@@@@@@@@@
            this.rows = response.data//table row로 보여질 객체에 DB에서 받은 데이터를 넣어줌   
            //@@@@@@@@@@@@@@@@@@@@@@@
            //console.log("전체조회_"+this.rows)
            ))           
      },
      //### 상세보기(R) ### 
      onRowClick(params) {
          this.dialog=true
          // 비어있는 editedItem에다가 DB에서 SELECT한 결과값을 넣어줘야!
          this.editedItem.boardNo = params.row.boardNo
          console.log("this.editedItem.boardNo ==="+this.editedItem.boardNo)
          this.editedItem.userNo = params.row.userNo
          this.editedItem.title = params.row.title
          this.editedItem.userName = params.row.userName
          console.log("상세보기_"+this.editedItem.userName)
          this.editedItem.boardDate = params.row.boardDate
          this.editedItem.content = params.row.content
          this.editedItem.depth = params.row.depth
          this.editedItem.groupNo = params.row.groupNo // 여기까진 상세보기 기능.
        // params.row - row object 
        // params.pageIndex - index of this row on the current page.
        // params.selected - if selection is enabled this argument 
        // indicates selected or not
        // params.event - click event
        //console.log(params.pageIndex+"=== params.pageIndex")
        //#####################################
        this.editidItemIndex = params.pageIndex  // params.pageIndex를 알아채기 전까지 엄청 뻘짓했음.. ㅂㄷㅂㄷ;
      //#####################################
        //console.log(this.editidItemIndex+"=== editidItemIndex")
      },
      // 수정하기(U)
      update(updatedItem,editedItem){
          console.log("update_edtiedItem.userNo="+this.editedItem.userNo)
          console.log("userInfo_userNo="+this.userInfo.userNo)
          if(this.editedItem.userNo !== this.userInfo.userNo){
            alert("다른 회원의 글은 수정할 수 없습니다.")
            this.dialog=false
            return false
            //다이얼로그만 다시 리로딩하고 싶은데? -> 실패 
          }
              //### 
          this.updatedItem.boardNo = this.editedItem.boardNo // ### boardNo 없으면, update 쿼리문 수행불가(boardNo 조건절이 필요해서)
          console.log( "update_this.updatedItem.boardNo="+this.updatedItem.boardNo) 
          this.updatedItem.title = this.editedItem.title                     
          this.updatedItem.content = this.editedItem.content                                                             
          axios.post ('http://localhost:1234/update',this.updatedItem)
          .then( res =>{
            //this.updatedItem = res.data  ---> 에러발생: TypeError: Cannot create property 'title' on string '' at VueComponent.update 
          }),  
          console.log("board.vue_update()"+this.updatedItem)
          // 입력칸 제출후 깨끗하게 만드는 기능코드 
          alert("수정이 완료되었습니다.")
          this.dialog=false
          //### 전체조회하기 
          history.go(0) 
      },
      // 삭제하기(D)
      dele(){
        // if(this.editedItem.userNo !== this.userInfo.userNo){
        //     alert("다른 회원의 글은 삭제할 수 없습니다.")
        //     this.dialog=false
        //     return false //밑으로 안내려가고 여기서 함수 종료시켜버림.
        //     /* 이런 방법도 있네~        
        //     <div v-if="isLogin===false" class="mt-3">
        //       <v-btn text router :to="{name:'signup'}" exact>회원가입</v-btn>
        //       <v-btn text router :to="{name:'signin'}" exact>로그인</v-btn>
        //     </div>
        //     */
        // }
        console.log("삭제_this.editidItemIndex ="+this.editidItemIndex  )
        console.log("삭제_this.editedItem.boardNo ="+this.editedItem.boardNo)
        
        axios.get('http://localhost:1234/delete/'+this.editedItem.boardNo).then(response => this.rows.splice(this.editidItemIndex , 1))
        
        this.dialog=false
      },
       reply(BoardVO,editedItem){
          //### 
          console.log("this.editedItem.boardNo ==="+this.editedItem.boardNo)
          // console.log("this.editedItem.boardNo ==="+this.editedItem.boardNo)
          this.BoardVO.boardNo=this.editedItem.boardNo
          console.log("boardNo 확인:"+this.BoardVO.boardNo)
          // console.log("boardNo 확인:"+this.BoardVO.boardNo)
          this.BoardVO.title=this.editedItem.title
          this.BoardVO.content=this.editedItem.content
          this.BoardVO.userNo = this.userInfo.userNo
          this.BoardVO.boardDate = new Date() // DB와 데이터타입을 맞춰줘야돼서 Date() 
          this.BoardVO.userName = this.userInfo.userName                     
          this.BoardVO.hits=0        
          this.BoardVO.depth=0
          this.BoardVO.groupNo=this.editedItem.groupNo//back단에서 하려고했는데, front단에서 처리했음. 
          console.log("reply()_this.editedItem.title="+this.editedItem.title)
          console.log("reply()_this.BoardVO.title="+this.BoardVO.title)
          console.log("reply()_this.BoardVO.boardNO="+this.BoardVO.boardNo)
          // console.log("reply()_this.editedItem.title="+this.editedItem.title)
          // console.log("reply()_this.BoardVO.title="+this.BoardVO.title)
          // console.log("reply()_this.BoardVO.boardNO="+this.BoardVO.boardNo)
          // if(this.BoardVO.depth==0){
          //   this.BoardVO.groupNo=0
          // }
          /*
          else {
            this.BoardVO.groupNo=boardNo가 넘어와야함
          }
          */
          //BoardNo값을 안넣어서 보내면 스프링쪽에선 boardNo=0이라고 찍히더라. 
            
            axios.post ('http://localhost:1234/reply',this.BoardVO) // 여기선 객체 던져주는 식이네 
           .then( res =>{
            this.initialize
                this.BoardVO = res.data
                console.log(this.BoardVO)
           })
           history.go(0)
         }
  },
}
</script>