<template>
  <v-app id="inspire">
    <v-navigation-drawer
    v-model="drawer"
      app
      clipped
    >
    
      <v-list dense>
        <v-list-item link class="ma-5" router :to="{name: 'home'}" exact>
          <v-list-item-action>
            <v-icon>mdi-home</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>홈</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
         
          <v-divider ></v-divider>
        
        <v-list-item link class="ma-5" router :to="{name: 'reservation'}" exact>
          <v-list-item-action>
            <v-icon>mdi-home</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>예약관리</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        
        <v-divider ></v-divider>
        
        <v-list-item link class="ma-5" router :to="{name: 'member'}">
          <v-list-item-action>
            <v-icon>mdi-contact-mail</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>회원관리</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        
        <v-divider ></v-divider>
        
        <v-list-item link class="ma-5" router :to="{name: 'employee'}">
          <v-list-item-action>
            <v-icon>mdi-contact-mail</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>직원관리</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        
        <v-divider ></v-divider>
        
        <v-list-item link class="ma-5" router :to="{name: 'approval'}"> 
          <v-list-item-action>
            <v-icon>mdi-contact-mail</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>승인대기</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        
        <v-divider ></v-divider>
        
        <v-list-item link class="ma-5" router :to="{name: 'board'}">
          <v-list-item-action>
            <v-icon>mdi-contact-mail</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>게시판 관리</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        
        <v-divider ></v-divider>
        
        <v-list-item link class="ma-5" router :to="{name: 'payment'}">
          <v-list-item-action>
            <v-icon>mdi-contact-mail</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>결재내역</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-divider ></v-divider>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar
      app
      color="indigo"
      dark
      clipped-left
      dense
    >
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
      <v-toolbar-title>관리자</v-toolbar-title>
      <v-spacer></v-spacer>
      
      <v-toolbar-items>
        <v-btn text icon>
        <v-icon>mdi-emoticon-devil</v-icon>
        </v-btn>
        
        <v-divider vertical></v-divider>
        <v-btn text>로그아웃</v-btn>
      </v-toolbar-items>
    </v-app-bar>

    <v-content>
      <v-container>
        <router-view/>
      </v-container>
    </v-content>
    <v-footer
      color="indigo"
      app
    >
      <span class="white--text">&copy; 2019</span>
    </v-footer>
  </v-app>
</template>

<script>
  export default {
    props: {
      source: String,
    },
    data: () => ({
      drawer: null,
    }),
  }
</script>