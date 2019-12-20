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
                <v-text-field label="name" v-model="binG.name" required ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="age"  v-model="binG.age" required readonly></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="city" v-model="binG.city" required readonly></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">확인</v-btn>
          <v-btn color="blue darken-1" text @click.native="alert=true">삭제</v-btn>
          <!-- <v-btn color="blue darken-1" text @click="dele(binG)">삭제</v-btn> -->
          <!-- <v-btn color="blue darken-1" text @click="save(binG) ">저장</v-btn> -->
        </v-card-actions>
        <v-card-text>
          <v-card-text v-if="alert">
            <v-alert v-model="alert" type="warning">
              <h4>정말 삭제 하시겠습니까?</h4>
              <v-btn class="mr-4"  color="error" @click="dele(binG)">확인</v-btn>
              <v-btn color="secondary" @click="alert=false">취소</v-btn>
            </v-alert>
          </v-card-text>
        </v-card-text>
      </v-card>
    </v-dialog>

  </v-col>
  </v-row>
</v-app>
</template>
<script>
import 'vue-good-table/dist/vue-good-table.css'
import { VueGoodTable } from 'vue-good-table'

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
       alert: false,
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
      rows: [
        { id:1, name:"a", age: 20,  city:"busan" },
        { id:2, name:"b", age: 24,  city:"busan" },
        { id:3, name:"c", age: 16,  city:"busan" },
        { id:4, name:"d", age: 55,  city:"busan" },
        { id:5, name:"e", age: 40,  city:"busan" },
        { id:6, name:"f", age: 23,  city:"busan" },
        { id:7, name:"g", age: 24,  city:"busan" },
      ],
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
     console.log(binG)
     console.log(idx)
     this.dialog=false
     this.alert=false
     this.rows.splice(binG , 1)
/* for(let i=0 ; i<this.rows.length;i++){
         if(this.rows[i].id==Id){
         break;
         }
      } */

  }

}
}
</script>