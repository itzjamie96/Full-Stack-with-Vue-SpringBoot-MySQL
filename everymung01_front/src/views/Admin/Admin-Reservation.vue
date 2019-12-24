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
       <v-btn text router :to="{name: 'oneday'}" exact>
        <v-icon size="100px">mdi-run</v-icon>  
      </v-btn>
      <b class="display-1 text--primary font-weight-black">하루</b>
        <div class="text-center pl-12 ml-12">
        <h2 class="mr-8 text--primary font-weight-black">12건</h2>
        </div>
      <br><br><br>

    </v-card-text>
    <v-card-actions>

    </v-card-actions>

  </v-card>
        </v-col>
    <v-col>
        <v-card
    class="left-auto"
    max-width="300"
  >
   <v-card-text class="pt-12">
      <br><br>
       <v-btn text router :to="{name: 'consignment'}" exact >
        <v-icon size="100px">mdi-home</v-icon>  
      </v-btn>
      <b class="display-1 text--primary font-weight-black">위탁</b>
      <div class="text-center pl-12 ml-12">
        <h2 class="mr-8 text--primary font-weight-black">12건</h2>
        </div>
      <br><br><br>
    </v-card-text>
    <v-card-actions>

    </v-card-actions>

  </v-card>
    </v-col>
    <v-col>
        <v-card
    class="left-auto"
    max-width="300"
  >
  <v-card-text class="pt-12">
      <br><br>
       <v-btn text router :to="{name: 'repeated'}" exact>
        <v-icon size="100px">mdi-calendar-month</v-icon>  
      </v-btn>
      <b class="display-1 text--primary font-weight-black">정기</b>
      <div class="text-center pl-12 ml-12">
        <h2 class="mr-8 text--primary font-weight-black">12건</h2>
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