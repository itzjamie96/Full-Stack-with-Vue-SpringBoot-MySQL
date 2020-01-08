<template>
  <v-row>
    <v-col
      sm="12"
      lg="6"
      class="mb-4 controls mt-12"
    >

      <v-menu
        ref="startMenu"
        v-model="startMenu"
        :close-on-content-click="false"
        :nudge-right="40"
        :return-value.sync="start"
        transition="scale-transition"
        min-width="290px"
        offset-y
        full-width
      >
        <template v-slot:activator="{ on }">
          <v-text-field
            v-model="start"
            label="Start Date"
            prepend-icon="far fa-calendar-alt"
            readonly
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
          v-model="start"
          no-title
          scrollable
          type="month"
        >
          <v-spacer></v-spacer>

          
          <v-btn
            text
            color="primary"
            @click="startMenu = false"
          >
            Cancel
          </v-btn>
          <v-btn
            text
            color="primary"
            @click="$refs.startMenu.save(start)"
            
          >
            OK
          </v-btn>
        </v-date-picker>
      </v-menu>
      <h1 class="text-center">매출현황</h1><br>
       <VeLine :data="chartData" />
      </v-col>
      
      <v-col
      sm="12"
      lg="6"
      class="mb-4 controls mt-12"
      >
      <v-menu
        ref="endMenu"
        v-model="endMenu"
        :close-on-content-click="false"
        :nudge-right="40"
        :return-value.sync="end"
        transition="scale-transition"
        min-width="290px"
        offset-y
        full-width
      >
        <template v-slot:activator="{ on }">
          <v-text-field
            v-model="end"
            label="End Date"
            prepend-icon="far fa-calendar-alt"
            readonly
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
          v-model="end"
          no-title
          scrollable
          type="month"
        >
          <v-spacer></v-spacer>
          <v-btn
            text
            color="primary"
            @click="endMenu = false"
          >
            Cancel
          </v-btn>
          <v-btn
            text
            color="primary"
            @click="payment(start,end),allUser(start,end),allSitter(start,end)"
            
          >
            OK
          </v-btn>
          
        </v-date-picker>
      
      </v-menu>
      <h1 class="text-center">가입현황</h1><br>
    <veBar :data="chartDataA"></veBar>
    </v-col>
 
  </v-row>
</template>
<script>
import VeLine from 'v-charts/lib/line.common'
import veBar from 'v-charts/lib/histogram.common'
import axios from 'axios'
import * as FormData from 'form-data'


  export default {
    components:{
      VeLine,veBar
    },
    data: () => ({
      startMenu: false,
      endMenu: false,
      start: '2019-11',
      end: '2020-01',
      //가입현황
      chartDataA: {
            columns: ['date', 'sales'],
            rows: [
              
            ]
          },
      //매출현황
       chartData: {
            columns: ['date', 'sales'],
            rows: [
              
            ]
          },
      user:[],
      home:[],
      daycare:[],
      test:[],
      test1:0,
      sum:[],
    }),
    computed: {
     lsm(){
       
       return this.series
     } 
    },
    created(){
      this.allUser();
      this.allSitter();
      this.payment();
    },
    methods:{
      allUser(start,end){
        this.user=[];
        let dddate = new Date(start)
        let ddate =new Date(end) 
              if(start !== undefined && end !== undefined){
              this.$http.get(`http://localhost:1234/userlist`)
                .then(res => { 
                  
                        for(let i in res.data){
                          let zz =new Date(res.data[i].userDate.substr(0,7))
                          if(zz >=dddate && zz<=ddate)
                          {
                          this.user.push(res.data[i])
                          
                          }
                        }
                        
                       // this.chartDataA.rows.push({'User':this.user.length,'Home':0,'DayCare':0})
                      }) 
                      .catch(err => { 
                        alert(err+"\n"+"Admin-Home(allUser) 에러1")
                      });
              } else{
                this.$http.get(`http://localhost:1234/userlist`)
                .then(res => { 
                  
                        for(let i in res.data){
                          
                          this.user.push(res.data[i])
                          
                        }
                        
                        
                      }) 
                      .catch(err => { 
                        alert(err+"\n"+"Admin-Home(allUser) 에러2")
                      });
              } 

      },
      allSitter(start,end){
        this.chartDataA.rows=[]
        let dddate = new Date(start)
        let ddate =new Date(end) 
        this.daycare=[];
        this.home=[];      
                  if(start !== undefined && end !== undefined){
                    this.$http.get(`http://localhost:1234/showAllSitters`)
                      .then(res => { 
                           for(let i in res.data){
                            let zz =new Date(res.data[i].approvalDate.substr(0,7))
                            
                              zz >=dddate && zz<=ddate &&res.data[i].sittingNo===1?this.daycare.push(res.data[i]):
                              zz >=dddate && zz<=ddate &&res.data[i].sittingNo===2?this.home.push(res.data[i]):''
                            }
                          this.chartDataA.rows.push({'date':'User','sales':this.user.length},
                          {'date':'Home','sales':this.home.length},{'date':'DayCare','sales':this.daycare.length})
                      })
                        .catch(err => { 
                          alert(err+"\n"+"Admin-Home(showAllSitters) 에러1")
                        });
                      }else{
                        this.$http.get(`http://localhost:1234/showAllSitters`)
                                            .then(res => { 
                                              console.log(res.data)
                                                for(let i in res.data){
                                                  
                                                    res.data[i].sittingNo===1?this.daycare.push(res.data[i]):
                                                    res.data[i].sittingNo===2?this.home.push(res.data[i]):''
                                                  }
                                                this.chartDataA.rows.push({'date':'User','sales':this.user.length},
                                                {'date':'Home','sales':this.home.length},{'date':'DayCare','sales':this.daycare.length})
                                            })
                                              .catch(err => { 
                                                alert(err+"\n"+"Admin-Home(showAllSitters) 에러2")
                                              });
                      }
                      
      },
      payment(start,end){
        
            this.$http.get(`http://localhost:1234/showAllPayment`)
              .then(res => {
                let lsm = []
                this.test1 =res.data
                  
                  for(let i=0;i<this.test1.length;i++){
                    for(let j=i+1;j<this.test1.length;j++){
                    if(this.test1[i].paymentDate.substr(0,7) === this.test1[j].paymentDate.substr(0,7)){
                      this.test1[i].amount +=this.test1[j].amount
                      this.test1[j].amount=0
                    }
                    
                  }}

                  for(let i in this.test1){
                    this.test1[i].amount === 0 ? '':lsm.push(this.test1[i])
                  }
                  
                    if(start !==undefined && end !==undefined){
                      let dddate = new Date(start)
                      let ddate =new Date(end) 
                      
                      this.chartData.rows = [];
                      for(let i=lsm.length-1 ; i>=0;i--){
                      let ppp = new Date(lsm[i].paymentDate.substr(0,7))
                      if(ppp>=dddate && ppp<=ddate){
                      this.chartData.rows.push({'date':lsm[i].paymentDate.substr(0,7),'sales':lsm[i].amount})
                      }
                    }
                    }else{
                    for(let i=lsm.length-1 ; i>=0;i--){
                      this.chartData.rows.push({'date':lsm[i].paymentDate.substr(0,7),'sales':lsm[i].amount})
                    }
                        }
              })
              
      },

    }


    
  }
</script>
