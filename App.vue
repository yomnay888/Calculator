<template>
  <div class="container">
      <div class="result">{{a}}{{op }}{{ b }}</div>
      <div class="buttons">
          <button @click="singleOperation('percentage')" >%</button>
          <button @click="singleOperation('root')" >&radic;<span style="text-decoration:overline;">&nbsp;X &nbsp;</span></button>
          <button @click="singleOperation('square')">x<sup>2</sup></button>
          <button @click="singleOperation('reciprocal')">1/x</button>
          <button @click="clear()">CE</button>
          <button @click="clear()">C</button>
          <button @click="back()" >BACK</button>
          <button class="op-button" @click="handleOperator('/')"><span>&#247;</span></button>
          <button class="num" @click="updateValue('7')">7</button>
          <button class="num" @click="updateValue('8')">8</button>
          <button class="num" @click="updateValue('9')">9</button>
          <button class="op-button" @click="handleOperator('*')"><span>&#215;</span></button>
          <button class="num" @click="updateValue('4')">4</button>
          <button class="num" @click="updateValue('5')">5</button>
          <button class="num" @click="updateValue('6')">6</button>
          <button class="op-button" @click="handleOperator('-')">-</button>
          <button class="num" @click="updateValue('1')">1</button>
          <button class="num" @click="updateValue('2')">2</button>
          <button class="num" @click="updateValue('3')">3</button>
          <button class="op-button" @click="handleOperator('+')">+</button>
          <button @click="posNeg()">+/-</button>
          <button class="num" @click="updateValue('0')">0</button>
          <button @click="updateValue('.')">.</button>
          <button class="op-button" @click="cal(this.a,this.b,this.op)">=</button>
      </div>
  </div> 
</template>
<script>
export default {
  data() {
    return{
      a:'0',
      b:'',
      op:'',
      hasOp:false,
    IsaResult:false,
    IsbResult:false,
    }
  },
  methods:{
    cal(a,b,op) {
      if( this.a=='Error' )
          this.clear()
        else{
      let data=[a,b,op];
  fetch("http://localhost:8081", {
    method: "POST",
    headers: {
      'content-type': 'application/json',
    },
    body: JSON.stringify(data),
  }).then(response => response.json()).then(result => {
    if(result=='Infinity'||result=='NaN'){
      this.clear()
      this.a='Error'      
    }
    else{
    this.a = result
    this.IsaResult=true
    this.IsbResult=false
    if(!this.hasOp)
          this.op = ''
    else
        this.hasOp=false
    this.b=''
    }
  })
  .catch(error => {
    console.log('Error:', error)
  })}
},
updateValue(element){
  if(this.a=='Error')
         this.clear()
  if(this.op==''&&!this.IsaResult){
    if(element!='.'||!this.a.includes('.')){
      if(this.a=='0'&&element!='.')
        this.a=''
    this.a+=element;
    }
  }
      else{
        if(!this.op==''&&!this.IsbResult&&(element!='.'||!this.b.includes('.'))){
          if(this.b==''&&element=='.')
             this.b='0'
        this.b+=element;
      }   
    }
    },
  singleOperation(operation){
    if(this.a=='Error')
          this.clear()
    let val = 0 
        if(this.b!== ''){ 
          val = this.b 
          this.IsbResult=true
        }
        else { 
          val = this.a
          this.IsaResult=true
          this.op = '' 
        } 
    fetch("http://localhost:8081/single", {
    method: "POST",
    headers: {
      'content-type': 'application/json',
    },
    body: JSON.stringify([val,operation]),
  }).then(response => response.json()).then(result => {
    if(result=='Infinity' ||result=='NaN'){
      this.clear()
      this.a='Error'  
    }
else{
    if(val === this.b) 
              this.b = result
            else
              this.a = result
}
  })
  .catch(error => {
    console.log('Error:', error)
  })
    },
    clear(){ 
        this.a = '0' 
        this.b = '' 
        this.op = '' 
      this.IsaResult=false
      this.IsbResult=false
      this.hasOp=false
      },
      handleOperator(element){
        if(this.a=='Error')
          this.clear()
        if(this.b!=''){
         this.hasOp='true'
         this.cal(this.a,this.b,this.op) 
        }
        this.op=element
      }, 
      back(){
        if(this.a=='Error')
          this.clear()
        else{
        if(this.b!=''&&!this.IsbResult)
          this.b=this.b.substring(0,(this.b.length)-1)
        else if(this.b==''&&this.op!='')
          this.op=''
        else if(this.b==''&&!this.IsaResult)
          this.a=this.a.substring(0,(this.a.length)-1)
        if(this.a=='-')
            this.a='0'
          else if(this.b=='-')
          this.b='0'
        }
      },
      posNeg(){
        if(this.a=='Error')
          this.clear()
        else{
        if(this.op==''&&this.a!='')
          this.a=String(-1*this.a)
      else if(this.b!='')
        this.b=String(-1*this.b)
      }
    }
}
}
</script>
<style>
body {
  background-color:  lightgray;
}
.op-button:hover{
  background-color: rgba(0, 81, 255, 0.712);
  border:none;
}
.op-button:active{
  background-color: rgba(0, 17, 255, 0.712);
  border:none;
}
.result {
  height: 120px;
  display:flex;
  align-items: center;
  font-size: 30px;
  font-weight: bold;

}
.buttons {
  display: grid;
  grid-template-columns: auto auto auto auto;
}
button {
  height: 80px;
  border: 1px solid rgba(0, 0, 0, 0.1);
  border-top:0px;
  font-size: 25px;
  text-align: center;
  background-color: rgb(245, 243, 243);
}
button:hover{
  background-color: lightgray;
  border:none;
}
button:active{
  background-color: rgba(240, 240, 240, 0.452);

}
.num {
  font-weight: bold;
  background-color: white;
}
.clicked-button-op:active {
  background-color: rgba(113, 77, 245, 0.658);
}
</style>
