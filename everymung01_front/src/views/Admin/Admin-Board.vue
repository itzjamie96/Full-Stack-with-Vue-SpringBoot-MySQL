<template>
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
                <v-text-field label="Title" v-model="board.title" disabled></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="Content" v-model="board.content" disabled></v-text-field>
              </v-col>
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
              <v-btn class="mr-4"  color="error" @click="delet(board.boardNo)">확인</v-btn>
              <v-btn color="secondary" @click="deleteAlert=false">취소</v-btn>
            </v-alert>
        </v-card-text>
        <v-card-text v-if="updateAlert">
            <v-alert v-model="updateAlert" type="warning">
              <h4>정말 수정 하시겠습니까?</h4>
              <v-btn class="mr-4"  color="error" @click="update()">확인</v-btn>
              <v-btn color="secondary" @click="updateAlert=false">취소</v-btn>
            </v-alert>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false,content=null">확인</v-btn>
          <v-btn color="blue darken-1" text @click.native="deleteAlert=true">삭제</v-btn>
          <v-btn color="blue darken-1" text @click="reply(content)">답변달기</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-col>
  </v-row>
</template>
<script>
import 'vue-good-table/dist/vue-good-table.css'
import { VueGoodTable } from 'vue-good-table'

export default {
    components: {
  VueGoodTable,
},data(){
    return {
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
          label: 'No',
          field: 'boardNo',
        },
        {
          label: 'Name',
          field: 'userName',
        },
        {
          label: 'Title',
          field: 'title',

        },
        {
          label: 'Date',
          field: 'boardDate',
        },
        
      ],
      rows:[],
    };
  },
  created() {
    this.selectAll();
  },
  methods: {
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
  },
  selectAll(){
      this.$http.get(`http://localhost:1234/falseBoards`)
          .then( res =>{
            this.rows = res.data

          })
          .catch(err => {
            alert("backand(falseBoards) 에러 확인")
          })
     
  },
  delet(boardNo){
     this.dialog=false
     this.deleteAlert=false
     const No = boardNo
     
      this.$http.get(`http://localhost:1234/delete/${No}`).then(res =>{
        const idx = this.rows.findIndex(x => x.boardNo === boardNo)
        console.log(idx)
              this.dialog=false
              this.rows.splice(idx, 1)
      }).catch(err =>{
        alert("backend(delete) 에러 확인!")
      })

  },
  reply(content){
     this.board.title = "ㄴRE:  "+this.board.title
     this.board.status = true
     this.board.content = content
     this.dialog=false
     this.$http.post('http://localhost:1234/insertReply',this.board) 
              .then(res => { 
                this.selectAll();
                this.content=null
              }) 
              .catch(err => { 
                alert("backend(update) 에러 확인!")

              });
  },
    },

 
}
</script>