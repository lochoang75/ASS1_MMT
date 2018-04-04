const char MAIN_page[] PROGMEM = R"=====(
<!DOCTYPE HTML>
<html>
   <head>
      <meta charset="utf-8"/>
      <title>Welcome home !</title>
      <script src="http://code.jquery.com/jquery-1.8.3.min.js "></script>
      <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
      <script>
      	window.onload = function () {
      		var dps = [];
      		var minutes= new Date();
      		for(var i=0;i<10;i++){
      		 dps[i]=[];
      		}
var chart = new CanvasJS.Chart("chartContainer", {
      		 title :{ text: "Bảng theo dõi"},
      		 axisX: {    title: "Thời gian",  },
      		 axisY: {    title: "Nhiệt độ ( °C)",    includeZero: false,    suffix: " °C", },
      		 data: [
      		 {xValueType: "dateTime",xValueFormatString: "DD MMM hh:mm TT",showInLegend: true,name:"ID0",type: "spline",dataPoints: dps[0]},
      		 {xValueType:"dateTime",xValueFormatString: "DD MMM hh:mm TT",showInLegend: true,name:"ID1",type: "spline",dataPoints: dps[1]},
      		 {xValueType: "dateTime",xValueFormatString: "DD MMM hh:mm TT",showInLegend: true,name:"ID2",type: "spline",dataPoints: dps[2]},
      		 {xValueType: "dateTime",xValueFormatString: "DD MMM hh:mm TT",showInLegend: true,name:"ID3",type: "spline",dataPoints: dps[3]},
      		 {xValueType: "dateTime",xValueFormatString: "DD MMM hh:mm TT",showInLegend: true,name:"ID4",type: "spline",dataPoints: dps[4]},
      		 {xValueType: "dateTime",xValueFormatString: "DD MMM hh:mm TT",showInLegend: true,name:"ID5",type: "spline",dataPoints: dps[5]},
      		 {xValueType: "dateTime",xValueFormatString: "DD MMM hh:mm TT",showInLegend: true,name:"ID6",type: "spline",dataPoints: dps[6]},
      		 {xValueType: "dateTime",xValueFormatString: "DD MMM hh:mm TT",showInLegend: true,name:"ID7",type: "spline",dataPoints: dps[7]},
      		 {xValueType: "dateTime",xValueFormatString: "DD MMM hh:mm TT",showInLegend: true,name:"ID8",type: "spline",dataPoints: dps[8]},
      		 {xValueType: "dateTime",xValueFormatString: "DD MMM hh:mm TT",showInLegend: true,name:"ID9",type: "spline",dataPoints: dps[9]}
      		 ]
      		});


	var xVal = "";
      		var yVal = 100;
      		var updateInterval = 1000;
      		var dataLength = 15;
var updateChart = function (count) {
			var source= $('#data').data('value');
      		var subStr=[];
      		for(var i=0;i<10;i++){
      	 	 subStr = source.split(";");
      		}
      			count = count || 1;
      			for (var j = 0; j < count; j++) {
      				for(var i=0;i<10;i++){
      					var temp="0,0,0";
      					for(var k=0;k<10;k++){
      						if(subStr[k]&&subStr[k][0].valueOf()==i){
      							temp= subStr[k].split(",");
      						}
      					}
      					dps[i].push({
      						x: xVal,
      						y: Math.round(temp[1].valueOf()),
      					});
      				}
      				var Time = new Date();
      				xVal=Time.getTime();
      			}
      			if (dps[0].length > dataLength) {
      				for(var i=0;i<10;i++){
      					dps[i].shift();
      				}
      			}
      			chart.render();
      		};

updateChart(dataLength);

setInterval(function() {
  // Call a function repetatively with 2 Second interval
  getData();
  updateChart();
}, 2000); //2000mSeconds update rate 
function getData() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
     $('#data').data('value',this.responseText);
    }
  };
  xhttp.open("GET", "readADC", true);
  xhttp.send();
}
}
</script>
<br><br><a href="https://circuits4you.com">Circuits4you.com</a>
<div id="chartContainer" style="height: 300px; width: 100%;"></div>
      <a id="data" data-value="2,22.13"></a>
</body>
</html>
)=====";