  <template>
  <v-container fluid class="full-height"> 
      <v-row class="fill-height">
        <v-col cols="3">
          <side-bar/>
          
        </v-col>

        <v-col cols="9">
          
        </v-col>

      </v-row>
    </v-container>


</template>

<script>
import NavBar from '@/components/userNavigation.vue'

  export default {
    components: {
    'side-bar' : NavBar
    }
  }
</script>