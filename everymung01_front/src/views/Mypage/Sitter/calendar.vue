<template>
<v-container>
  <full-calendar :events="fcEvents" locale="en"></full-calendar>

</v-container>
</template>

<script>
var demoEvents = [
	{
      title : 'Sunny Out of Office',
      start : '2016-08-25',
      end : '2017-07-27'
    }
]
export default {
  data () {
	return {
	  fcEvents : demoEvents
	}
  },
  components : {
	'full-calendar': require('vue-fullcalendar')	
  }
}
</script>