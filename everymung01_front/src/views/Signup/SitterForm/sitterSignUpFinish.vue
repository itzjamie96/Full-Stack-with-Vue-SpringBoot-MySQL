<template>
<v-container class="mt-12 pt-12">
    <v-row class="justify-center ">
        <v-col cols="8" >
            <v-card>
                <v-row class="justify-center pt-12">
                    <v-col cols="10" class="text-center">
                        <h1>에브리댕에 지원해주셔서 감사합니다!</h1>
                    </v-col>
                </v-row>
                <v-divider class="mx-12"></v-divider>
                <v-row class="justify-center py-12">
                    <v-col cols="6" class="text-center">
                        <p>지원 결과는 추후 이메일로 통지될 예정입니다</p>
                    </v-col>
                </v-row>
                <v-row class="justify-center">
                    <v-col cols="6" class="text-center">
                        <v-btn  @click="$router.push({name : 'home'})" depressed>메인으로</v-btn>

                    </v-col>
                </v-row>
                <v-row class="pb-12">
                    <v-col>
                    </v-col>
                </v-row>
            </v-card>

        </v-col>
    </v-row>

</v-container>
</template>
<script>
export default {
    
}
</script>