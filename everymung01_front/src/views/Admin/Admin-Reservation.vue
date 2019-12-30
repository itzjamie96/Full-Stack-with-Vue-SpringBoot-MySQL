<template>
    <v-app>
        <v-container>
        <br><br><br><br><br>
        <v-row>
            <v-col>
  <v-card
    class="left-auto"
    max-width="300"
  >
   <v-card-text class="pt-12">
      <br><br>
       <v-btn text @click="onedayData" router :to="{name: 'adminoneday'}" exact>
        <v-icon size="100px">mdi-run</v-icon>  
      </v-btn>
      <b class="display-1 text--primary font-weight-black">하루</b>
        <div class="text-center pl-12 ml-12">
        <h2 class="mr-8 text--primary font-weight-black">{{home.length}}건</h2>
        </div>
      <br><br><br>
    </v-card-text>
  </v-card>
        </v-col>

    <v-col>
        <v-card
          class="left-auto"
          max-width="300"
        >
   <v-card-text class="pt-12">
      <br><br>
       <v-btn text router :to="{name: 'consignment'}" exact>
        <v-icon size="100px">mdi-home</v-icon>  
      </v-btn>
      <b class="display-1 text--primary font-weight-black">위탁</b>
      <div class="text-center pl-12 ml-12">
        <h2 class="mr-8 text--primary font-weight-black">{{daycare.length}}건</h2>
        </div>
      <br><br><br>
    </v-card-text>
    <v-card-actions>

    </v-card-actions>

  </v-card>
    </v-col>
    <!-- <v-col>
        <v-card
    class="left-auto"
    max-width="300"
  >
  <v-card-text class="pt-12">
      <br><br>
       <v-btn text router :to="{name: 'adminrepeated'}" exact>
        <v-icon size="100px">mdi-calendar-month</v-icon>  
      </v-btn>
      <b class="display-1 text--primary font-weight-black">정기</b>
      <div class="text-center pl-12 ml-12">
        <h2 class="mr-8 text--primary font-weight-black">12건</h2>
        </div>
      <br><br><br>
    </v-card-text>

  </v-card>
    </v-col> -->
    </v-row>
    </v-container>
    
    
  </v-app>

</template> 


<script>
import {eventBus} from'@/main.js'

export default {
  data (){
    return {
      data:[],
      home:[],
      daycare:[],
    }
  },
  created(){
    this.$http.get(`http://localhost:1234/test`)
        .then(res => { 
                this.data=res.data
                for(let sitterT in res.data){
                  if(res.data[sitterT].sittingType==="home"){
                    this.home.push(res.data[sitterT])
                  }else if(res.data[sitterT].sittingType==="daycare"){
                    this.daycare.push(res.data[sitterT])
                  }
                }
                console.log(this.data)
              }) 
              .catch(err => { 
                alert("backend(test) 에러 확인!")
              });
  },
  methods: {
    onedayData(){
      eventBus.$emit("onedayData",this.home);
      console.log(this.home)
    }
  },
  /* methods: {
    select(){
      this.$http.get(`http://localhost:1234/test`)
        .then(res => { 
                this.data=res.data
                for(let sitterT in res.data){
                  if(res.data[sitterT].sittingType==="home"){
                    this.home.push(res.data[sitterT])
                  }else if(res.data[sitterT].sittingType==="daycare"){
                    this.daycare.push(res.data[sitterT])
                  }
                }
                console.log(this.data)
              }) 
              .catch(err => { 
                alert("backend(test) 에러 확인!")

              });
    },
    test12(){
      for(let i=0; i<this.data.length; i++){
          if(this.data[i].sittingType === "home"){
          this.onedayCount++;
          console.log(onedayCount)
          }
          else{
          this.ConsignmentCount++;
          }
          
      }
    }
  } */
}
</script>