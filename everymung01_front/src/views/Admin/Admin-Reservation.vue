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
       <v-btn text router :to="{name: 'admindaycare'}" exact>
        <v-icon size="100px">mdi-run</v-icon>
        <!-- <i class="fas fa-running fa-5x"></i> -->
      </v-btn>
      <b class="display-1 text--primary font-weight-black">하루</b>
        <div class="text-center pl-12 ml-12">
        <h2 class="mr-8 text--primary font-weight-black">{{daycare.length}}건</h2>
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
       <v-btn text router :to="{name: 'adminhomecare'}" exact>
        <!-- <v-icon size="100px">mdi-home</v-icon>  -->
        <i class="fas fa-home fa-5x"></i>
      </v-btn>
      <b class="display-1 text--primary font-weight-black">위탁</b>
      <div class="text-center pl-12 ml-12">
        <h2 class="mr-8 text--primary font-weight-black">{{home.length}}건</h2>
        </div>
      <br><br><br>
    </v-card-text>
    <v-card-actions>

    </v-card-actions>

  </v-card>
    </v-col>
    </v-row>
    </v-container>
    
    
  </v-app>

</template> 


<script>

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
                
              }) 
              .catch(err => { 
                alert(err+"\n"+"Admin-Reservation(created) 에러")
              });
  },
}
</script>