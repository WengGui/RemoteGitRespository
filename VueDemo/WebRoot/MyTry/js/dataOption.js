var initData={
		message:'钱钱钱钱钱钱钱钱钱钱钱钱',
		see:false,
		people:[
		        {name:'GGGGG'},
		        {name:'WWWWW'},
		],
	mes:'some messages'
}

var app = new Vue({
	el:'#app',
	data:initData,
	methods:{
		reverseMessage:function(){
			this.mes = this.mes.split('').reverse().join('')
		}
	}
});

//定义名为 todo-item 的新组件
Vue.component('todo-item', {
	// todo-item 组件现在接受一个
	// "prop"，类似于一个自定义属性
	// 这个属性名为 todo。
	props: ['todo'],
	template: '<li>{{ todo.text }}</li>'
});

var app7 = new Vue({
	  el: '#app-7',
	  data: {
	    groceryList: [
	      { id: 0, text: '蔬菜' },
	      { id: 1, text: '奶酪' },
	      { id: 2, text: '随便其他什么人吃的东西' }
	    ]
	  }
	});