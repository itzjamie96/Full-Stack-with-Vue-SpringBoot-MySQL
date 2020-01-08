<template>
  <v-app id="inspire">
    <v-navigation-drawer
    v-model="drawer"
      app
      clipped
    >
    
      <v-list dense>
        <v-list-item link class="ma-5" router :to="{name: 'adminHome'}" exact>
          <v-list-item-action>
            <v-icon>mdi-home</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>홈</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
         
          <v-divider ></v-divider>
        
        <v-list-item link class="ma-5" router :to="{name: 'adminreservation'}" exact>
          <v-list-item-action>
            <v-icon>mdi-home</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>예약관리</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        
        <v-divider ></v-divider>
        
        <v-list-item link class="ma-5" router :to="{name: 'adminmember'}" exact>
          <v-list-item-action>
            <v-icon>mdi-contact-mail</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>회원관리</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        
        <v-divider ></v-divider>
        
        <v-list-item link class="ma-5" router :to="{name: 'adminemployee'}" exact>
          <v-list-item-action>
            <v-icon>mdi-contact-mail</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>직원관리</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        
        <v-divider ></v-divider>
        
        <v-list-item link class="ma-5" router :to="{name: 'adminapproval'}" exact> 
          <v-list-item-action>
            <v-icon>mdi-contact-mail</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>
                승인대기
              <v-badge
                v-if="approvalcnt!=='0'"
                color="red"
                :content='approvalcnt'
                inline
              >
              </v-badge>
            </v-list-item-title>
            
          </v-list-item-content>
        </v-list-item>
        
        <v-divider ></v-divider>
        
        <v-list-item link class="ma-5" router :to="{name: 'adminboard'}" exact>
          <v-list-item-action>
            <v-icon>mdi-contact-mail</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>
              게시판 관리
              <v-badge
                v-if="boardcnt!=='0'"
                color="red"
                :content='boardcnt'
                inline
              >
              </v-badge>
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        
        <v-divider ></v-divider>
        
        <v-list-item link class="ma-5" router :to="{name: 'adminpayment'}" exact>
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
        <v-btn @click="$store.dispatch('logout')" text>로그아웃</v-btn>
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
import {mapState,mapActions} from "vuex"
import router from '../router/index'
import { eventBus } from '../main'

  export default {
    props: {
      source: String,
    },
    data: () => ({
      drawer: null,
     
    }),
    computed:{
      ...mapState(["trigger",'count','boardcount']),
      
      approvalcnt(){
        return this.count
      },
      boardcnt(){
        return this.boardcount
      }
    },
    methods:{
      ...mapActions(['TriggerTO','approval','boardInfo']),

      boardCtn() {
        this.$http.get(`http://localhost:1234/falseBoard`)
          .then( res => {
            this.boardInfo(String(res.data.length))
          })
          .catch( err => {
            alert(err+"\n"+"Admin-main(boardCtn) 에러")
          })
      },

      approvalCtn(){
        this.$http.get(`http://localhost:1234/falseAllSitters`)
          .then( res =>{
            this.approval(String(res.data.length))
          })
          .catch(err => {
            alert(err+"\n"+"Admin-main(approval) 에러")
          })
      }


    },
    created() {
      this.approvalCtn();
      this.boardCtn();

    }
  }
</script>