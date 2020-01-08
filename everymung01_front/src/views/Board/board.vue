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
          pageLabel: 'page', // for 'pages' mode
          allLabel: 'All',
    }"
    >  </vue-good-table>
 
    <!-- 글쓰기 버튼 -->
    <div v-if="this.isLogin==false">
      <v-btn color="red lighten-1" dark v-on:click="alert()" @click="$router.push({path: '/signin'})">글쓰기</v-btn>
    </div>
    <div v-if="this.isLogin==true" >
      <v-btn color="red lighten-1" dark  @click="writeClick()" >글쓰기</v-btn>
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
                <v-text-field  label="제목" v-model=editedItem.title  required maxlength="240" :readonly=true></v-text-field> <!-- 50-6하는 이유:: 6(ㄴRE:의 글자수) -->
                <v-text-field  label="작성자" v-model="editedItem.userName" required :readonly=true></v-text-field>
                  <!-- ### editedItem.userName(O), this.userinfo.userName(X) => userName of null에러 뜸.### -->
                  <!-- v-vind 이용해서 data에 선언된 변수 updateTrig 의 값인 true 연결해주기   -->
                <v-text-field  label="작성일" v-model="editedItem.boardDate" required readonly="readonly" ></v-text-field>
              </v-col>  <!-- v-vind:value= 우항에 오는 변수명에 따옴표를 써줘도 되고 안써줘도 됨. 둘 다 가능. -->
                        
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
            <!-- <v-btn color="blue darken-1" text @click="dbClickProtectedReply()">답글달기</v-btn> -->
          </div>
        </v-card-actions> <!-- @click="$router.push({path: '/update'}) -->
      </v-card>
    </v-dialog>
 
       <!-- [수정하기_다이얼로그] -->
   <v-dialog v-model="dialog_update" persistent max-width="600px">
      <template v-slot:activator="{ on }">
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">수정하기</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field  label="제목" v-model=editedItem.title  required maxlength="240" disabled></v-text-field> <!-- 50-6하는 이유:: 6(ㄴRE:의 글자수) -->
                <v-text-field  label="작성자" v-model="editedItem.userName" required disabled></v-text-field>
                  <!-- ### editedItem.userName(O), this.userinfo.userName(X) => userName of null에러 뜸.### -->
                  <!-- v-vind 이용해서 data에 선언된 변수 updateTrig 의 값인 true 연결해주기   -->
                <v-text-field  label="작성일" v-model="editedItem.boardDate" required  disabled ></v-text-field>
              </v-col>  <!-- v-vind:value= 우항에 오는 변수명에 따옴표를 써줘도 되고 안써줘도 됨. 둘 다 가능. -->
                        
              <v-textarea
                id="focus_textarea"
                label="내용을 입력해주세요"
                outlined
                name="input-7-4"
                v-model="editedItem.content"
                maxlength="1000"
                required
                
                auto-grow
              ></v-textarea> <!-- :readonly=updateTrig // v-model=editedItem.content-->
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
            <!-- <v-btn color="blue darken-1" text @click="dbClickProtectedReply()">답글달기</v-btn> -->
          </div>
        </v-card-actions> <!-- @click="$router.push({path: '/update'}) -->
      </v-card>
    </v-dialog>
 
   
    <!-- [글쓰기 다이얼로그] 
     // 여기서 비로그인시 문의게시글 조회 에러 발생_
     // <에러명> "TypeError: Cannot read property 'userName' of null" 
     // <해결함> this.userInfo.userName을 this.BoardVO.userName으로 바꿔주니까 해결됨. -->
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
                <!-- v-vind:value= 우항에 오는 변수명에 따옴표를 써줘도 되고 안써줘도 됨. 둘 다 가능. -->
          
                <v-textarea
                  label="Content"
                  outlined
                  name="input-7-4"
                  v-model="BoardVO.content"
                  maxlength="1000"
                  required
                  auto-grow
                ></v-textarea>
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
          </v-card-actions> <!-- @click="$router.push({path: '/update'}) -->
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
          //userName: 에다가 this.userInfo.userName를 써주면,  
          /*"TypeError: Cannot read property 'userName' of undefined"
          *TypeError: Cannot read property 'userName' of undefined
          *Property or method "columns" is not defined on the instance but referenced during render
          *Property or method "BoardVO" is not defined on the instance but referenced during render
          *TypeError: Cannot read property 'title' of undefined
          *Cannot read property 'title' of undefined
          *한번에 이 에러들이 모두 발생.  
          */               
          hits:'',       
          depth:'',
          groupNo:'',
          status:'',
     },
     // 전체조회 테이블의 컬럼
     columns: [
      //   {
      //      label: '글번호',
      //      field: 'boardNo',
      //      width: '100px',
      //      //thClass: 'text-center',
      //      //tdClass: 'text-center',
      //  },
       {
           label: '제목',
           field: 'title',
           width: '700px',
           thClass: 'text-center',
           //tdClass: 'text-center',
       },
       {
           label: '작성자',
           field: 'userName',
           width: '100px',
           //thClass: 'text-center',           
           //tdClass: 'text-center',
       },       
       {
           label: '조회수',
           field: 'hits',
           width: '80px',
          //  type: 'number',
          //thClass: 'text-center',          
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
       }, // MMM Do yy
       /* {
           label: 'status',
           field: 'status',
          //  type: 'boolean',
           hidden: true
       }, */
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
  created(){ //현재 컴포넌트가 생성되자 마자 initialize를 수행하라는 의미
        if(this.isLogin === true)
        {this.BoardVO.userName=this.userInfo.userName}
        else
        {this.BoardVO.userName=''}
        setTimeout(this.showAllBoards(),1000);
        //console.log(this.userInfo.userName)
        //###########에러해결 by상민_기발하다!##############
        
        
        //##################################################
        //console.log("this.userInfo.userNo == this.editedItem.userNo"+this.userInfo.userNo+"=="+this.editedItem.userNo) 
  },
  methods: {
      //수정버튼에서 updateTrig로 if/else 걸어서  true일땐 -> a함수 실행하고, false일땐->b함수 실행
      //### 전체목록 조회(R) ###
      showAllBoards(){//DB와 연동해서 게시판 목록을 전부 가져옴
            axios.get('http://localhost:1234/showAllBoards')
            .then( res =>{ 
              
            //@@@@@@@@@@@@@@@@@@@@@@@ 
            
            this.rows = res.data//table row로 보여질 객체에 DB에서 받은 데이터를 넣어줌   
              
            //@@@@@@@@@@@@@@@@@@@@@@@
            })          
            //alert("v-on: 작동여부 확인테스트") -> <v-btn>에서 2개의 onclick을 쓸 수 있단 걸 알게됨. 3개도 되는진 모름.
            //console.log("전체조회_"+this.rows)
           
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
              //this.showAllBoards() //이걸로 재조회가 안되네 
            }
            // console.log(this.doubleSubmitCheck())
            // console.log(this.doubleSubmitFlag)
          
            //글쓰기 
 
            //##################발표 전 반드시 포트번호 확인하고 바꿔줘야함############################## 
            
            window.location.href="http://localhost:8080/board"
            //######################################################################################## 
            
       },
        
      //제출 버튼_중복방지처리 X
      add(){
            //console.log("add()_this.userInfo.userNo="+this.userInfo.userNo)
            this.BoardVO.userNo = this.userInfo.userNo
            this.BoardVO.boardDate = new Date() // DB와 데이터타입을 맞춰줘야돼서 Date() 
            this.BoardVO.userName = this.userInfo.userName    
            this.BoardVO.title = this.BoardVO.title //v-model=this.BoardVO.title 
            this.BoardVO.content = this.BoardVO.content //v-model=this.BoardVO.title                    
            //BoardNo값을 안넣어서 보내면 스프링쪽에선 boardNo=0이라고 찍히더라. 
            console.log(this.BoardVO.title)
            if(this.BoardVO.title===''){ // === null로 할 땐 안먹었는데, ''로 하니까 되네. 
              //alert("값을 입력하라우~")
              this.BoardVO.userName = this.userInfo.userName
            }
            
            axios.post ('http://localhost:1234/add',this.BoardVO) // 여기선 객체 던져주는 식이네 
           .then( res =>{
             this.BoardVO = res.data             
           })
            // 입력칸 제출후 깨끗하게 만드는 기능코드 (글쓰기에서는 페이지이동하므로 필요가 없음)
            //this.BoardVO = this.default 
            //router.push({name:'Board'}) // 이유는 모르겠지만 확실한건 새로고침이 됐다가 말았다가 한다.. 거의 대부분 되다가 딱 1번 안되는 식. 
           
        },
 
 
      //### 상세보기(Read) ### + //### 조회수 처리 시작부분### 
      onRowClick(params) {     
          //this.rows[0].boardNo = 99999     \
          console.log("this.rows.length :: " + this.rows.length)
          
          //조회수 처리하기 위해 row index 만들기
          for ( var i = 0; i < this.rows.length; i++ ) {
            if(this.rows[i].boardNo==params.row.boardNo){
              this.indexForHits = i //조회수를 변경하기 위해, pageindex의 한계인 페이지 내에서만 인덱스 적용되는 문제를 보완해주는 row마다 index번호를 부여해줌 
            }
          }        
        
          //console("params.Index ::" + params.Index)
          // 비어있는 editedItem에다가 DB에서 SELECT한 결과값을 넣어주기!
          this.editedItem.boardNo = params.row.boardNo          
          this.editedItem.userNo = params.row.userNo
          //console.log(this.editedItem.userNo)
          this.editedItem.title = params.row.title
          this.editedItem.userName = params.row.userName
          //console.log("상세보기_"+this.editedItem.userName)
          this.editedItem.boardDate = params.row.boardDate
          this.editedItem.content = params.row.content
          this.editedItem.hits = params.row.hits
          this.editedItem.depth = params.row.depth
          this.editedItem.groupNo = params.row.groupNo // 여기까진 상세보기 기능.
          this.editedItem.status= params.row.status
        // params.row - row object 
        // params.pageIndex - index of this row on the current page.
        // params.selected - if selection is enabled this argument 
        // indicates selected or not
        // params.event - click event
         this.dialog_detail=true
        //재조회로 삭제하는 방법 쓰기 전에 인덱스로 삭제하는 법 쓸 때 사용하던 페이지인덱스 정보.
        //this.editidItemIndex = params.pageIndex  
         //조회 메소드 만들기              
         this.updateHits()
      },
 
      //수정-버튼(Update) 
      updateClick(){
          this.dialog_update=true
          this.updateTrig=false
          //document.getElementById("focus_textarea").focus()
        //   this.editedItem.content=this.lsm 
        //   if(this.updateTrig===true){//상세보기 모드일 때  
        //     this.lsm =this.editedItem.content
        //     console.log("this.editedItem.content ::" +this.editedItem.content)
        //     console.log("this.lsm ::" +this.lsm)
 
        //     this.editedItem.content = null
        //     console.log("this.editedItem.content ::" +this.editedItem.content)
        //     //수정_밑작업 
        //     this.updateName = '수정하기'
        //     this.updateTrig = false //수정가능 상태가 됨 
        //     document.getElementById("focus_textarea").focus()
              
        //       /* lsm !==this.editedItem.content&&this.editedItem.content.length!==0?this.update():
        //       alert("내용이 같거나 내용이 없습니다.") */
 
        //   }
         
        //  else if(this.updateTrig === false){// 수정모드일 때 
           
        //    console.log("lsm::"+this.lsm)
        //    console.log("this.editedItem.content ::" +this.editedItem.content)
        //    this.lsm !==this.editedItem.content && this.editedItem.content!==null
        //    &&this.editedItem.content !== '' && this.editedItem.content !== undefined?this.update():
        //     alert("내용이 같거나 공백입니다.")
        //    this.updateTrig=false
        //    document.getElementById("focus_textarea").focus()
           
        //  }
      },
      //2차 수정(Update)
      update(){  
          // console.log(this.editedItem.content)
          // const existingContent = this.editedItem.content // ex) editedItem.content값이 고정된 값으로 저장됨. 
          // console.log(existingContent)
          // if(existingContent===this.editedItem.content){
          //   console.log("if(existingContent===this.editedItem.content)==>"+existingContent)
          //   alert("내용을 입력해주세요")
          // }else{    
          axios.post ('http://localhost:1234/update',this.editedItem)
          .then( res =>{
          }),  
          alert("수정이 완료되었습니다.")
          this.dialog_detail=false
          history.go(0) 
  
      },
      //삭제하기(Delete)
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
        //console.log(this.editedItem.groupNo)
        axios.get('http://localhost:1234/deleteBoardByUser/'+this.editedItem.groupNo) 
          //axios.get방식으로 depth도 같이 넘기는 시도해봄 
            //결론> front와 back의 모든 경우의 수 따져보기 => ,,(x) / ,+(x) / ,&(x)   // +,(x) / ++(x) / +&(x) // &,(x) / &+(x) / &&(x)  ===> 2개의 파라미터 전달 실패. 
            //대안> axios.post()방식으로 그냥 객체를 통째로 넘기겠음. 
        .then(response =>{
          // console.log(this.editedItem.status)
          // if(this.editedItem.status===false){this.rows.splice(this.editidItemIndex , 1)}
          // else{console.log(this.editedItem.status) ,this.rows.splice(this.editidItemIndex , 2)}
        })
         this.dialog_detail=false
         history.go(0)
      },
      //상세보기-닫기버튼
      close(){
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
      close_update(){
        this.dialog_detail=false
        this.dialog_update=false
        
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
          //console.log("this.editedItem.boardNo ==="+this.editedItem.boardNo)
          this.BoardVO.boardNo=this.editedItem.boardNo
          this.BoardVO.title=this.editedItem.title
          this.BoardVO.content=this.editedItem.content
          this.BoardVO.userNo = this.userInfo.userNo
          this.BoardVO.boardDate = new Date() // DB와 데이터타입을 맞춰줘야돼서 Date() 
          this.BoardVO.userName = this.userInfo.userName                     
          // this.BoardVO.hits=0        
          // this.BoardVO.depth=0
          this.BoardVO.groupNo=this.editedItem.groupNo//back단에서 하려고했는데, front단에서 처리했음. 
          this.BoardVO.status=true
          // console.log("reply()_this.editedItem.title="+this.editedItem.title)
          // console.log("reply()_this.BoardVO.title="+this.BoardVO.title)
          // console.log("reply()_this.BoardVO.boardNO="+this.BoardVO.boardNo)
          console.log("reply() 호출")
      
          axios.post ('http://localhost:1234/insertReply',this.BoardVO) // 객체로 안넘겨주면 에러남. 왜그런지는 모름. 
          .then( res =>{
                this.BoardVO = res.data// 결과값 1로 저장됨. 
                // console.log("res::"+res)
                // console.log("res.data::"+res.data)
                //console.log(this.BoardVO)// showOneBoard()의 출력값 
           })
          //history.go(0) //현재 페이지 새로고침 
       
          this.BoardVO.title= "ㄴRE "+this.BoardVO.title
          //<i class="fab fa-replyd"></i> 
          window.location.href="http://localhost:8080/board"
          console.log("reply() 수행완료")
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
          console.log("reply() 호출")
     },
    //조회수 증가(Update)
    updateHits(){
      //console.log("updateHits()_this.editedItem.boardNo = "+this.editedItem.boardNo)
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
 
