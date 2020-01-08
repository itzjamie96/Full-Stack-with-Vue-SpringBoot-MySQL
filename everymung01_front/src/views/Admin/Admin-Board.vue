<template>
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
>  </vue-good-table>

<v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">상세정보</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field label="No" v-model="board.boardNo" disabled></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Name" v-model="board.userName" disabled></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Title" v-model="board.title" readonly></v-text-field>
              </v-col>
              <v-col cols="12">
              </v-col>
              <v-textarea
                cols="12"
                outlined
                label="Content"
                v-model="board.content" 
                readonly
              >
              </v-textarea>
              <v-col cols="12">
                <v-text-field label="Date" v-model="board.boardDate" disabled ></v-text-field>
              </v-col>
              <v-textarea
                v-model="content"
                outlined
                name="input-7-4"
                label="답변작성"
                value="">
              </v-textarea>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-text v-if="deleteAlert">
            <v-alert v-model="deleteAlert" type="warning">
              <h4>정말 삭제 하시겠습니까?</h4>
              <v-btn class="mr-4"  color="error" @click="delet(board.status)">확인</v-btn>
              <v-btn color="secondary" @click="deleteAlert=false">취소</v-btn>
            </v-alert>
        </v-card-text>
        <v-card-text v-if="updateAlert">
            <v-alert v-model="updateAlert" type="warning">
              <h4>정말 수정 하시겠습니까?</h4>
              <v-btn class="mr-4"  color="error" @click="update(content)">확인</v-btn>
              <v-btn color="secondary" @click="updateAlert=false">취소</v-btn>
            </v-alert>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false,content=null">확인</v-btn>
          <v-btn color="blue darken-1" text @click.native="deleteAlert=true">삭제</v-btn>
          <v-btn color="blue darken-1" text @click.native="updateAlert=true" v-if="board.userNo === 0">답글수정</v-btn>
          <v-btn color="blue darken-1" text @click="reply(content)" v-if="board.userNo !== 0">답변달기</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-col>
  </v-row>
</template>
<script>
import 'vue-good-table/dist/vue-good-table.css'
import { VueGoodTable } from 'vue-good-table'
import { mapState, mapActions } from 'vuex';

export default {
    components: {
  VueGoodTable,
    'editor': Editor,
    'viewer': Viewer
},data(){
    return {
       editorText: '',
       content:'',
       dialog: false,
       deleteAlert: false,
       updateAlert: false,
       board:{
          boardNo :'',
          userName:'',
          title:'',
          content:'',
          depth:'',
          groupNo:'',
          hits:'',
          boardDate:'',
          userNo:'',
          status:'',
       },
      columns: [
        {
          label: '이름',
          field: 'userName',
        },
        {
          label: '제목',
          field: 'title',
        },
        {
          label: '날짜',
          field: 'boardDate',
        },
        {
          label: '답변여부',
          field: 'status',
        },
        
      ],
      rows:[],
    };
  },
  created() {
    this.selectAll();
  },
  computed: {
    ...mapState(['boardcount']),
    boardCtn(){
      return this.boardcount
    }
  },
  methods: {
    ...mapActions(['boardInfo']),
    
    onRowClick(params) {
      this.dialog=true
      this.board.boardNo = params.row.boardNo
      this.board.title = params.row.title
      this.board.userName = params.row.userName
      this.board.content = params.row.content
      this.board.boardDate = params.row.boardDate
      this.board.groupNo = params.row.groupNo
      this.board.depth = params.row.depth
      this.board.hits = params.row.hits
      this.board.userNo = params.row.userNo
      this.board.status = params.row.status
      },
    selectAll(){
        this.$http.get(`http://localhost:1234/showAdminBoards`)
            .then( res =>{
              this.rows = res.data
            })
            .catch(err => {
              alert(err+"\n"+"Admin-Board(selectAll) 에러")
            })
      
    },
    delet(status){
      let cnt = this.boardcount
      this.board.status = false
      const boardNo = this.board.boardNo
      const groupNo = this.board.groupNo
      
      if(status === false) { // 게시판 원글만 삭제
        this.$http.post(`http://localhost:1234/deleteBoardByMngr`,this.board)
              .then(res =>{
                console.log(trig)
                  const idx = this.rows.findIndex(x => x.boardNo === boardNo)
                  this.dialog=false
                  this.deleteAlert=false
                  this.rows.splice(idx, 1)
                  cnt--
                  this.boardInfo(String(cnt))
                }).catch(err =>{
                  alert(err+"\n"+"Admin-Board(delet) 에러")
                })
      }else{ 
        if(this.board.userNo===0){ // 댓글만 삭제
        this.$http.post(`http://localhost:1234/deleteBoardByMngr`,this.board)
              .then(res =>{
                  cnt++
                  this.boardInfo(String(cnt))
                  this.selectAll();
                  this.dialog=false
                  this.deleteAlert=false
                }).catch(err =>{
                  alert(err+"\n"+"Admin-Board(delet) 에러")
                })}
          else{ // 원글+댓글 삭제
            this.$http.post(`http://localhost:1234/deleteBoardByMngr`,this.board)
                .then(res =>{
                    this.selectAll();
                    this.dialog=false
                    this.deleteAlert=false
                  }).catch(err =>{
                    alert(err+"\n"+"Admin-Board(delet) 에러")
                  })
                  }
        
      }
      
    },
    update(content){
      this.board.content = content
      this.$http.post(`http://localhost:1234/updateBoardByMngr`,this.board)
              .then(res =>{
                  this.dialog=false
                  this.updateAlert=false
                  this.selectAll();
                  this.content=null
                }).catch(err =>{
                  alert(err+"\n"+"Admin-Board(update) 에러")
                })
    },
    reply(content){
      let cnt = this.boardcount
      this.board.title = "ㄴRE:  "+this.board.title
      this.board.status = true
      this.board.content = content
      this.$http.post('http://localhost:1234/insertReply',this.board) 
                .then(res => { 
                  this.selectAll();
                  this.dialog=false
                  this.content=null
                  cnt--
                  this.boardInfo(String(cnt))
                }) 
                .catch(err => { 
                  alert(err+"\n"+"Admin-Board(reply) 에러")

                });
    },
},

 
}
</script>