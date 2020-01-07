<template>
    <div height="100%">
        <v-navigation-drawer
          left
          width="300px"
      >
        <template>
          <v-list-item class="ml-6 mt-3">
            <v-list-item-avatar
              size="100"
            >
            <v-img
            :src="'http://localhost:1234/download/' + this.userInfo.userProfile"
            >
            </v-img>


            </v-list-item-avatar>
            
            <v-list-item-content>
              <v-list-item-title>{{this.userInfo.userName}}</v-list-item-title>
              <v-list-item-subtitle>일반 유저</v-list-item-subtitle>
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
          { title: '예약내역', icon: 'mdi-home-city', link: '/uMyPage/userReservationList' },
          { title: '실시간 케어 보기', icon: 'mdi-account' , link: '/uMyPage/live'},
          { title: '반려견 정보관리', icon: 'mdi-account' , link: '/uMyPage/petInfo'},
          { title: '계정관리', icon: 'mdi-account-group-outline' , link: '/uMyPage/uAccount'},
        ],
        }
    },
    computed: {
        ...mapState(["isLogin","userInfo","isLoginError"]),
    },


  }
</script>