<template>
    <div height="100%">
        <v-navigation-drawer
          left
          width="300px"
      >
        <template>
          <v-list-item class="ml-6 mt-3">
            <router-link :to="{path:'/sitterProfilePage/' + this.userInfo.sitterNo + '/' + this.userInfo.sittingType}">
            <v-list-item-avatar
              size="100"
            >
             <v-img
              :src="'http://localhost:1234/download/' + this.userInfo.sitterImg1">
             </v-img>
            </v-list-item-avatar>
            </router-link>

            <v-list-item-content>
              <v-list-item-title>{{this.userInfo.sitterName}}</v-list-item-title>
              <v-list-item-subtitle>{{this.userInfo.sittingType}} sitter</v-list-item-subtitle>
            </v-list-item-content>
            
            
          </v-list-item>

        </template>

        <v-divider></v-divider>

        <v-list>
          <v-list-item
            v-for="item in items"
            :key="item.title"
            routuer :to="item.link"
            class="mx-3"
          >

            <v-list-item-content>
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>
  </div>

</template>

<script>
import {mapState,mapActions} from "vuex"

  export default {
    data () {
      return {
        items: [
          { title: '예약내역', icon: 'mdi-home-city', link: '/sMyPage/sitterReservationList' },
          { title: '실시간 케어 보기', icon: 'mdi-account' , link: '/sMyPage/live'},
          { title: '프로필 관리', icon: 'mdi-account' , link: '/sMyPage/sitterProfile'},
          { title: '계정관리', icon: 'mdi-account-group-outline' , link: '/sMyPage/sitterMyPageSetting'},
        ],
      }
    },

    computed: {
        ...mapState(["isLogin","userInfo","isLoginError"])
    },


  }
</script>