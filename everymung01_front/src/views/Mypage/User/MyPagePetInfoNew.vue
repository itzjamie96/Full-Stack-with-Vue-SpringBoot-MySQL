<template>
<div>
 <form>
     <!-- 반려견 사진 -->
    <v-row justify="center">
       <v-col cols="3" >
         <v-list-item-avatar tile size="100">
             <v-img 
               >
             </v-img>
             <v-btn>사진추가</v-btn>
         </v-list-item-avatar>
       </v-col>
    </v-row >
    <!-- 반려견 이름 text-field -->
    <v-row justify="center">
      <v-col cols="3"> 
         <v-text-field
         v-model="PetName"
         :counter="10"
         :rules="nameRules"
         label="Name"
         required
    ></v-text-field>
      </v-col>
    </v-row>
    <!-- 반려견 견종 정보  -->
    <v-row justify="center">
        <v-col cols="3">
          <v-text-field
          
          >
          
          </v-text-field>
        </v-col>
    </v-row>
    <!-- 반려견 생일(Age)  -->
    <v-row>
       <v-col>

       </v-col>
    </v-row>

    <!-- 반려견 체중  -->
    <v-row>
       <v-col>

       </v-col>
    </v-row>
    
    
    <!-- 반려견 성별 라디오버튼  -->
    <v-row>
       <v-col>

       </v-col>
    </v-row>

    
    <!-- 반려견 중성화 여부 라디오버튼 -->
    <v-row>
       <v-col>

       </v-col>
    </v-row>

    <!-- 반려견 답변 1 -->
    <v-row>
       <v-col>

       </v-col>
    </v-row>

    <!-- 반려견 답변 2-->
    <v-row>
       <v-col>

       </v-col>
    </v-row>

     <!-- 반려견 답변 3-->
    <v-row>
       <v-col>

       </v-col>
    </v-row>
    
     <!-- 반려견 답변 4-->
    <v-row>
       <v-col>

       </v-col>
    </v-row>
    
    

 </form>
</div>
   

    
</template>



<script>
export default {
    data: () => ({
      valid: true,
      PetName: '',
      nameRules: [
        v => !!v || '이름은 필수 입력 사항입니다. ',
        v => (v && v.length <= 10) || ' 10 자 내외로 입력 가능합니다. ',
      ],
    })
    
}
</script>