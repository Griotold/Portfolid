//----- 좌표 데이터 처리 -------------------------------------------------------------------------------------------------------
import geojson from "/data/geo/seoulGeo.json" assert { type: "json" };

let data = geojson.features;
let paths = [];
let names = [];

for(let j = 0; j < data.length; j++){
	var a = [];
	names.push(data[j].properties.SIG_KOR_NM);
	
	for(let i = 0; i < data[j].geometry.coordinates[0].length; i++){
		a.push(new kakao.maps.LatLng(data[j].geometry.coordinates[0][i][1], data[j].geometry.coordinates[0][i][0]));
	}	
	paths[j] = a;
}

function Area(name, path){
	this.name = name;
	this.path = path;
}

var areas = []

for(let k = 0; k < names.length; k++){
	var pathss = new Area(names[k], paths[k]);
	areas[k] = pathss;	
}

//--------------------------------------------------------------------------------------
var cityCode = {'종로구' : '111123', '중구' : '111121', '용산구' : '111131', '성동구' : '111142', '광진구' : '111141',
			   '동대문구' : '111152', '중랑구' : '111151', '성북구' : '111161', '강북구' : '111291', '도봉구' : '111171', 
			   '노원구' : '111311', '은평구' : '111181', '서대문구' : '111191', '마포구' : '111201', '양천구' : '111301',
			   '강서구' : '111212', '구로구' : '111221', '금천구' : '111281', '영등포구' : '111231', '동작구' : '111241',
			   '관악구' : '111251', '서초구' : '111262', '강남구' : '111261', '송파구' : '111273', '강동구' : '111274'}

//--------------------------------------------------------------------------------------

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        draggable : false,
        level: 9 // 지도의 확대 레벨
        
    };

var map = new kakao.maps.Map(mapContainer, mapOption),
    customOverlay = new kakao.maps.CustomOverlay({}),
    infowindow = new kakao.maps.InfoWindow({removable: true});

// 지도에 영역데이터를 폴리곤으로 표시합니다 
for (var i = 0, len = areas.length; i < len; i++) {
    displayArea(areas[i]);
}

// 다각형을 생상하고 이벤트를 등록하는 함수입니다
function displayArea(area) {

    // 다각형을 생성합니다 
    var polygon = new kakao.maps.Polygon({
        map: map, // 다각형을 표시할 지도 객체
        path: area.path,
        strokeWeight: 2,
        strokeColor: '#004c80',
        strokeOpacity: 0.8,
        fillColor: '#fff',
        fillOpacity: 0.7 
    });

    // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다 
    // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
    kakao.maps.event.addListener(polygon, 'mouseover', function(mouseEvent) {
		pollutionValue(cityCode[area.name], 'PM10');
		
		var colorCode = changeColor(pA);
		
		polygon.setOptions({fillColor: colorCode});
		
        //polygon.setOptions({fillColor: '#09f'});
						
        customOverlay.setContent('<div class="area">' + area.name + '</div>');
        
        customOverlay.setPosition(mouseEvent.latLng); 
        customOverlay.setMap(map);
    });

    // 다각형에 mousemove 이벤트를 등록하고 이벤트가 발생하면 커스텀 오버레이의 위치를 변경합니다 
    kakao.maps.event.addListener(polygon, 'mousemove', function(mouseEvent) {
        
        customOverlay.setPosition(mouseEvent.latLng); 
    });

    // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
    // 커스텀 오버레이를 지도에서 제거합니다 
    kakao.maps.event.addListener(polygon, 'mouseout', function() {
		
        polygon.setOptions({fillColor: '#fff'});
        customOverlay.setMap(null);
    }); 


    // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 다각형의 이름과 면적을 인포윈도우에 표시합니다 
    kakao.maps.event.addListener(polygon, 'click', function(mouseEvent) {
		pollutionValue(cityCode[area.name], 'PM10');
		pollutionValue(cityCode[area.name], 'PM25');
        var content = '<div class="info">' + 
                    '   <div class="title">' + area.name + '</div>' +
                    '   <div class="pm10">미세먼지 : ' + pA + ' ㎍/㎥' +'</div>' + 
                    '   <div class="pm25">초미세먼지 : ' + pB +' ㎍/㎥' +'</div>' + 
                    '</div>';
		
		var colorCode = changeColor(pA);
	
		polygon.setOptions({fillColor: colorCode});
		
        infowindow.setContent(content); 
        infowindow.setPosition(mouseEvent.latLng); 
        infowindow.setMap(map);
    });
}
//-----------------------------------------------------------------------------------------------------------------------

const getPollutionVal = async (code, type) => {
	const response = await fetch('http://openapi.seoul.go.kr:8088/41796f7462746d7638397263707561/json/ListAirQualityByDistrictService/1/5/' + code);
	if (response.status === 200){
		const data = await response.json();
		return data.ListAirQualityByDistrictService.row[0][type];
	} else{
		throw new Error('Error');
	}
}

var pA;
var pB;

const pollutionValue = async (code, type) => {
	const pollutionVal = await getPollutionVal(code, type);
	if (type === "PM10"){
		pA = pollutionVal;	
	}
	if (type === "PM25"){
		pB = pollutionVal;	
	}
	//return pollutionVal;
}

//-----------------------------------------------------------------------------------------------------------------------

function changeColor(pm10){
	
	var colorCode; 
	
	if (pm10 >= 0 && pm10 <=30){
		colorCode = '#09f';
	} else if(pm10 >= 31 && pm10 <= 80){
		colorCode = '#09';
	} else if(pm10 >= 81 && pm10 <= 150){
		colorCode = '#ff0';
	} else if(pm10 >= 151){
		colorCode = '#f00';
	} else{
		colorCode = '#fff';
	}
	
	return colorCode;
}

