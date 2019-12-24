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

      <v-toolbar-title>
        <router-link to="/" tag="span" style="cursor: pointer">
        <v-btn text router :to="{name: 'home'}" exact>
          <i class="fas fa-paw fa-2x mr-2"></i>
            에브리멍
        </v-btn>
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
        <div v-if="isLogin===false" class="mt-3">
        <v-btn text router :to="{name:'signup'}" exact>회원가입</v-btn>
        <v-btn text router :to="{name:'signin'}" exact>로그인</v-btn>
        </div>
        <div class="mt-3" v-else>
        <v-btn text  router :to="{name:'uMyPage'}" exact>마이페이지</v-btn>
        <v-btn text  @click="$store.dispatch('logout')">로그아웃</v-btn>
        </div>
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
        {title: '위탁 시터', link: '/homesitters/'},
        {title: '펫시터 지원', link: '/sitterSignUp'},
        {title: '문의하기', link: '/board'},
      ]
    }
  },
  computed:{
      ...mapState(['isLogin']),...mapState(['userInfo'])
      /* ...mapActions(['logout']) */
    },
}
</script>


