<template>

    <div>
      <v-navigation-drawer
        v-model="drawer"
        absolute
        temporary
      >
      <v-list-item
        v-for="item in menuItems" 
        :key="item.title"
        router :to="item.link"
      >
        <v-list-item-content>
          <v-list-item-title>{{item.title}}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item >
        <v-list-item-content>
          <v-list-item-title>회원가입</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item >
        <v-list-item-content>
          <v-list-item-title>로그인</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item >
        <v-list-item-content>
          <v-list-item-title>마이페이지</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item >
        <v-list-item-content>
          <v-list-item-title>로그아웃</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      </v-navigation-drawer>

    <v-toolbar dark color="red lighten-1">

      <v-app-bar-nav-icon 
        @click.stop="drawer = !drawer"
        class="hidden-md-and-up"
        ></v-app-bar-nav-icon>

      <v-toolbar-title class="mr-0 px-0">
          <router-link :to="{name: 'home'}" exact
          class="white--text"
          id="homebtn"
          >
        <div>
          <div class="d-inline">
            <i class="fas fa-paw fa-1x"></i>
          </div>
          <div class="d-inline ml-1">
            에브리멍
          </div>
        </div>
          </router-link>

      </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-toolbar-items class="hidden-sm-and-down">

        <v-btn 
        text
        v-for="item in menuItems" 
        :key="item.title"
        router :to="item.link"
        >{{item.title}}</v-btn>
   
          <v-btn text router :to="{name:'signup'}" exact v-if="isLogin===false">회원가입</v-btn>
          <v-btn text  router :to="{name:'SitterReservationList'}" exact v-else-if="isLogin===true&& role ==='Sitter'">마이페이지</v-btn>
          <v-btn text  router :to="{name:'UserReservationList'}" exact v-else-if="isLogin===true&& role ==='User'">마이페이지</v-btn>

          <v-btn text router :to="{name:'signin'}" exact v-if="isLogin===false">로그인</v-btn>
          <v-btn text  @click="$store.dispatch('logout')" v-else>로그아웃</v-btn>

      </v-toolbar-items>

    </v-toolbar>
  </div>

</template>

<script>
import {mapState,/* mapActions */} from 'vuex'

export default {
  data () {
    return {
      drawer: false,
      menuItems: [
        {title: '하루 시터', link: '/daysitters'},
        {title: '위탁 시터', link: '/homesitters'},
        {title: '펫시터 지원', link: '/sitterSignUp',},
        {title: '문의하기', link: '/board'},
      ]
    }
  },
  computed:{
      ...mapState(['isLogin','role']),...mapState(['userInfo'])
      /* ...mapActions(['logout']) */
    },

}
</script>


<style>
@import url('https://fonts.googleapis.com/css?family=Gugi|Jua|Single+Day&display=swap');
@font-face { 
  font-family: 'S-CoreDream-8Heavy'; 
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-8Heavy.woff') format('woff'); 
  font-weight: normal; 
  font-style: normal; 
}
@font-face { 
  font-family: 'HangeulNuri-Bold'; 
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_three@1.0/HangeulNuri-Bold.woff') format('woff'); 
  font-weight: normal; 
  font-style: normal; }

#homebtn{
    text-decoration: none;
    font-size: 19pt;
    font-family: 'Gugi', cursive;

}

.v-btn__content{
  font-size: 14pt;
  /* font-family: 'HangeulNuri-Bold';  */
}

</style>

