<template>
   <v-container fluid="">
      <v-row class="fill-height">
         <v-col cols="3">
            <side-bar/>   
         </v-col>
         <v-col cols="9">
            <v-card>
               <form>
            <!-- 반려견 사진 -->
                  <div>
                     <v-row justify="center">
                        <v-col cols="3" > 
                           <div class="file-upload-form" >
                           반려견 사진 추가 :
                              <input 
                              type="file" 
                              @change="previewImage" 
                              accept="image/*">
                  </div>
                     <div class="image-preview" v-if="imageData.length > 0">
                        <img  class="preview" :src="imageData">
                     </div>
                        </v-col>
                     </v-row>   
                  </div>
            <!-- 반려견 이름 text-field -->
                     <v-row justify="center">
                        <v-col cols="5"> 
                           <v-text-field
                           v-model="userPetVo.petName"
                           :counter="15"
                           :rules="nameRules"
                           label="반려견이름"
                           required
                           ></v-text-field>
                        </v-col>
                        <v-col cols="1"></v-col>
      
            <!-- 반려견 견종 정보  -->
            
                        <v-col cols="5">
                           <v-select
                           v-model="userPetVo.dogBreed"
                           :items="lsm"
                           label="견종"
                           >
                           </v-select>
                        </v-col>
                      </v-row>
            <!-- row2  -->
            <!-- 반려견 생일(Age)  -->
                     <v-row justify="center">
                        <v-col cols="5">
                           <v-menu
                           v-model="menu2"
                           :close-on-content-click="false"
                           :nudge-right="40"
                           transition="scale-transition"
                           offset-y
                           min-width="290px"
                           requried
                           >
                              <template v-slot:activator="{ on }">
                                 <v-text-field
                                 v-model="tempAge"
                                 label="생년월일"
                                 readonly
                                 v-on="on"
                                 ></v-text-field>
                              </template>      
                                 <v-date-picker v-model="tempAge" @input="menu2 = false"></v-date-picker>
                           </v-menu>
                        </v-col>
                        <v-col cols="1"></v-col>
               <!-- 반려견 체중  -->
                           <v-col cols="5">
                              <v-text-field
                              v-model="userPetVo.petWeight"
                              label="반려견체중"
                              required
                              >
                              </v-text-field>
                           </v-col>
                     </v-row>
               <!-- row3  -->
               <!-- 반려견 성별 라디오버튼  -->
                     <v-row justify="center">
                        <v-col cols="5" >
                           <p> 성별 </p> 
                              <v-radio-group v-model="userPetVo.petGender" row>
                                 <v-radio label="수컷" :value="1"></v-radio>
                                 <v-radio label="암컷" :value="0"></v-radio>
                              </v-radio-group>
                        </v-col>
       <v-col cols="1"></v-col>
               <!-- 반려견 중성화 여부 라디오버튼 -->
                        <v-col cols="5">
                           <p> 중성화 여부  </p> 
                              <v-radio-group v-model="userPetVo.neutralization" row>
                                 <v-radio label="YES" value="true"></v-radio>
                                 <v-radio label="NO" value="false"></v-radio>
                              </v-radio-group>
                        </v-col>
                     </v-row>
               <!-- 반려견 답변 1 -->
                     <v-row justify="center" class="ml-12">
                        <v-col cols="5">
                           <p> Q. 반려동물 등록을 하였습니까?(필수)  </p> 
                              <v-radio-group v-model="petAnswerOne" column>
                                 <v-radio label="내장형 무선식별장치 개체삽입" value="내장형 무선식별장치 개체삽입"></v-radio>
                                 <v-radio label="외장형 무선식별장치 부착"    value="외장형 무선식별장치 부착"></v-radio>
                                 <v-radio label="등록인식표 부착"           value="등록인식표 부착"></v-radio>
                                 <v-radio label="하지않음"                value="하지않음"></v-radio>
                              </v-radio-group>
                        </v-col>
                     </v-row>
     
               <!-- 반려견 답변 2-->
                     <v-row justify="center" class="ml-12">
                        <v-col cols="5">
                           <p> Q. 다른 강아지와 친화적인가요?(필수)  </p>
                              <v-radio-group v-model="petAnswerTwo" row>
                                 <v-radio label="YES" value="1"></v-radio>
                                 <v-radio label="NO"  value="0"></v-radio>
                              </v-radio-group>
                        </v-col>
                     </v-row>
               <!-- 반려견 답변 3-->
                     <v-row justify="center" class="ml-12">
                           <v-col cols="5">
                              <p> Q. 필수 예방 접종을 완료하셨나요?(필수)  </p>
                                 <v-radio-group v-model="petAnswerThree" row>
                                    <v-radio label="YES" value="1"></v-radio>
                                    <v-radio label="NO"  value="0"></v-radio>
                                 </v-radio-group>
                           </v-col>
                     </v-row>
    
               <!-- 반려견 답변 4-->
                     <v-row justify="center" class="ml-12">
                           <v-col cols="5">
                              <p>Q. 기타 참고사항 및 특이사항(선택)</p>
                                 <v-textarea
                                 filled
                                 auto-grow
                                 label="예)우리 강아지는 실외배변만 해요"
                                 rows="4"
                                 value="answer4"
                                 row-height="30" v-model="petAnswerFour"
                                 ></v-textarea>
                           </v-col>
                     </v-row>
                     <v-row>
                        <div v-if="false"
                        >
                         <v-text-field
                         @input="userInfoReading"
                         :value="userInfo.userNo"
                         >

                         </v-text-field>
                          
                        </div>
                     </v-row>

                        <v-row justify="center" class="ml-12">
                           <v-col cols="3">
                              <v-btn @click="saveUserVo" 
                              >저장하기</v-btn>
                           </v-col>
                        </v-row>
               </form>
            </v-card>
         </v-col>
      </v-row>
   </v-container>
</template>



<script>
import axios from 'axios'
import NavBar from '@/components/userNavigation.vue'
import { mapState} from 'vuex'
import { format } from 'path'



export default {
   
    // data: () => ({
    //   valid: true,
    //   PetName: '',
    //   nameRules: [
    //     v => !!v || '이름은 필수 입력 사항입니다. ',
    //     v => (v && v.length <= 10) || ' 10 자 내외로 입력 가능합니다. ',
    //   ],
    // }),
    data(){
        return{
            valild:true,
            nameRules:[
               v => !!v || '반려견 이름은 필수 입력 사항입니다. ',
               v => (v && v.length <= 15) || ' 15 자 내외로 입력 가능합니다. '
            ],
             tempBreed:[

             ],
             tempBreedNo:[

             ],
             tempAge:'',
             petAnswerOne:'',
             petAnswerTwo:'',
             petAnswerThree:'',
             petAnswerFour:'',
             imageData:'',
             petImg:'',
             previewImageData:'',
             userPetVo:{
               userNo:null,             
               petName:'',
               petAge:'',
               dogBreed:'',
               petWeight:'',
               petGender:'',
               neutralization:'',
               petAnswer:'',
               breedNo:''
               //breedInfo:''

            },
            date: new Date().toISOString().substr(0,10),
            menu2:false
        }
    },
         components: {
    'side-bar' : NavBar
      },
    created(){
       this.loadBreedInfo()
       this.userPetVo.userNo=this.$store.state.userInfo.userNo
    },
    computed:{
      ...mapState(['userInfo']),
       lsm(){
          let qwe=[]
          for(let i in this.tempBreed){
             qwe.push(this.tempBreed[i].dogBreed)
          }
          return qwe
       },
      getUserNo:{
         get(){
            return this.$store.state.userInfo.userNo

         }
      } 


    },
    methods:{
       loadBreedInfo(){  //axios 를 통해서 BreedVO 를 불러와서 견종의 종류를 선택할 수 있도록 함 
          axios.get('http://localhost:1234/showAllBreedInfo')
          .then(response =>{
             console.log(response)
             for(let i=0; i<response.data.length; i++){
                this.tempBreed.push(response.data[i])
                //this.tempBreedNo.push(response.data[i].breedNo)
             }
             // console.log(this.tempBreedNo)
            // console.log(this.tempBreedInfo)
            //this.dogInfo.push(response.data[0].dogBreed)
            //this.userPetVo.breedString = this.breedArray
            //console.log(this.items)
            //console.log(this.breedArray)
            //console.log('this.userPetVo.breedString==>'+this.userPetVo.breedString)
          })
          .catch(error =>{
            console.log(error)
          })
       },
       saveUserVo(){  // 저장 후 디비 저장할 수 있도록 axios 
           this.userPetVo.petAnswer = this.petAnswerOne+','+this.petAnswerTwo+','+this.petAnswerThree+
                            ','+this.petAnswerFour
           //console.log(this.userPetVo.petAnswer)
           let today = new Date().getFullYear()
           //console.log(today)  // 2019 
           let year = this.tempAge.split('-')
           this.userPetVo.petAge = today - parseInt(year[0])
           
           /* switch(this.userPetVo.dogBreed){
               case 'Maltese':
                  this.userPetVo.breedNo = 1
                  break;
               case 'Pomeranian':
                  this.userPetVo.breedNo = 2
                  break;
               case 'YorkshireTerrier':
                  this.userPetVo.breedNo = 3
                  break;
           } */
           for(let i in this.tempBreed){
              if(this.userPetVo.dogBreed ===this.tempBreed[i].dogBreed){
                 this.userPetVo.breedNo = this.tempBreed[i].breedNo
                 break;
              }
           }
           let formData = new FormData()
            formData.append('file', this.petImg)            

           axios.post('http://localhost:1234/addPet', this.userPetVo)   //이미지만 제외 후 저장 
           .then(response =>{
              console.log(response)
               let newPetVo = response.data
            if(this.petImg !== ''){   
                 // 성공 후 바로 또 axios를 통해서 이미지만 저장 
                 axios.post('http://localhost:1234/upload-petImg/'+this.userInfo.userEmail+'/'+newPetVo.petNo, formData,{    
                           headers:{
                                 'Content-Type' : 'multipart/form-data'
                                   }
                 }).then(response =>{
                    console.log(response.data)
                    this.$router.push('/uMyPage/petInfo')
                 })
           }else{
              this.$router.push('/uMyPage/petInfo')
           }

           })
           .catch(error =>{
              alert('입력이 잘 되었는지 다시 확인 해 주세요')
           })
           
             

       },
       previewImageOne(event){   // 반려견 이미지 업로드 
          console.log(event)
          var input = event.target;
            // Ensure that you have a file before attempting to read it
            if (input.files && input.files[0]) {
                // create a new FileReader to read this image and convert to base64 format
                var reader = new FileReader();
                // Define a callback function to run, when FileReader finishes its job
                reader.onload = (e) => {
                    // Note: arrow function used here, so that "this.imageData" refers to the imageData of Vue component
                    // Read image as base64 and set to imageData
                    this.previewImageData = e.target.result;
                    
                }
                // Start the reader job - read file as a data url (base64 format)
                reader.readAsDataURL(input.files[0]);
            }
       },
       previewImage(event){
            //console.log(event)
            this.petImg = event.target.files[0]
            console.log(this.petImg)
            var input = event.target;
            //console.log(input)
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = (e) => {
                    //console.log(e)
                    this.imageData= e.target.result;
                    
                }
                
                reader.readAsDataURL(input.files[0]);
            }
        },
        userInfoReading(e){
           this.$store.commit('userInfoReading', e.target.value)
           console.log(e)
        }

       // 이 함수는 사진을 업로드 하고 이것을 axios 를 통해서 백엔드로 보내면서 디비에 저장 할 수 있는 함수이다. 나는 이것을 한번에 보낼것이기 때문에 필요 없다. 
      //  imageUpload(){      
      //     const fd = new FormData();
      //     fd.append('image', this.selectedFile, this.selectedFile.name)
      //     axios.post("백엔드 쪽에 보낼 url ", "보낼 객체 ")

      //  }
       
    }


    
}
</script>

<style  scoped>
  .file-upload-form, .image-preview {
    font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    padding: 20px;
   }
    img.preview {
    width: 200px;
    background-color: white;
    border: 1px solid #DDD;
    padding: 5px;
}

</style>