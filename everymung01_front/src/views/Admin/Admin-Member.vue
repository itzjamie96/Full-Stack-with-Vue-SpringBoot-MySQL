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
                <v-text-field label="name" v-model="rows.userEmail" required></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="age"  v-model="rows.Name" required></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="city" v-model="rows.Pw" required></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">확인</v-btn>
          <v-btn color="blue darken-1" text @click="dele(binG)">삭제</v-btn>
          <v-btn color="blue darken-1" text @click="save(binG) ">저장</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

  </v-col>
  </v-row>
</template>
<script>
import 'vue-good-table/dist/vue-good-table.css'
import { VueGoodTable } from 'vue-good-table'
import axios from "axios"

export default {
    components: {
  VueGoodTable,
},data(){
    return {
       dialog: false,
       binG:{
       id :'',
       name:'',
       age:'',
       city:''},
      columns: [
        {
          label: 'Name',
          field: 'name',
        },
        {
          label: 'Age',
          field: 'age',
          type: 'number',
        },
        
      ],
      rows: [],
    };
  },
  methods: {
  onRowClick(params) {
     this.dialog=true
     this.binG.id = params.row.id
     this.binG.name = params.row.name
     this.binG.age = params.row.age
     this.binG.city = params.row.city
     // console.log(this.binG)
      
    // params.row - row object 
    // params.pageIndex - index of this row on the current page.
    // params.selected - if selection is enabled this argument 
    // indicates selected or not
    // params.event - click event
  },
  save(binG){
     this.dialog=false
     const Id = binG.id;

      for(let i=0 ; i<this.rows.length;i++){
         if(this.rows[i].id==Id){
         this.rows[i].name=binG.name
         this.rows[i].age=binG.age
         this.rows[i].city=binG.city
         break;
         }
      }
     
  },
  dele(binG){
     this.dialog=false
     this.rows.splice(binG.id , 1)
/* for(let i=0 ; i<this.rows.length;i++){
         if(this.rows[i].id==Id){
         break;
         }
      } */

  },
  userlist(){
      axios.get(`http://localhost:1234/userlist`) 
              .then(res => { 
                console.log(res)
                this.rows = res.data
              }) 
              .catch(error => { 
                console.log(error)
              }
            },

    },
    created: (){
        this.userlist();
    },
 
}
</script>