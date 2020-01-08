<template>
<v-container>
  <v-row justify="center" >
  <v-col>
      <!-- 테이블 -->
      <vue-good-table
      :columns="columns"
      :rows ="rows"
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
          nextLabel: '다음',
          prevLabel: '이전',
          rowsPerPageLabel: '페이지당 게시물 개수', 
          ofLabel: 'of',
          pageLabel: 'page', 
          allLabel: 'All',
    }"
    >  </vue-good-table>
    <v-spacer></v-spacer>
    <!-- 글쓰기 버튼 -->
    <div v-if="this.isLogin==false">
      <v-btn  v-on:click="alert()" @click="$router.push({path: '/signin'})">글쓰기</v-btn>
    </div>
    <div v-if="this.isLogin==true" >
      <v-btn  @click="writeClick()" >글쓰기</v-btn>
    </div>
 
   <!-- [상세보기_다이얼로그] -->
   <v-dialog v-model="dialog_detail" persistent max-width="600px">
      <template v-slot:activator="{ on }">
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">상세보기</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field  label="제목" v-model=editedItem.title  required maxlength="240" :readonly=true></v-text-field> 
                <v-text-field  label="작성자" v-model="editedItem.userName" required :readonly=true></v-text-field>
                <v-text-field  label="작성일" v-model="editedItem.boardDate" required readonly="readonly" ></v-text-field>
              </v-col>  
                        
              <v-textarea
                label="내용을 입력해주세요"
                outlined
                name="input-7-4"
                v-model="editedItem.content"
                maxlength="1000"
                required
                :readonly=updateTrig
                auto-grow
              ></v-textarea>
            </v-row>
          </v-container>
        </v-card-text>
         <!-- 상세보기-다이얼로그_버튼목록 -->
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="close()">닫기</v-btn>
          <div v-if="isLogin==true && userInfo.userNo==editedItem.userNo">
            <v-btn href="javascript:;" color="blue darken-1" text @click="updateClick()">수정</v-btn>
            <v-btn color="blue darken-1" text @click="dele()">삭제</v-btn>
          </div>
        </v-card-actions>
      </v-card>
    </v-dialog>
 
       <!-- [수정하기_다이얼로그] -->
   <v-dialog v-model="dialog_update" persistent max-width="600px">
      <template v-slot:activator="{ on }">
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">{{updateName}}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field  label="제목" v-model=editedItem.title  required maxlength="240" disabled></v-text-field>
                <v-text-field  label="작성자" v-model="editedItem.userName" required disabled></v-text-field>
                <v-text-field  label="작성일" v-model="editedItem.boardDate" required  disabled ></v-text-field>
              </v-col>  
                        
              <v-textarea
                id="focus_textarea"
                label="내용을 입력해주세요"
                outlined
                name="input-7-4"
                v-model="editedItem.content"
                maxlength="1000"
                required
                
                auto-grow
              ></v-textarea> 
            </v-row>
          </v-container>
        </v-card-text>
         <!-- 수정하기-다이얼로그_버튼목록 -->
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="close_update()">닫기</v-btn>
          <div v-if="isLogin==true && userInfo.userNo==editedItem.userNo">
            <v-btn href="javascript:;" color="blue darken-1" text @click="update()">수정</v-btn>
            <v-btn color="blue darken-1" text @click="dele()">삭제</v-btn>
          </div>
        </v-card-actions> 
      </v-card>
    </v-dialog>
 
   
    <!-- [글쓰기 다이얼로그]--> 
    <v-dialog v-model="dialog_write" persistent max-width="600px">
        <template v-slot:activator="{ on }">
        </template>
        <v-card>
          <v-card-title>
            <span class="headline">글쓰기</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <v-text-field label="제목" v-model="BoardVO.title"  required maxlength="240" ></v-text-field>
                  <v-text-field  label="작성자" v-model="BoardVO.userName" required readonly="readonly">
                  </v-text-field>  
                </v-col>  

                <v-textarea
                  label="Content"
                  outlined
                  name="input-7-4"
                  v-model="BoardVO.content"
                  maxlength="1000"
                  required
                  auto-grow>
                </v-textarea>
              </v-row>
            </v-container>
          </v-card-text>
          <!-- 글쓰기-다이얼로그_버튼목록 -->
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
            :disabled="BoardVO.title ===''"
             color="blue darken-1" text @click="dbClickProtectedAdd()">제출</v-btn>
            <v-btn color="blue darken-1" text @click="dialog_write = false">닫기</v-btn>
          </v-card-actions>
        </v-card>
    </v-dialog>  
  </v-col>
  </v-row>
</v-container>
</template>
 
 
<script>
    import 'vue-good-table/dist/vue-good-table.css'
    import { VueGoodTable } from 'vue-good-table'
    import axios from 'axios'
    import router from '../../router/index'
    import {mapState,mapActions} from "vuex"
    export default {
      components: {
          VueGoodTable,
      },
      computed: {
            ...mapState(["isLogin","userInfo","isLoginError","role"])
      },
  data(){
    return {
      updateName:'수정하기',
      updateTrig:true,
      dialog_detail: false,
      dialog_update:false,
      dialog_write: false,
      
      // 상세보기 + 수정하기에서 사용 
      editedItem:{
           boardNo: '',
           userNo:'',
           title: '',
           user: '',
           content:'',
           boardDate: '',
           hits: '',
           status:'',
       },
     // 삭제하기
     editidItemIndex: '', // 테이블 행마다의 index번호
 
     //글쓰기 & 답글달기 
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
          status:'',
     },
     // 전체조회 테이블의 컬럼
     columns: [
       {
           label: '제목',
           field: 'title',
           width: '700px',
           thClass: 'text-center',
       },
       {
           label: '작성자',
           field: 'userName',
           width: '100px',
       },       
       {
           label: '조회수',
           field: 'hits',
           width: '80px',
           tdClass: 'text-center',
          
       },
       {
           label: '작성일',
           field: 'boardDate',
           type: 'date',
           dateInputFormat: 'yyyy-MM-dd HH:mm:ss',
           dateOutputFormat: 'yyyy-MM-dd HH:mm',
           thClass: 'text-left', 
           tdClass: 'text-left',
           width: '180px',
       }, 
     ],

     //조회수
     indexForHits: 0,

     // 전체조회 테이블의 행
     rows: [
        {
           boardNo: '',
           userNo:'',
           title: '',
           userName: '',
           content:'',
           boardDate: undefined,
           hits: '',
           depth: '', 
           groupNo: '',
        },
     ],
     doubleSubmitFlag:false
    }; 
  },

  // 첫 전체 조회 화면_자동으로 뜨게 하기!
  created(){
        if(this.isLogin === true)
        {this.BoardVO.userName=this.userInfo.userName}
        else
        {this.BoardVO.userName=''}
        setTimeout(this.showAllBoards(),1000);
  },
  methods: {
        //전체목록 조회(R)
        showAllBoards(){//DB와 연동해서 게시판 목록을 전부 가져옴
              axios.get('http://localhost:1234/showAllBoards')
              .then( res =>{ 
              this.rows = res.data
              })              
        },
        //글쓰기 버튼(Create)
        writeClick(){
            this.BoardVO.userName=this.userInfo.userName
            this.dialog_write=true
        },      
        //제출 버튼_더블클릭-중복입력-방지
        dbClickProtectedAdd(){
            
            if(this.doubleSubmitCheck()) {
             alert("중복 제출하실 수 없습니다. 기존 입력정보와 다르게 작성해주세요")
            return 
            }else{
              this.add(); 
            }           
            window.location.href="http://localhost:8080/board"       
       },
        
      //제출 버튼_중복방지처리 X
      add(){
            this.BoardVO.userNo = this.userInfo.userNo
            this.BoardVO.boardDate = new Date() 
            this.BoardVO.userName = this.userInfo.userName    
            this.BoardVO.title = this.BoardVO.title 
            this.BoardVO.content = this.BoardVO.content                    
            if(this.BoardVO.title===''){ 
              this.BoardVO.userName = this.userInfo.userName
            }   
            axios.post ('http://localhost:1234/add',this.BoardVO) // 여기선 객체 던져주는 식이네 
           .then( res =>{
            this.BoardVO = res.data             
           })      
        },
 
 
      //상세보기(Read) & //조회수-처리 시작부분
      onRowClick(params) {     
          //조회수 처리하기 위해 row index 만들기
          for ( var i = 0; i < this.rows.length; i++ ) {
            if(this.rows[i].boardNo==params.row.boardNo){
              this.indexForHits = i 
            }
          }        
        
          // 비어있는 editedItem에다가 DB에서 SELECT한 결과값을 넣어주기!
          this.editedItem.boardNo = params.row.boardNo          
          this.editedItem.userNo = params.row.userNo
          this.editedItem.title = params.row.title
          this.editedItem.userName = params.row.userName
          this.editedItem.boardDate = params.row.boardDate
          this.editedItem.content = params.row.content
          this.editedItem.hits = params.row.hits
          this.editedItem.depth = params.row.depth
          this.editedItem.groupNo = params.row.groupNo 
          this.editedItem.status= params.row.status //여기까진 상세보기 기능
          this.dialog_detail=true

         //조회 메소드 만들기              
         this.updateHits()
      },
 
      //수정-버튼(Update) 
      updateClick(){
          this.dialog_update=true
          this.updateTrig=false
      },
      //2차 수정(Update)
      update(){    
          axios.post ('http://localhost:1234/update',this.editedItem)
          .then( res =>{
          }),  
          alert("수정이 완료되었습니다.")
          this.dialog_detail=false
          history.go(0) 
  
      },
      //삭제하기(Delete)
      dele(){
        axios.get('http://localhost:1234/deleteBoardByUser/'+this.editedItem.groupNo) 
        .then(response =>{
        })
         this.dialog_detail=false
         history.go(0)
      },
      //상세보기-닫기버튼
      close(){
        this.dialog_detail=false
      },
      //수정하기-닫기버튼
      close_update(){
        this.dialog_detail=false
        this.dialog_update=false

        if(this.updateName==='상세보기'){
          this.dialog_detail=false
        }else{
          this.updateTrig = true
          this.updateName = '상세보기'
          //여기에 confirm을 써주면 될듯! => "수정중인데 진짜 나갈거냐? Yes or No"
            if(confirm("수정중에 나가시면 입력하신 정보가 저장되지 않을 수 있습니다.        정말로 나가시겠습니까?")){
              this.dialog_detail=false
            }else{
              this.dialog_detail=true
            }
        }
        
      },
      //더블클릭-중복입력-방지       
      doubleSubmitCheck(){
        if(this.doubleSubmitFlag){
            console.log("if(doubleSubmitFlag=true)로 실행돼서 메소드가 종료돼버리므로 해당 버튼의 중복클릭->중복내용삽입이 불가능!")
            return this.doubleSubmitFlag;
        }else {
            this.doubleSubmitFlag = true;
            //console.log("else(doubleSubmitFlag=false)로 실행됨")
            return false;   
        }
     },
 
       //답글달기 버튼(Create)
       reply(BoardVO,editedItem){       
          this.BoardVO.boardNo=this.editedItem.boardNo
          this.BoardVO.title=this.editedItem.title
          this.BoardVO.content=this.editedItem.content
          this.BoardVO.userNo = this.userInfo.userNo
          this.BoardVO.boardDate = new Date() 
          this.BoardVO.userName = this.userInfo.userName                     
          this.BoardVO.groupNo=this.editedItem.groupNo
          this.BoardVO.status=true

          axios.post ('http://localhost:1234/insertReply',this.BoardVO)  
          .then( res =>{
                this.BoardVO = res.data
           })
       
          this.BoardVO.title= "ㄴRE "+this.BoardVO.title
          window.location.href="http://localhost:8080/board"
       },

     //답글달기(Create)_더블클릭 중복입력 방지O 
     dbClickProtectedReply(BoardVO,editedItem){
          console.log("dbClickProtectedReply() 호출")
         if(this.doubleSubmitCheck()) {
              return 
          }
          console.log(this.doubleSubmitCheck())
          console.log(this.doubleSubmitFlag)
          this.reply();
     },
     
    //조회수 증가(Update)
    updateHits(){
      
      //indexForHits            
      axios.post('http://localhost:1234/updateHits',this.editedItem).then(res=>{
        this.rows[this.indexForHits].hits = res.data // res.data => 제대로 이해못함.  
      console.log("res" +"::"+res) // res = 컨트롤러에서 return값!!!
      console.log("res.data"+"::"+res.data)
      })
    },
    //비로그인 상태_글쓰기 클릭시_안내문 
    alert(){
      alert("로그인을 하셔야만 글을 쓰실 수 있습니다!")
    },
 
  },
}
</script>
 
