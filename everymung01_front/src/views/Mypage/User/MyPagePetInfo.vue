<template>
   <v-container fluid="" class="fill-height">
       <v-row class="fill-height">
        <v-col cols="3">
          <side-bar/>   
        </v-col>
        <v-col cols="9">
            <v-row>
             <v-btn router :to="{name:'myPetDetail'}"> 반려견 추가하기 </v-btn>
            </v-row>
            
                <v-card
                class="mb-4 mt-2"
                width="70%"
                outlined
                v-for="(userPet,index) in userPetList"
                :key="index"
                >
                    <v-list-item three-line>
                    <v-list-item-avatar
                        tile
                        size="100"
                    >
                        <v-img
                        :src="tempImg[index]"
                        > </v-img>
                    </v-list-item-avatar>
                    <v-list-item-content class="mx-auto">
                        <p class="font-weight-bold">{{userPet.petName}}</p>
                        <br><br>
                        <p class="font-weigth-bold"
                            v-if="userPet.petGender === '1'"
                        > 
                        ( {{userPet.dogBreed}},수컷,만{{userPet.petAge}}살 ) 
                        </p>
                        <p v-else>
                          ( {{userPet.dogBreed}}, 암컷 ,만{{userPet.petAge}}살 ) 
                        </p>
                        <v-col class="text-reft">
                        <v-btn large color
                        @click="itemView(userPet,index)"
                        >상세보기</v-btn>
                        </v-col>
                    </v-list-item-content>

                    <v-spacer></v-spacer>

                    <!-- <v-btn> 수정 </v-btn>
                    <v-btn> 삭제 </v-btn> -->
                </v-list-item>
                </v-card>
               

            
          
        </v-col>
      </v-row> 
      
      <v-dialog v-model="dialog"  max-width="600px">
         <!-- 상세보기 다이얼로그  -->
         
          <v-card>
           
            
              <v-card-title>
                  <span class="headline"> 상세정보 </span>
              </v-card-title>
              <v-card-text>
                  <v-container>
                      <!-- 다이얼로그 반려견 사진 -->
                    
                        <div>
                         <v-row justify="center" >
                             <v-col cols="3">
                                 <div class="file-upload-form">
                                     <input type="file"
                                     @change="previewImg"
                                     accept="image/*"
                                     >
                                 </div>
                                 <!-- 기존사진  -->
                                   <div class="image-preview" v-if="trig">
                                     <v-img :src="tempPetImg.petImg"
                                     height="100" width="100" class="preview"
                                     />
                                    </div>
                                    <!-- 변경할 사진 -->
                                         <div  v-else>
                                            <v-img
                                            height="100" width="100"
                                            :src="updatedImg"
                                            class="image-preview"
                                            >
                                           </v-img>
                                        </div>
                               </v-col>
                          </v-row>
                        </div>
                      <!-- 다이얼로그 반려견 이름 -->
                      <v-row>
                          <v-col>
                            <v-text-field
                            v-model="diaDefault.petName"
                            label="반려견이름"
                            
                           ></v-text-field>
                           </v-col>
                           <!-- 다이얼로그 반려견 견종 -->
                           <v-col>
                             <v-text-field
                           v-model="diaDefault.dogBreed"
                           label="견종"
                           disabled
                             ></v-text-field>
                           </v-col>
                           <v-col>
                               <!-- 다이얼로그 반려견 나이 -->
                              <v-text-field 
                              v-model="diaDefault.petAge" 
                              label="나이" readonly>
                              </v-text-field>
                           </v-col>
                           <!-- 다이얼로그 반려견 체중 -->
                           <v-col>
                               <v-text-field
                               v-model="diaDefault.petWeight"
                               label="반려견체중" 
                               >
                               </v-text-field>
                           </v-col>
                      </v-row>
                      <!-- 다이얼로그 반려견 성별 -->
                      <v-row>
                          <v-col>
                              <p>성별</p>
                              <v-radio-group v-model="diaDefault.petGender" row>
                                 <v-radio label="수컷" value=1 true-value=1></v-radio>
                                 <v-radio label="암컷" value=0 true-value=0></v-radio>
                              </v-radio-group>
                          </v-col>
                          <v-col>
                              <p> 중성화 여부  </p> 
                              <v-radio-group 
                                v-model="diaDefault.neutralization" row>
                                 <v-radio label="YES" :value="true" ></v-radio>
                                 <v-radio label="NO"  :value="false" ></v-radio>
                              </v-radio-group>
                          </v-col>
                      </v-row>
                      <v-row>
                          <!-- 다이얼로그 반려견 답변들 -->
                            <v-col>
                              <p> Q. 반려동물 등록을 하였습니까?(필수)  </p> 
                               <v-radio-group  v-model="tempPetAnswer[0]"    column>
                                 <v-radio  label="내장형 무선식별장치 개체삽입" value="내장형 무선식별장치 개체삽입"   ></v-radio>
                                 <v-radio  label="외장형 무선식별장치 부착"    value="외장형 무선식별장치 부착"></v-radio>
                                 <v-radio  label="등록인식표 부착"           value="등록인식표 부착"></v-radio>
                                 <v-radio  label="하지않음"                value="하지않음"></v-radio>
                               </v-radio-group>
                            </v-col>
                      </v-row>
                      <v-row>
                          <v-col>
                              <p> Q. 다른 강아지와 친화적인가요?(필수)  </p>
                                    <v-radio-group v-model="tempPetAnswer[1]" row>
                                        <v-radio label="YES" value="1"></v-radio>
                                        <v-radio label="NO"  value="0"></v-radio>
                                    </v-radio-group>
                          </v-col>
                      </v-row>
                      <v-row>
                          <v-col>
                               <p> Q. 필수 예방 접종을 완료하셨나요?(필수)  </p>
                                 <v-radio-group v-model="tempPetAnswer[2]" row>
                                    <v-radio label="YES" value="1"></v-radio>
                                    <v-radio label="NO"  value="0"></v-radio>
                                 </v-radio-group>
                          </v-col>
                      </v-row>
                      <v-row>
                          <v-col>
                              <p>Q. 기타 참고사항 및 특이사항(선택)</p>
                                 <v-textarea
                                 filled
                                 auto-grow
                                 label="예)우리 강아지는 실외배변만 해요"
                                 rows="4"
                                 value="answer4"
                                 row-height="30" v-model="tempPetAnswer[3]"
                                 ></v-textarea>
                          </v-col>
                      </v-row>
                      <v-row >
                          <v-col class="text-right">
                          <v-btn text color="primary"
                          @click="sendUpdatePetInfo"
                          >수정</v-btn>

                          <v-btn 
                          @click.stop="deleteBtn = true"
                          text color="primary"
                          @click="deletePet"
                          >삭제</v-btn>
                          <!-- @click="deletePet" -->
                          <!-- @click="comfirmDelete" -->
                          </v-col>
                      </v-row>
                    
                  </v-container>
              </v-card-text>
          
          </v-card>
         
      </v-dialog>
                  
      
   </v-container>
</template>


<script>
import axios from 'axios'
import NavBar from '@/components/userNavigation.vue'
import {mapState} from 'vuex'

export default {
   data(){
       return{
           trig:true,
           _tempUpdatePetImg:'',    //신규 이미지 이름 담고 있는 놈. 
           updatedImg:'',
           deleteBtn:false,
           tempPetImg:{          //기존이미지 바인딩 
               petImg:''
           },
           petNo:'',
           tempPetAnswer:'',
           diaDefault:{
               petName:'',
               dogBreed:'',
               petAge:'',
               petWeight:'',
               petGender:'',
               neutralization:'',
               petAnswer:'',
               breedNo:'',
               userNo:'',

           },
           tempUpdatePet:{
              u_neutralization:'',

           },
           tempPetAnswer:[],
           tempImg:[],
           tempImg2:'',
        userPetList:[
         
        ],
        dialog:false
       }
   },
     components: {
    'side-bar' : NavBar
  },

   created(){
       console.log("created()")
       this.init()
   },

   computed: {
       ...mapState(["userInfo"]),
   },
   
   methods:{
       itemView(item,index){
           if(this.updatedImg !==null){
            this.updatedImg=''
            this.trig=true
           }
           this.dialog=true
           this.tempPetImg.petImg = this.tempImg[index]
           this.diaDefault.petName =item.petName
           this.diaDefault.dogBreed = item.dogBreed
           this.diaDefault.petAge = item.petAge
           this.diaDefault.petWeight = item.petWeight
           this.diaDefault.petGender = item.petGender
           this.diaDefault.neutralization = item.neutralization
           this.tempPetAnswer = item.petAnswer.split(',')
           this.diaDefault.breedNo = item.breedNo
           this.diaDefault.userNo = item.userNo
           this.petNo = item.petNo
           
           //console.log(this.diaDefault)
           console.log(this.tempPetImg.petImg)
           //this.diaDefault=item
           //this.tempImg2=this.tempImg[index]
           //this.tempPetAnswer = item.petAnswer.split(',')
           //console.log(this.tempImg[index])  
           //console.log(item)
           //console.log(this.tempPetAnswer)
       },
       init(){
           
           axios.post(`http://localhost:1234/showAllpets/${this.userInfo.userNo}`) // 2=> 로그인 하면서 userNo 가 들어오면 동적으로 변수 바인딩 해줘야 한다. 
           .then(response => {
               this.userPetList = response.data
               console.log(this.userPetList)
               for(let img in this.userPetList){
                   if(response.data[img].petImg === null){
                       this.tempImg[img]= 'http://localhost:1234/download/default.jpg';
                   }else{
                   this.tempImg[img]='http://localhost:1234/download/'+response.data[img].petImg
                   }
               }
               //console.log(response.data)
                   
           })
           .catch(error =>{
               console.log(error)
           })
       },
       previewImg(event){
            //console.log(event)
            this._tempUpdatePetImg = event.target.files[0]
            console.log(this._tempUpdatePetImg)
            var input = event.target;
            //console.log(input)
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = (e) => {
                    //console.log(e)
                    this.updatedImg= e.target.result;
                }
                reader.readAsDataURL(input.files[0]);
            }
            this.trig=false
        },
        sendUpdatePetInfo(){
            this.diaDefault.petAnswer = this.tempPetAnswer[0]+','+this.tempPetAnswer[1]+','+this.tempPetAnswer[2]+','+this.tempPetAnswer[3]
            console.log(this.diaDefault)

            if(this.trig){  //이미지를 수정하지 않았을 때
                axios.post('http://localhost:1234/updatePet', this.diaDefault)
                .then(response =>{
                    console.log('success')
                    //this.dialog=false
                    //this.init()
                })
                .catch(error =>{
                    console.log(error)
                })

            }else {    // 이미지를 수정 했다면.. 
                axios.post('http://localhost:1234/deletePetImg/'+this.diaDefault.userNo)   // 기존 이미지 삭제 후 
                .then(response =>{
                    console.log('이미지 수정 성공')
                    let petVO = response.data
                    console.log(petVO)
                    let formData = new FormData()
                    formData.append('file', this._tempUpdatePetImg)

                    //수정할 이미지 업로드 한 후 
                    axios.post('http://localhost:1234/upload-petImg/'+this.userInfo.userEmail+'/'+petVO.petNo, formData,{
                        headers:{
                            'Content-Type':'multipart/form-data'
                        }
                    }).then(response =>{
                        console.log('수정이미지 업로드 중~ ')
                        let finalPetVo = response.data
                        console.log(finalPetVo)

                        axios.post('http://localhost:1234/updatePet',this.diaDefault)
                        .then(response =>{
                            console.log('이미지 수정 업로드 완료 !! ')
                            //this.dialog=false
                            //this.init()
                            
                        })

                    }).catch(error =>{
                        console.log('수정 이미지 업로드 에러 ')
                    })

                })
            }
             this.dialog = false
             this.init()
            
        },
       
        deletePet(){
            
            //axios.post('http://localhost:1234/deletePet/'+this.diaDefault.userNo)
            axios.post('http://localhost:1234/deletePet/'+this.petNo)
            .then(response =>{
                console.log('delete 성공')
            })
            .catch(error =>{
                console.log('delete 에러 ')
                console.log(error)
            })
            
            this.dialog = false
            this.init()
        }
   
    },
  
   

    
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
