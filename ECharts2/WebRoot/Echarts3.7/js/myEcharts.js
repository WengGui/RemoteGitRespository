var dataOption={
		title:{
			text:''
		},
		tooltip:{trigger:'axis'},
		toolbox:{
		    show:false,
		    feature : {
	            mark : {'show': true},
	            dataView : {'show': true, 'readOnly': false},
	            magicType : {'show': true, 'type': ['line', 'bar']},
	            restore : {'show': true},
	            saveAsImage : {'show': true}
	         } 
		},
		calculable:true,
		legend:{
			data:[],
		    selectedMode: 'single'
		},
		xAxis:[{
			type:'category',
			boundaryGap:true,
			data:[]
		}],
		yAxis:[{
			type:'value',
			axisLabel:{formatter:'{value}GB'}
		}],
		series:[]
};
var unit=LoadData("/ECharts2/Echarts3.7/Json/unitData.json")
var theme = null;

//同步加载数据加载数据
function LoadData(url){
	var option=null;
	$.ajax({
		type:"post",
		url:url,
		cache:false,
		async:false,
		dataType:"json",
		success:function(data){
			option = data;
		},error:function(XMLHttpRequest, textStatus, errorThrown) {
			//这个error函数调试时非常有用，如果解析不正确，将会弹出错误框
			alert(XMLHttpRequest.responseText);
			alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus); // parser error;
		}
	});
	return option;
}
//渲染图层
function RomanceChart(id,url){
	var chart = echarts.init(document.getElementById(id),'macarons');
	var option = LoadData(url);
	// 使用刚指定的配置项和数据显示图表。
    chart.setOption(option);
}

function RomanceChartByPartData(id,url,theme){
	if(theme==null)
		theme='macarons';
	var chart = echarts.init(document.getElementById(id),theme);
	var option = dataOption;
	var tempOption = LoadData(url);
	
	option.legend.data=tempOption.legend;
	option.title.text=tempOption.text;
	option.xAxis[0].data=tempOption.axis;
	option.series=tempOption.series;
	
	chart.setOption(option);
//	chart.hideLoading();
	legendSelectedEvenetForBar(chart,unit);
}
//图例选中事件
function legendSelectedEvenetForBar(chart,unit){
	chart.on('legendselectchanged',function(param){
		var curLegendText = param.name;
		var option = chart.getOption();
		for(var temp in unit){
			if(curLegendText == temp ){
				option.yAxis[0].axisLabel.formatter = '{value}'+unit[temp];
			}
		}
		chart.setOption(option);
	//	chart.hideLoading();
	});
}

$(function(){
	var url = "/ECharts2/Json/data.json";
	var id  = "main";
	var url2="/ECharts2/Echarts3.7/Json/partData.json";
	RomanceChartByPartData(id,url2,theme);

});

