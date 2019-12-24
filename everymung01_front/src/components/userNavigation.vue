<template>
    <v-card height="100%">
        <v-navigation-drawer
          absolute
          permanent
          left
          width="300px"
      >
        <template>
          <v-list-item class="ml-6 mt-3">
            <v-list-item-avatar
              size="100"
              color="green"
            >
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title>유저 이름</v-list-item-title>
              <v-list-item-subtitle>유저 타입</v-list-item-subtitle>
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
  </v-card>

</template>

<script>
  export default {
    data () {
      return {
        items: [
          { title: '예약내역', icon: 'mdi-home-city', link: '/reservation' },
          { title: '실시간 케어 보기', icon: 'mdi-account' , link: '/reservation'},
          { title: '반려견 정보관리', icon: 'mdi-account' , link: '/reservation'},
          { title: '계정관리', icon: 'mdi-account-group-outline' , link: '/reservation'},
        ],
      }
    },
  }
</script>