<template>
<div>
    <!-- 카카오 페이 성공 시 넘어오는 페이지 -->
    <h1>kakaoSuccess</h1>
    <h1>결제 완료</h1>
    <!-- kakao pay success 컨트롤러에서 넘어오는 리턴 값--> 
    <h2>{{this.kakao}}</h2>

    <h3>마이페이지 예약 내역으로 이동 추가 예정</h3>
</div>
</template>

<script>
import axios from "axios"

export default {
    data() {
        return{
            kakao : ''
        }
    },

    created() {
        this.initialize()
    },


    methods: {

    initialize(){
        const kakaoUrl = this.$route.query.pg_token
        console.log(kakaoUrl)
        axios.get(`http://localhost:1234/kakaoPaySuccesss?pg_token=${kakaoUrl}`)
                .then(res => {
                    this.kakao = res.data
                    console.log(res);
                })
                .catch(err => {
                    console.log(err);
                })
        }
    }

}
</script>